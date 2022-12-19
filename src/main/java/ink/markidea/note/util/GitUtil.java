package ink.markidea.note.util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import ink.markidea.note.entity.exception.PromptException;
import ink.markidea.note.entity.vo.NoteVersionVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import static org.eclipse.jgit.diff.DiffEntry.Side.OLD;
import static org.eclipse.jgit.lib.ConfigConstants.*;

@Slf4j
public class GitUtil {


    /**
     * git分支更动类型
     */
    private interface ChangeType {
        int NEW_OR_MODIFY = 0;
        int NEW = 1;
        int MODIFY = 2;
        int MOVE = 3;
        int COPY = 4;
        int RECOVER = 5;
        int RESET = 6;//撤销
        int DELETE = 7;
    }

    private static final String DEFAULT_REMOTE_ALIAS_NAME = "origin";

    private static final String DEFAULT_LOCAL_BRANCH_NAME = "master";

    private static final String DEFAULT_REMOTE_BRANCH_NAME = "master";

    private static final String GIT_FLAG_FILE = ".markidea";

    private static Git createNewGit(String path) {
        File gitDir = new File(path);
        return createNewGit(gitDir);
    }

    public static Git getOrInitGit(String path) {
        File gitDir = new File(path);
        return getOrInitGit(gitDir);
    }

    public static Git getOrInitGit(File gitDir) {
        if (!gitDir.exists() && !gitDir.mkdir()) {
            return null;
        } else if (gitDir.isFile()) {
            return null;
        }
        //获取本地git
        Git git = getLocalGit(gitDir);
        //如果没有再创建新的git对象
        if (git == null) {
            git = createNewGit(gitDir);
        }
        return git;
    }

    //创建新git对象
    private static Git createNewGit(File gitDir) {
        try {
            //初始化git 必须传入一个git仓库路径
            Git git = Git.init().setDirectory(gitDir).call();
            File file = new File(gitDir, GIT_FLAG_FILE);
            file.createNewFile();
            //将新建的git文件提交 设置后缀为 GIT_FLAG_FILE
            addAndCommit(git, GIT_FLAG_FILE);
            return git;
        } catch (GitAPIException | IOException e) {
            return null;
        }
    }

    /**
     * 获取笔记的历史版本信息
     */
    public static List<NoteVersionVo> getNoteHistory(Git git, String fileName) {
        //获取笔记历史版本
        List<NoteVersionVo> noteVersionVoList = new ArrayList<>();
        //获取每次笔记提交对象
        List<RevCommit> revCommitList = getVersionHistory(git, fileName);

        Set<String> skippedRefs = new HashSet<>();

        for (RevCommit revCommit : revCommitList) {
            //创建笔记历史版本信息dto
            CommitMessage message;
            //使用JsonUtil转换信息格式
            message = JsonUtil.stringToObj(revCommit.getFullMessage(), CommitMessage.class);
            //保证非空
            if (message == null || message.getChangeType() == null){
                continue;
            }
            //如果有撤销操作 则将当前版本信息添加到
            if (message.getChangeType() == ChangeType.RESET) {
                skippedRefs.add(message.prevRef);
            }
            //如果有重复的提交 则跳过
            if (skippedRefs.contains(revCommit.getName())) {
                continue;
            }
            //提交修改过的笔记
            if (isContentChangeCommit(message.getChangeType(), fileName.equals(message.getFileName()))) {
                //加入版本信息
                noteVersionVoList.add(
                        new NoteVersionVo()
                        .setDate(DateTimeUtil.dateToStr(revCommit.getAuthorIdent().getWhen()))
                        .setRef(revCommit.getName()));
            }
        }
        return noteVersionVoList;
    }

    /**
     * 获取某个笔记提交保存的对象
     * RevCommit 是每次提交的笔记对象
     */
    private static List<RevCommit> getVersionHistory(Git git, String fileName) {
        List<RevCommit> list = new ArrayList<>();
        Iterable<RevCommit> iterable = null;
        try {
            //log：View commit history
            iterable = git.log().addPath(fileName).call();
        } catch (GitAPIException e) {
            return list;
        }
        iterable.forEach(list::add);
        return list;
    }

    /**
     * 加入暂存区并提交
     * @param git
     * @param fileName
     */
    public static boolean addAndCommit(Git git, String fileName) {
        try {
            git.add().addFilepattern(fileName).call();
            git.commit().setMessage(getCommitMsgStr(ChangeType.NEW_OR_MODIFY, fileName)).call();
            return true;
        } catch (GitAPIException | JGitInternalException e) {
            log.error("can't add file: {}, cause is: {}", fileName, e.getMessage());
            return false;
        }
    }

