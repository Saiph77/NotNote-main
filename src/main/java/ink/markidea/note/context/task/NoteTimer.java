package ink.markidea.note.context.task;

import ink.markidea.note.dao.UserRepository;
import ink.markidea.note.entity.UserDo;
import ink.markidea.note.util.GitUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务
 */
@Component
@EnableScheduling
@Slf4j
public class NoteTimer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Value("${notesDir}")
    private String notesDir;

    @Value("${sshKeysDir}")
    private String sshKeysDir;

    private volatile boolean isPushing = false;

    //任务列表
    private volatile List<PushToRemoteTask> pushToRemoteTaskList = new ArrayList<>();

    @Scheduled(fixedRate = 10 * 1000, initialDelay = 3 * 1000)//每三秒钟推送一次
    public void pushLocalChangesToRemote(){
        try{
            if (isPushing){
                return ;
            }
            isPushing = true;
            //将每个任务
            pushToRemoteTaskList.forEach(Runnable::run);
        }finally {
            isPushing = false;
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        refreshPushTaskList();
    }

    public synchronized void refreshPushTaskList() {

        List<PushToRemoteTask> newTaskList = new ArrayList<>();
        userRepository.findAll().stream()
                .filter(userDO -> userDO.isPush() && StringUtils.isNotBlank(userDO.getRemoteRepository()))
                .forEach(
                userDO -> {
                    PushToRemoteTask task = new PushToRemoteTask()
                            .setUsername(userDO.getUsername())
                            .setLocalRepoPath(new File(notesDir, userDO.getUsername()).getAbsolutePath())//本地仓库地址
                            .setRemoteRepoUrl(userDO.getRemoteRepository())//远程仓库地址
                            .setPrivateKeyPath(new File(sshKeysDir, userDO.getUsername() + ".prv").getAbsolutePath());//私钥地址
                    newTaskList.add(task);
                }
            );
        this.pushToRemoteTaskList = newTaskList;
    }

    public Boolean checkPushTaskStatus(String username){
        for (PushToRemoteTask task : pushToRemoteTaskList){
            if (task.getUsername().equals(username)){
                return task.getStatus();
            }
        }
        return null;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    private static class PushToRemoteTask implements Runnable {

        //用户名
        private String username;

        //sshKey地址
        private String privateKeyPath ;

        //远程仓库地址
        private String remoteRepoUrl ;

        //本地仓库地址
        private String localRepoPath ;

        //推送至远程仓库状态 成功为true 失败为false
        private volatile Boolean status = true;

        @Override
        public void run() {
            try{
                //获取或初始化本地仓库
                Git git = GitUtil.getOrInitGit(localRepoPath);
                //设置远程仓库及分支
                GitUtil.setRemoteRepositoryAndBranch(git,remoteRepoUrl);
                //推送至远程仓库
                GitUtil.pushToRemoteViaSsh(git, privateKeyPath);
                status = true;
            }catch (Exception e){
                log.error("push local changes to remote: {} failed", remoteRepoUrl, e);
                status = false;
            }
        }
    }
}
