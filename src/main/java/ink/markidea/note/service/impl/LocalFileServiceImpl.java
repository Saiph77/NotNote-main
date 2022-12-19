package ink.markidea.note.service.impl;

import ink.markidea.note.entity.exception.PromptException;
import ink.markidea.note.entity.vo.UserFileVo;
import ink.markidea.note.service.IFileService;
import ink.markidea.note.util.DateTimeUtil;
import ink.markidea.note.util.FileUtil;
import ink.markidea.note.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class LocalFileServiceImpl implements IFileService {

    @Value("${fileDir}")
    private String filePath;

    private final String DIR_PREFIX = "/file/";

    //上传文件 返回文件名
    @Override
    public String upload(MultipartFile sourceFile) {
        File targetFile = uploadFile(sourceFile);
        //上传到当前用户的文件夹
        return DIR_PREFIX + getUsername() + "/" + targetFile.getName();
    }

    /**
     * 上传文件到服务器目录
     * @param sourceFile
     * @return targetFile 返回上传之后的文件
     */
    @Override
    public File uploadFile(MultipartFile sourceFile) {
        String filename = sourceFile.getOriginalFilename();

        //获取扩展名
        String fileExtensionName = filename.substring(filename.indexOf(".")+1);
        //防止可能上传同样文件名的文件 TODO
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        File userDir  = getOrInitUserFileDirectory();
        File targetFile = new File(userDir, uploadFileName);

        //上传文件至目标目录
        try {
            sourceFile.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException("upload file failed");
        }

        return targetFile;
    }

    @Override
    public boolean writeStringToFile(String content, File targetFile){
        return FileUtil.writeStringToFile(content, targetFile);
    }

    @Override
    public File createTmpEmptyDir() {
        return null;
    }

    @Override
    public String getContentFromFile(File file){
        return FileUtil.readFileAsString(file);
    }

    @Override
    public void deleteFile(File file){
        FileUtil.deleteFileOrDirectory(file);
    }

    @Override
    public void batchDelete(List<String> fileNames) {
        fileNames.forEach(fileName -> {
            File file = new File(getOrInitUserFileDirectory(), fileName);
            deleteFile(file);
        });
    }

    /**
     * 分页展示用户目录
     * @Param
     * @Return 用户文件夹的具体信息
     */
    @Override
    public UserFileVo listUserFiles(int pageIndex, int pageSize) {

        //获取文件的初始目录 this.getOrInitUserFileDirectory()
        File userDir = getOrInitUserFileDirectory();
        //整个用户文件夹为一个大文件数组 每个子文件有一个pageIndex和pageSize 来对应 文件/文件夹 在，目录中的位置
        File[] userFiles = userDir.listFiles();//获取用户的多级目录
        if (userFiles == null || userFiles.length == 0){
            return new UserFileVo().setPageSize(pageSize);
        }

        //计算目录页数
        int pageNum = userFiles.length / pageSize;
        if (userFiles.length % pageSize != 0){
            pageNum ++;
        }
        //封装目录信息
        UserFileVo userFileVo = new UserFileVo()
                .setTotalSize(userFiles.length)
                .setPageNum(pageNum)
                .setPageSize(pageSize);
        //按照改动时间排序
        Arrays.sort(userFiles,
                (file1, file2) -> (int) (file2.lastModified() / 1000 - file1.lastModified() / 1000));
        //判断页号是否合法
        if ((pageIndex - 1) * pageSize >= userFiles.length){
            return userFileVo;
        }

        //UserFileVo存文件目录信息(Page) FileDetailVo 存为文件的具体信息(文件名 文件大小 最后修改时间)
        //创建一个集合来保存目录下的文件具体信息
        List<UserFileVo.FileDetailVo> fileDetailVoList = new ArrayList<>();

        //这个for循环只遍历本页的文件信息
        for (int i = (pageIndex -1) * pageSize; i < userFiles.length && i < pageIndex * pageSize; i++) {
            File file = userFiles[i];
            UserFileVo.FileDetailVo detailVo = new UserFileVo.FileDetailVo()
                    .setFileName(file.getName())
                    .setFileSize(FileUtil.getFileSizeStr(file))
                    .setLastModifiedTime(DateTimeUtil.dateToStr(new Date(file.lastModified())));
            fileDetailVoList.add(detailVo);
        }

        //在数据库中保存文件信息 及其子文件的信息
        return userFileVo.setPageIndex(pageIndex).setFileDetailList(fileDetailVoList);
    }

    private String getUsername(){
        return ThreadLocalUtil.getUsername();
    }

    private File getOrInitUserFileDirectory(){
        File userDir = new File(filePath, getUsername());
        if (!userDir.exists() && !userDir.mkdir()){
            throw new PromptException("创建文件夹失败");
        }
        return userDir;
    }
}