    /**
     * 移动文件操作
     * @param git
     * @param oldFilename
     * @param newFilename
     * @return
     */
    public static boolean mvAndCommit(Git git, String oldFilename, String newFilename) {
        try {
            //将新的文件加入暂存区
            git.add().addFilepattern(newFilename).call();
            //移除旧文件
            git.rm().addFilepattern(oldFilename).call();
            //设置changeType并提交
            git.commit().setMessage(getCommitMsgStr(ChangeType.MOVE, newFilename)).call();
            return true;
        } catch (GitAPIException | JGitInternalException e) {
            log.error("can't move file: {}, cause is: {}", oldFilename, e.getMessage());
            throw new RuntimeException("can't move");
        }
    }

    /**
     * 删除并提交
     * @param git
     * @param fileName
     */
    public static boolean rmAndCommit(Git git, String fileName) {
        try {
            git.rm().addFilepattern(fileName).call();
            git.commit().setMessage(getCommitMsgStr(ChangeType.DELETE, fileName)).call();
            return true;
        } catch (GitAPIException | JGitInternalException e) {
            log.error("can't remove file: {}, cause is: {}", fileName, e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * 回滚并提交
     * @param git
     * @param fileName
     * @param versionRef
     * @return
     */
    public static boolean resetAndCommit(Git git, String fileName, String versionRef) {
        try {
            //回滚到versionRef的版本
            git.reset().setRef(versionRef).addPath(fileName).call();
            //checkout 本意表示切换分支 这里也可以用作撤销修改
            /*
            放弃工作区中某个文件的修改：
            git checkout -- filename
            如果不指定切换到哪个分支，那就是切换到当前分支，
            虽然HEAD的指向没有变化，但是后面的两个恢复过程依然会执行，
            于是就可以理解为放弃index和工作区的变动。
             */
            git.checkout().addPath(fileName).call();
            //commit回滚信息
            git.commit().setMessage(JsonUtil.objToString(new CommitMessage().setChangeType(ChangeType.RESET).setFileName(fileName).setPrevRef(versionRef))).call();
            log.info("reset file: {} to version: {}", fileName, versionRef);
            return true;
        } catch (GitAPIException | JGitInternalException e) {
            log.error("can't reset file: {} to version: {}, cause is: {}", fileName, versionRef, e.getMessage());
            return false;
        }
    }

    /**
     * 恢复已被删除的笔记
     * @param git
     * @param fileName
     * @param lastRef
     */
    public static void recoverDeletedFile(Git git, String fileName, String lastRef) {
        try {
            //恢复到lastRef的分支（startPoint）
            git.checkout().setStartPoint(lastRef).addPath(fileName).call();
            //加入到暂存区
            git.add().addFilepattern(fileName).call();
            //提交改动信息
            git.commit()
                    .setMessage(JsonUtil.objToString(new CommitMessage().setChangeType(ChangeType.RECOVER).setFileName(fileName)))
                    .call();
            log.info("recover deleted file: {}", fileName);
        } catch (GitAPIException e) {
            log.error("Can't recover deleted file: {}", fileName, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param privateKeyPath 存储用户私钥
     */
    public static void pushToRemoteViaSsh(Git git, String privateKeyPath) throws GitAPIException {
        SshSessionFactory sshSessionFactory = new MyJshConfigSessionFactory(privateKeyPath);
        git.push().setTransportConfigCallback(transport -> {
            SshTransport sshTransport = (SshTransport) transport;
            sshTransport.setSshSessionFactory(sshSessionFactory);
            System.out.println("push");
        }).call();
    }

    public static void pullFromRemote(Git git, String privateKeyPath) throws GitAPIException {
        SshSessionFactory sshSessionFactory = new MyJshConfigSessionFactory(privateKeyPath);

        git.pull().setTransportConfigCallback(transport -> {
            SshTransport sshTransport = (SshTransport) transport;
            sshTransport.setSshSessionFactory(sshSessionFactory);
        }).call();
    }

    public static void pushToRemoteViaHttp(Git git, String username, String password) throws GitAPIException {
        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
    }


    public static void setRemoteRepository(Git git, String remoteAliasName, String remoteUrl) throws IOException {
        StoredConfig config = git.getRepository().getConfig();
        config.setString(CONFIG_REMOTE_SECTION, remoteAliasName, CONFIG_KEY_URL, remoteUrl);
        config.setString(CONFIG_REMOTE_SECTION, remoteAliasName, "fetch", "+refs/heads/*:refs/remotes/" + remoteAliasName + "/*");
        config.save();
    }

    public static void setRemoteRepository(Git git, String remoteUrl) throws IOException {
        setRemoteRepository(git, DEFAULT_REMOTE_ALIAS_NAME, remoteUrl);
    }

    public static void setRemoteBranch(Git git, String localBranch, String remoteAliasName, String remoteBranch) throws IOException {
        StoredConfig config = git.getRepository().getConfig();
        config.setString(CONFIG_BRANCH_SECTION, localBranch, CONFIG_KEY_REMOTE, remoteAliasName);
        config.setString(CONFIG_BRANCH_SECTION, localBranch, CONFIG_KEY_MERGE, "refs/heads/" + remoteBranch);
        config.save();
    }

    public static void setRemoteBranch(Git git) throws IOException {
        setRemoteBranch(git, DEFAULT_LOCAL_BRANCH_NAME, DEFAULT_REMOTE_ALIAS_NAME, DEFAULT_REMOTE_BRANCH_NAME);
    }

    //设置远程仓库和分支
    public static void setRemoteRepositoryAndBranch(Git git, String remoteUrl) throws IOException {
        setRemoteRepository(git, remoteUrl);
        setRemoteBranch(git);
    }

    //获取文件上一次被使用的信息
    public static String getFileCurRef(Git git, String fileName) {
        List<NoteVersionVo> noteVersionVoList = getNoteHistory(git, fileName);
        for (NoteVersionVo noteVersionVo : noteVersionVoList) {
            return noteVersionVo.getRef();
        }
        return null;
    }

    /**
     * 获取文件某个历史版本的内容
     */
    public static String getFileHistoryContent(Git git, String filePath, String ref) {
        List<RevCommit> revCommitList = getVersionHistory(git, filePath);
        String curRef = revCommitList.get(0).getName();
        //判断是否为当前版本
        if (ref.equals(curRef)) {
            throw new PromptException("当前版本无需预览");
        }
        //获取git仓库
        Repository repository = git.getRepository();
        try {
            //获取迭代器 遍历branch
            AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, ref);
            AbstractTreeIterator newTreeParser = prepareTreeParser(repository, curRef);
            //获取变更内容
            //查看工作区与暂存区内容的区别，使用无选项的git diff命令。
            //git diff file_name：获取指定文件的修改
            List<DiffEntry> diff = git
                    .diff()
                    .setOldTree(oldTreeParser)
                    .setNewTree(newTreeParser)
                    .setPathFilter(PathFilter.create(filePath))
                    .call();

            for (DiffEntry entry : diff) {
                try (DiffFormatter formatter = new DiffFormatter(System.out)) {
                    formatter.setRepository(repository);
                    Method method = DiffFormatter.class.getDeclaredMethod("open", DiffEntry.Side.class, DiffEntry.class);
                    method.setAccessible(true);
                    RawText rawText = (RawText) method.invoke(formatter, OLD, entry);
                    return new String(rawText.getRawContent());
                }
            }
            throw new RuntimeException();
        } catch (Exception e) {
            log.error("getHistory content error", e);
            throw new IllegalArgumentException();
        }
    }

    /**
     * 不再使用
     * temporarily quit to complete the method
     */
    @Deprecated
    public static List<CommitChangeType> getChangeTypeList(Git git, List<RevCommit> revCommitList, String fileName) throws GitAPIException, IOException {
        List<CommitChangeType> list = new ArrayList<>();
        Iterator<RevCommit> iterator = revCommitList.iterator();
        if (!iterator.hasNext()) {
            return Collections.emptyList();
        }
        RevCommit newCommit = iterator.next();
        ObjectReader reader = git.getRepository().newObjectReader();
        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        diffFormatter.setRepository(git.getRepository());
        while (iterator.hasNext()) {
            //^ + n 表示当前head之前第n次commit的版本
            ObjectId newTree = git.getRepository().resolve(newCommit.getName() + "^{tree}");
            newTreeIter.reset(reader, newTree);

            RevCommit oldCommit = iterator.next();
            ObjectId oldTree = git.getRepository().resolve(oldCommit.getName() + "^{tree}");
            oldTreeIter.reset(reader, oldTree);
            String finalRef = newCommit.getName();
            diffFormatter.scan(oldTreeIter, newTreeIter)
                    .stream()
                    .filter(entry -> entry.getNewPath().equals(fileName) || entry.getOldPath().equals(fileName))
                    .findFirst()
                    .ifPresent(entry -> list.add(new CommitChangeType(finalRef, entry.getChangeType())));

            newCommit = oldCommit;
        }

        list.add(new CommitChangeType(newCommit.getName(), DiffEntry.ChangeType.ADD));
        return list;

    }

    private static DiffEntry.ChangeType getChangeType(Git git, List<DiffEntry> entryList, String fileName) {
        for (DiffEntry entry : entryList) {
            if (entry.getNewPath().equals(fileName) || entry.getOldPath().equals(fileName)) {
                return entry.getChangeType();
            }
        }
        return null;
    }

    /**
     * ssh连接配置类
     */
    private static class MyJshConfigSessionFactory extends JschConfigSessionFactory {

        //    ssh-keygen -t rsa -m PEM
        //    do not support openssh
        //    https://stackoverflow.com/questions/53134212/invalid-privatekey-when-using-jsch
        /*
             解除HostKey检查，也就意味着可以接受未知的远程主机的文件，
            这是不安全的，这种模式只是用于测试为目的的。
            利用ssh-keyscan -t rsa hostname，收集主机数据。
　　　　 */
        private String privateKeyPath;

        public MyJshConfigSessionFactory(String privateKeyPath) {
            this.privateKeyPath = privateKeyPath;
        }

        @Override
        protected void configure(OpenSshConfig.Host hc, Session session) {
            // to solve UnknownHostKey Exception, but not secure
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
        }

        @Override
        protected JSch createDefaultJSch(FS fs) throws JSchException {
            JSch defaultJSch = super.createDefaultJSch(fs);
            defaultJSch.addIdentity(this.privateKeyPath);
            return defaultJSch;
        }
    }

    /**
     * 笔记提交信息dto
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class CommitMessage {

        private Integer changeType;

        private String fileName;

        private String oldFileName;

        private String newFileName;

        /**
         * the prev commit ref reset
         */
        private String prevRef;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Deprecated
    public static class CommitChangeType {

        private String ref;

        private DiffEntry.ChangeType changeType;

    }

    /**
     * 设置文件名及提交类型 用于commit
     * @param changeType
     * @param fileName
     * @return
     */
    private static String getCommitMsgStr(int changeType, String fileName) {
        return JsonUtil.objToString(new CommitMessage().setFileName(fileName).setChangeType(changeType));
    }

    //判断是否是内容上的修改
    private static boolean isContentChangeCommit(int changeType, boolean checkFilename) {
        return changeType == ChangeType.NEW_OR_MODIFY
                || changeType == ChangeType.NEW
                || changeType == ChangeType.MODIFY
                || changeType == ChangeType.RESET//回滚
                || (changeType == ChangeType.MOVE && checkFilename);
    }

    /**
     * 判断是否是git仓库
     *
     * @param path
     * @return
     */
    public static boolean isGitDir(String path) {
        return getLocalGit(path) == null;
    }

    public static  boolean checkGitDir(File file) {
        try {
            return Git.open(file) != null;
        }catch (IOException e) {
            return false;
        }
    }

    private static Git getLocalGit(String path) {
        File gitDirectory = new File(path);
        try {
            return Git.open(gitDirectory);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取本地git
     * @param gitDir
     * @return
     */
    private static Git getLocalGit(File gitDir) {
        try {
            return Git.open(gitDir);
        } catch (IOException e) {
            return null;
        }
    }

    //获取被更改但未被提交但数据
    public static Set<String> getModifiedButUnCommitted(Git git, String dirPath) {
        try {
            //获取当前状态
            Status status = git.status().addPath(dirPath).call();
            Set<String> modifiedSet = status.getModified();
            Set<String> unTrackedSet = status.getUntracked();
            if (unTrackedSet.isEmpty()) {
                return modifiedSet;
            } else if (modifiedSet.isEmpty()) {
                return unTrackedSet;
            }
            Set<String> mergedSet = new HashSet<>(modifiedSet);
            mergedSet.addAll(unTrackedSet);
            return mergedSet;
        } catch (GitAPIException e) {
            log.error("git status error", e);
            throw new RuntimeException("git status error");
        }
    }

    //撤销更改
    public static void discardChange(Git git, String path){
        try {
            //checkout有两种用法，第一种是切换分支，第二种是撤销修改
            //这里 只放弃工作区的改动，index 保持不变，其实就是从当前 index 恢复 工作区
            git.checkout().addPath(path).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成迭代器 用于遍历branch
     * @param repository
     * @param objectId
     * @return
     * @throws IOException
     */
    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        // no inspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }

}
