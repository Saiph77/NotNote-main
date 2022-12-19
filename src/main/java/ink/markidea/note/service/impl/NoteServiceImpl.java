package ink.markidea.note.service.impl;

import com.github.benmanes.caffeine.cache.LoadingCache;
import ink.markidea.note.dao.DelNoteRepository;
import ink.markidea.note.entity.DelNoteDo;
import ink.markidea.note.entity.dto.NotePreviewInfo;
import ink.markidea.note.entity.dto.UserNoteKey;
import ink.markidea.note.entity.exception.PromptException;
import ink.markidea.note.entity.resp.ServerResponse;
import ink.markidea.note.entity.vo.DeletedNoteVo;
import ink.markidea.note.entity.vo.NoteVersionVo;
import ink.markidea.note.entity.vo.NoteVo;
import ink.markidea.note.service.IArticleService;
import ink.markidea.note.service.IFileService;
import ink.markidea.note.service.INoteService;
import ink.markidea.note.util.DateTimeUtil;
import ink.markidea.note.util.FileUtil;
import ink.markidea.note.util.GitUtil;
import ink.markidea.note.util.ThreadLocalUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteServiceImpl implements INoteService {

    @Value("${notesDir}")
    private String notesDir;

    @Autowired
    private IFileService fileService;

    @Autowired
    private DelNoteRepository delNoteRepository;

    private static final String NOTEBOOK_FLAG_FILE = ".notebook";

    @Autowired
    @Qualifier("userNoteCache")
    LoadingCache<UserNoteKey, String> userNoteCache;


    @Autowired
    @Qualifier("userNotePreviewCache")
    LoadingCache<UserNoteKey, NotePreviewInfo> userNotePreviewCache;


    @Autowired
    private IArticleService articleService;

    /**
     * 展示笔记本
     * 从用户目录中读取
     * @return 所有笔记本名的集合
     * 被引用：
     *  NoteController.getNotebooks()
     *  NoteServiceImpl.search()
     */
    @Override
    public ServerResponse<List<String>> listNotebooks(){
        //获取用户文件夹
        File dir = getOrCreateUserNotebookDir();
        //获取用户笔记本（笔记文件夹中的子文件即笔记本）
        File[] childFiles = dir.listFiles();
        //判断是否为空
        if (childFiles == null || childFiles.length == 0){
            return ServerResponse.buildSuccessResponse(Collections.emptyList());
        }
        //如果不为空则全部载入 notebookNameList 只展示下级目录中的笔记
        List<String> notebookNameList = new ArrayList<>();
        for (File file : childFiles){
            if (file.isDirectory()){
                String fileName = file.getName();
                if (fileName.startsWith(".")){
                    continue;
                }
                notebookNameList.add(file.getName());
            }
        }
        return ServerResponse.buildSuccessResponse(notebookNameList);
    }

    /**
     * 展示笔记实体的集合
     * @param notebookName 传参为用户笔记文件夹名称
     * @return 返回服务器响应对象 Data为笔记实体的集合
     */
    @Override
    public ServerResponse<List<NoteVo>> listNotes(String notebookName){
        return ServerResponse.buildSuccessResponse(listNotesByNotebookName(notebookName));
    }

    /**
     * 通过笔记目录名 展示笔记的具体实现
     * @param notebookName 传参为用户笔记文件夹名称
     * @return 返回笔记实体的集合
     */
    private List<NoteVo> listNotesByNotebookName(String notebookName) {
        //新建一个临时文件目录 getOrCreateUserNotebookDir()返回一个用户唯一目录 来容纳指定文件夹的子文件
        File notebookDir = new File(getOrCreateUserNotebookDir(), notebookName);
        //如果这个笔记本不存在 则抛出异常
        if (!notebookDir.exists() || notebookDir.isFile()){
            throw new RuntimeException("No such notebook");
        }
        //来容纳指定文件夹的子文件
        File[] childFiles = notebookDir.listFiles();
        if (childFiles == null || childFiles.length == 0 ){
            return Collections.emptyList();
        }
        //在子文件夹不为空的情况下 获取改动过但未提交至git仓库的文件名的集合
        Set<String> modifiedSet = GitUtil.getModifiedButUnCommitted(getOrCreateUserGit(), notebookName);
        //根据上次修改的时间排序
        Arrays.sort(childFiles, (f1, f2) -> (int) (
                f2.lastModified() - f1.lastModified()));
        return Arrays.stream(childFiles).
                filter(file -> !file.isDirectory())//过滤不是note的文件
                .filter(file -> checkExtension(file.getName()))//过滤扩展名不是.md的文件
                .map(file -> {
                    //获取文件名
                    String title = file.getName().substring(0,file.getName().lastIndexOf("."));
                    //使用文件对象自带的方法 获取上一次修改的时间
                    String lastModifiedDate = DateTimeUtil.dateToStr(new Date(file.lastModified()));
                    String previewContent = null;
                    Integer articleId = null;
                    //从缓存中加载笔记信息
                    NotePreviewInfo previewInfo = userNotePreviewCache.get(buildUserNoteKey(notebookName, title));
                    //获取预览信息
                    if (previewInfo != null) {
                        previewContent = previewInfo.getPreviewContent();
                        articleId = previewInfo.getArticleId();
                    }
                    //笔记状态 是否包含此文件
                    //git仓库中是否包含这个key 如果有 则状态为临时保存，暂时不加入临时仓库 若没有则为本地文件
                    int noteStatus = modifiedSet.contains(getRelativeFileName(notebookName, title)) ?
                            NoteVo.STATUS_TMP_SAVED : NoteVo.STATUS_PRIVATE;
                    //返回视图层实体对象 加入map集合
                    return new NoteVo().setNotebookName(notebookName)
                            .setTitle(title)
                            .setStatus(noteStatus)
                            .setArticleId(articleId)
                            .setLastModifiedTime(lastModifiedDate)
                            .setPreviewContent(previewContent);
                })
                .collect(Collectors.toList());
    }

    /**
     * 查找用户笔记
     * @param keyWord 关键字
     * @param searchNotebooks 查询范围(笔记名)
     * @return 包含查询到的笔记字段
     */
    @Override
    public ServerResponse<List<NoteVo>> search(String keyWord, List<String> searchNotebooks) {
        List<String> notebookNameList ;

        if (!CollectionUtils.isEmpty(searchNotebooks)){
            //如果有传进指定的书名集合（前端指定搜索范围）则在搜索范围内搜索
            notebookNameList = searchNotebooks;
        } else {
            //若前端没有指定查询范围 则默认全部笔记
            notebookNameList = listNotebooks().getData();
        }
        if (CollectionUtils.isEmpty(notebookNameList)){
            return ServerResponse.buildSuccessResponse();
        }
        //查询结果集
        List<NoteVo> res = new ArrayList<>();
        notebookNameList.forEach(notebookName ->
                listNotesByNotebookName(notebookName).stream()//通过dto从缓存中获取每一个笔记本对象
                .map(noteVo -> noteVo.setContent(userNoteCache.get(buildUserNoteKey(notebookName, noteVo.getTitle()))))
                .filter(noteVo ->
                        StringUtils.isNotBlank(noteVo.getContent()) //先判断内容是否为空
                        && (noteVo.getContent().contains(keyWord)   //再判断标题和文章中是否存在关键字
                        || noteVo.getTitle().contains(keyWord)))
                .map(noteVo -> //记录搜索命中次数
                        noteVo.setSearchCount(subStrCount(noteVo.getContent(), keyWord) + subStrCount(noteVo.getTitle(), keyWord)))
                .forEach(res::add));
        //根据搜索命中的字符串个数排序
        res.sort((o1, o2) -> o2.getSearchCount() - o1.getSearchCount());
        return ServerResponse.buildSuccessResponse(res);

    }

    private void createNotebookIfNecessary(String notebookName){
        File notebookDir = new File(getOrCreateUserNotebookDir(),notebookName);
        if (notebookDir.exists()){
            return ;
        }
        createNotebook(notebookName);
    }

    /**
     * 创建笔记本
     * @param notebookName
     * @return
     */
    @Override
    public ServerResponse createNotebook(String notebookName){
        //先用封装类创建目录
        File notebookDir = new File(getOrCreateUserNotebookDir(), notebookName);
        if (notebookDir.exists()) {
            throw new PromptException("笔记本已存在");
        }
        if (!notebookDir.mkdir()) {
            throw new RuntimeException("Create notebook failed");
        }

        //笔记本标记后缀
        File notebookFlagFile = new File(notebookDir, NOTEBOOK_FLAG_FILE);
        try {
            if (!notebookFlagFile.createNewFile()) {
                throw new RuntimeException("Create notebook failed");
            }
        } catch (IOException e) {
            throw new RuntimeException("Create notebook failed");
        }
        String relativeName = notebookName + "/" + NOTEBOOK_FLAG_FILE;
        GitUtil.addAndCommit(getOrCreateUserGit(), relativeName);
        log.info("create notebook: {}", notebookName);
        return ServerResponse.buildSuccessResponse();
    }

    /**
     * 创建笔记
     * @param noteTitle
     * @param notebookName
     * @param content
     * @return
     */
    @Override
    public ServerResponse createNote(String noteTitle, String notebookName, String content){
        File targetFile =  new File(getOrCreateUserNotebookDir(), getRelativeFileName(notebookName, noteTitle));
        if (targetFile.exists()){
            throw new RuntimeException("Note already exists");
        }
        return saveNote(noteTitle, notebookName, content);
    }

    /**
     * 保存笔记
     * @param noteTitle
     * @param notebookName
     * @param content
     * @return
     */
    @Override
    public ServerResponse saveNote(String noteTitle, String notebookName, String content) {
        //确保目录存在
        createNotebookIfNecessary(notebookName);
        String relativeFileName = getRelativeFileName(notebookName,noteTitle);
        File noteFile = new File(getOrCreateUserNotebookDir(), relativeFileName);
        //确保文件存在 不存在则创建
        try {
            if (!noteFile.exists() && !noteFile.createNewFile()){
                throw new RuntimeException("Save note failed");
            }
        } catch (IOException e) {
            log.error("save note error", e);
            throw new RuntimeException("Save note failed");
        }

        //将笔记内容写入文件
        fileService.writeStringToFile(content,noteFile);
        GitUtil.addAndCommit(getOrCreateUserGit(),relativeFileName);
        //使缓存失效 并重新载入缓存
        invalidateCache(buildUserNoteKey(notebookName, noteTitle));
        return ServerResponse.buildSuccessResponse();
    }

    /**
     * 临时笔记 不加入版本控制
     * @param noteTitle
     * @param notebookName
     * @param content
     */
    @Override
    public void tmpSaveNote(String noteTitle, String notebookName, String content) {
        //根据笔记本名和笔笔记名获取存储的本地的文件名
        String relativeFileName = getRelativeFileName(notebookName,noteTitle);
        File noteFile = new File(getOrCreateUserNotebookDir(), relativeFileName);
        //如果file不存在 则直接结束
        if (noteFile.exists() && noteFile.isDirectory()) {
            return ;
        }
        fileService.writeStringToFile(content, noteFile);
        //数据有改动 让原来的缓存失效 重新缓存
        invalidateCache(buildUserNoteKey(notebookName, noteTitle));
    }

    /**
     * 删除临时保存的文件
     */
    @Override
    public void delTmpSavedNote(String noteTitle, String notebookName) {
        //撤销add
        GitUtil.discardChange(getOrCreateUserGit(), getRelativeFileName(notebookName, noteTitle));
    }

    /**
     * 删除笔记 同时删除文章实体和缓存和数据库以及git仓库信息
     * @param notebookName
     * @param noteTitle
     * @return
     */
    @Override
    public ServerResponse deleteNote(String notebookName, String noteTitle){
        String relativeFileName = getRelativeFileName(notebookName,noteTitle);
        File noteFile = new File(getOrCreateUserNotebookDir(), relativeFileName);
        String content = getNote(notebookName, noteTitle).getData();
        if (!noteFile.exists() || !noteFile.delete()){
            return ServerResponse.buildErrorResponse("Can't delete note");
        }
        //获取上一次被使用的信息
        String lastRef = GitUtil.getFileCurRef(getOrCreateUserGit(),relativeFileName);
        NotePreviewInfo previewInfo = userNotePreviewCache.get(buildUserNoteKey(notebookName, noteTitle));
        if (previewInfo.getArticleId() != null) {
            articleService.deleteArticle(previewInfo.getArticleId());
        }
        //删除git中的信息
        GitUtil.rmAndCommit(getOrCreateUserGit(),relativeFileName);
        //在数据库中删除笔记信息
        delNoteRepository.save(new DelNoteDo().setNotebook(notebookName)
                                    .setTitle(noteTitle)
                                    .setLastRef(lastRef)
                                    .setContent(content)
                                    .setUsername(getUsername()));
        invalidateCache(buildUserNoteKey(notebookName, noteTitle));
        return ServerResponse.buildSuccessResponse();
    }

    /**
     * 恢复文件
     * @param id
     * @return
     */
    @Override
    public ServerResponse recoverNote(Integer id){
        //在回收站中寻找 获取笔记实体
        DelNoteDo delNoteDO = delNoteRepository.findByIdAndUsername(id, getUsername());
        String relativeFileName = getRelativeFileName(delNoteDO.getNotebook(), delNoteDO.getTitle());
        File noteFile = new File(getOrCreateUserNotebookDir(), relativeFileName);
        if (noteFile.exists()){
            return ServerResponse.buildErrorResponse("Note already exists");
        }
        //从远程仓库获取文件
        GitUtil.recoverDeletedFile(getOrCreateUserGit(), relativeFileName, delNoteDO.getLastRef());
        return clearDelNote(id);//从回收站数据库中移除数据
    }

    /**
     * 获取笔记
     * @param notebookName
     * @param noteTitle
     * @return
     */
    @Override
    public ServerResponse<String> getNote(String notebookName, String noteTitle){
        return getNote(notebookName, noteTitle, getUsername());
    }

    @Override
    public ServerResponse<String> getNote(String notebookName, String noteTitle, String username) {
        //从本地缓存中读取笔记内容 全部返回给前端
        String content = userNoteCache.get(buildUserNoteKey(notebookName, noteTitle, username));

        if (content == null){
            return ServerResponse.buildErrorResponse("读取笔记失败");
        }
        return ServerResponse.buildSuccessResponse(content);
    }

    /**
     * 获取笔记修改记录
     * @param notebookName
     * @param noteTitle
     * @return
     */
    @Override
    public ServerResponse<List<NoteVersionVo>> getNoteHistory(String notebookName, String noteTitle){
        String relativeFileName = getRelativeFileName(notebookName,noteTitle);
        List<NoteVersionVo> noteVersionVoList = GitUtil.getNoteHistory(getOrCreateUserGit(),relativeFileName);
        return ServerResponse.buildSuccessResponse(noteVersionVoList);
    }

    /**
     * 获取历史文件内容
     * @param notebookName
     * @param noteTitle
     * @param versionRef
     * @return
     */
    @Override
    public ServerResponse<String> getNoteHistoryContent(String notebookName, String noteTitle, String versionRef) {
        String relativeFileName = getRelativeFileName(notebookName,noteTitle);
        String historyContent= GitUtil.getFileHistoryContent(getOrCreateUserGit(), relativeFileName, versionRef);
        return ServerResponse.buildSuccessResponse(historyContent);
    }

    /**
     * 从历史版本中恢复笔记
     * @param notebookName
     * @param noteTitle
     * @param versionRef
     * @return
     */
    @Override
    public ServerResponse<String> resetAndGet(String notebookName, String noteTitle, String versionRef){
        String relativeFileName = getRelativeFileName(notebookName,noteTitle);
        boolean result = GitUtil.resetAndCommit(getOrCreateUserGit(),relativeFileName,versionRef);
        if (!result){
            return ServerResponse.buildErrorResponse("Recover to history version failed");
        }
        invalidateCache(buildUserNoteKey(notebookName, noteTitle));
        return getNote(notebookName, noteTitle);
    }

    /**
     * 删除笔记本
     * @param notebookName
     * @return
     */
    @Override
    public ServerResponse deleteNotebook(String notebookName){
        File notebookDir = new File(getOrCreateUserNotebookDir(), notebookName);
        listNotes(notebookName).getData().forEach(noteVo -> deleteNote(notebookName, noteVo.getTitle()));
        fileService.deleteFile(notebookDir);
        GitUtil.rmAndCommit(getOrCreateUserGit(),notebookName + "/" + NOTEBOOK_FLAG_FILE);
        return ServerResponse.buildSuccessResponse();
    }

    /**
     * 重命名笔记本
     * @param srcNotebookName
     * @param targetNotebookName
     */
    @Override
    public void renameNotebook(String srcNotebookName, String targetNotebookName) {
        File srcNotebookDir = getNotebookDir(srcNotebookName);
        if (!srcNotebookDir.exists() || srcNotebookDir.isFile()) {
            throw new PromptException("笔记本不存在");
        }

        File targetNotebookDir = getNotebookDir(targetNotebookName);
        if (targetNotebookDir.exists()) {
            throw new PromptException("目标笔记本已存在");
        }
//        if (!targetNotebookDir.mkdir()) {
//            throw new PromptException("目标笔记本无法创建");
//        }
        List<NoteVo> noteVoList = listNotes(srcNotebookName).getData();
        if (!FileUtil.renameFileOrDir(srcNotebookDir, targetNotebookDir)) {
            throw new PromptException("重命名笔记本失败");
        }
        GitUtil.rmAndCommit(getOrCreateUserGit(), srcNotebookName);
        GitUtil.addAndCommit(getOrCreateUserGit(), targetNotebookName);
        noteVoList.forEach(noteVo -> invalidateCache(buildUserNoteKey(srcNotebookName, noteVo.getTitle())));
        articleService.updateArticlesNotebookName(srcNotebookName, targetNotebookName);
    }

    /**
     * 获取回收站文章列表
     * @return
     */
    @Override
    public ServerResponse<List<DeletedNoteVo>> listDelNotes(){
        List<DeletedNoteVo> deletedNoteList = new ArrayList<>();
        delNoteRepository.findAllByUsername(getUsername())
                .forEach(delNoteDo -> deletedNoteList.add(new DeletedNoteVo()
                    .setId(delNoteDo.getId())
                    .setTitle(delNoteDo.getTitle())
                    .setNotebook(delNoteDo.getNotebook())
                    .setLastRef(delNoteDo.getLastRef())
                    .setUsername(delNoteDo.getUsername())
                    .setContent(delNoteDo.getContent())
        ));
        return ServerResponse.buildSuccessResponse(deletedNoteList);
    }

    /**
     * 清除回收站表中的文章信息
     * @param id
     * @return
     */
    @Override
    public ServerResponse clearDelNote(@NonNull Integer id){
        delNoteRepository.deleteByIdAndUsername(id, getUsername());
        return ServerResponse.buildSuccessResponse();
    }

    /**
     * 复制笔记
     * @param srcNotebook
     * @param targetNotebook
     * @param title
     * @return
     */
    @Override
    public ServerResponse copyNote(String srcNotebook, String targetNotebook, String title) {
        //判断是否已经存在相同的笔记
        if (srcNotebook.equals(targetNotebook)){
            throw new RuntimeException("Same notebook");
        }
        //若获取源文件失败 则直接返回失败信息
        ServerResponse<String> response = getNote(srcNotebook, title);
        if (!response.isSuccess()){
            return response;
        }
        //从读取到的源文件中获取笔记内容
        String content = response.getData();
        //将文章信息写进新笔记中
        return createNote(title, targetNotebook, content);
    }

    /**
     * 移动笔记
     * @param srcNotebook
     * @param srcTitle
     * @param targetNotebook
     * @param targetTitle
     * @return
     */
    @Override
    public ServerResponse moveNote(String srcNotebook, String srcTitle, String targetNotebook, String targetTitle){
        // src != target
        if (srcNotebook.equalsIgnoreCase(targetNotebook) && srcTitle.equalsIgnoreCase(targetTitle)){
            throw new IllegalArgumentException();
        }
        if (StringUtils.isAnyBlank(srcNotebook, srcTitle, targetNotebook, targetTitle)){
            throw new IllegalArgumentException();
        }
        ServerResponse<String> response = getNote(srcNotebook, srcTitle);
        if (!response.isSuccess()){
            return response;
        }
        String content = response.getData();
        String targetRelativeName = getRelativeFileName(targetNotebook, targetTitle);
        File targetFile =  new File(getOrCreateUserNotebookDir(), targetRelativeName);
        if (targetFile.exists()){
            throw new RuntimeException("Note already exists");
        }
        String srcRelativeName = getRelativeFileName(srcNotebook, srcTitle);
        File srcFile = new File(getOrCreateUserNotebookDir(), srcRelativeName);
        fileService.deleteFile(srcFile);
        fileService.writeStringToFile(content, targetFile);
        NotePreviewInfo previewInfo = userNotePreviewCache.get(buildUserNoteKey(srcNotebook, srcTitle, getUsername()));
        // 先移动记录
        if (previewInfo.getArticleId() != null) {
            articleService.moveArticle(previewInfo.getArticleId(), targetNotebook, targetTitle);
        }
        GitUtil.mvAndCommit(getOrCreateUserGit(), srcRelativeName, targetRelativeName);
        invalidateCache(buildUserNoteKey(srcNotebook, srcTitle));
        userNoteCache.put(buildUserNoteKey(targetNotebook, targetTitle), content);
        return ServerResponse.buildSuccessResponse();
    }

    /**
     * 清空回收站 直接删除回收站中的笔记信息
     * @return
     */
    @Override
    @Transactional
    public ServerResponse clearAllDelNotes(){
        delNoteRepository.deleteAllByUsername(getUsername());
        return ServerResponse.buildSuccessResponse();
    }

    //获取相对文件名 = 笔记本名/笔记标题.md
    private String getRelativeFileName(String notebookName, String noteTitle) {
        return notebookName + "/" + noteTitle+".md";
    }

    @Override
    public File getOrInitUserNotebookDir() {
        File dir =  getOrCreateUserNotebookDir();
        GitUtil.checkGitDir(dir);
        return null;
    }

    private File getOrCreateUserNotebookDir(){
        File dir = new File(notesDir, getUsername());
        if (dir.exists()){
            return dir;
        }
        dir.mkdir();
        return dir;
    }

    //从本地线程中获取用户名
    private String getUsername(){
        return ThreadLocalUtil.getUsername();
    }

    //从本地线程中获取笔记本
    private File getUserNotebookDir(){
        File dir = new File(notesDir, ThreadLocalUtil.getUsername());
        if (dir.exists()){
            return dir;
        }
        return null;
    }

    //创建Git 调用用户笔记本的地址
    public Git getOrCreateUserGit(){
        return GitUtil.getOrInitGit(getOrCreateUserNotebookDir());
    }

    /**
     * 判断扩展名 筛选markdown格式的文件
     * @param filename
     * @return
     */
    private boolean checkExtension(String filename){
        return filename.endsWith(".md")
                || filename.endsWith(".MD")
                || filename.endsWith(".mD")
                || filename.endsWith(".Md");
    }

    /**
     * 根据note信息 建立数据传输对象 用于cache的查找
     * @param notebookName
     * @param noteTitle
     * @return
     */
    private UserNoteKey buildUserNoteKey(String notebookName, String noteTitle){
        return buildUserNoteKey(notebookName, noteTitle, getUsername());
    }

    private UserNoteKey buildUserNoteKey(String notebookName, String noteTitle, String username){
        return new UserNoteKey().setNotebookName(notebookName).setNoteTitle(noteTitle).setUsername(username);
    }


    /**
     * 记录搜索时keyWord命中次数
     * @param target
     * @param substr
     * @return
     */
    private int subStrCount(String target, String substr){
      int count = 0;
      int startIndex = 0;
      int searchIndex;
      while ((searchIndex = target.indexOf(substr, startIndex)) != -1){
          count ++;
          startIndex = searchIndex + substr.length();
        }
      return count;
    }

    /**
     * 文件改动之后要撤销缓存 重新载入
     * @param key
     */
    void invalidateCache(UserNoteKey key){
        userNotePreviewCache.invalidate(key);
        userNoteCache.invalidate(key);
    }

    File getNotebookDir(String notebookName) {
        return new File(getOrCreateUserNotebookDir(), notebookName);
    }
}
