package ink.markidea.note.context.config;

import com.github.benmanes.caffeine.cache.*;
import ink.markidea.note.entity.ArticleDo;
import ink.markidea.note.entity.dto.NotePreviewInfo;
import ink.markidea.note.entity.dto.UserNoteKey;
import ink.markidea.note.entity.vo.UserVo;
import ink.markidea.note.service.IArticleService;
import ink.markidea.note.service.IFileService;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig implements EnvironmentAware {

    /**
     * token失效时间
     */
    private static volatile int tokenExpireTimeInHour;

    private static final long HOUR_DURATION_IN_NANO_SECONDS = 3600L * 1000L * 1000L;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    @Qualifier("userNoteCache")
    private LoadingCache<UserNoteKey, String> userNoteCache;

    @Value("${notesDir}")
    private String notesDir;

    /**创建一个 用户缓存（类似一个map）*/
    @Bean
    public Cache<String, UserVo> userCache(){
        return Caffeine.newBuilder()
                        .maximumSize(500)
                .expireAfter(new Expiry<String, UserVo>() {
                    //重写三个缓存过期的方法 设置缓存过期时间
                    @Override
                    public long expireAfterCreate(@NonNull String key, @NonNull UserVo value, long currentTime) {
                        return currentTime + tokenExpireTimeInHour * HOUR_DURATION_IN_NANO_SECONDS;
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull String key, @NonNull UserVo value, long currentTime, @NonNegative long currentDuration) {
                        return currentTime + tokenExpireTimeInHour * HOUR_DURATION_IN_NANO_SECONDS;
                    }

                    @Override
                    public long expireAfterRead(@NonNull String key, @NonNull UserVo value, long currentTime, @NonNegative long currentDuration) {
                        return currentTime + tokenExpireTimeInHour * HOUR_DURATION_IN_NANO_SECONDS;
                    }
                }).build();
    }

    /**用户Note缓存
     *
     * @return LoadingCache<UserNoteKey, String>
     *      UserNoteKey 笔记数据的key对象
     *      String 文章内容
     */
    @Bean("userNoteCache")
    public LoadingCache<UserNoteKey, String> userNoteCache(){
        return Caffeine.newBuilder()
                .maximumWeight(125 * 1024 * 1024)
                .weigher(new Weigher<UserNoteKey, String>() {
                    @Override
                    public @NonNegative int weigh(@NonNull UserNoteKey key, @NonNull String value) {
                        return value.length();
                    }
                })
                .expireAfterWrite(12, TimeUnit.HOURS)
                //将用户笔记内容通过(key)载入缓存
                .build(key -> loadNote(key.getUsername(), key.getNotebookName(), key.getNoteTitle()));
    }


    /**
     * 用户笔记预览缓存
     * 用于公开笔记内容的展示
     * @return
     */
    @Bean("userNotePreviewCache")
    public LoadingCache<UserNoteKey, NotePreviewInfo> userNotePreviewCache(){
        return Caffeine.newBuilder()
                .maximumWeight(10 * 1024 * 1024)
                .weigher(new Weigher<UserNoteKey, NotePreviewInfo>() {
                    @Override
                    public @NonNegative int weigh(@NonNull UserNoteKey key, @NonNull NotePreviewInfo value) {
                        return value.getPreviewContent() == null ? 0:value.getPreviewContent().length();
                    }
                })
                .expireAfterWrite(12, TimeUnit.HOURS)
                .build(this::loadPreview);
    }

    /**
     * 以String的形式 加载笔记内容 后面加载进缓存
     * @param username
     * @param notebookName
     * @param noteTitle
     * @return content 笔记内容
     */
    private String loadNote(String username, String notebookName, String noteTitle) {
        //获取用户笔记目录及文件名
        String relativeFileName = getRelativeFileName(notebookName,noteTitle);
        //获取用户笔记文件
        File noteFile = new File(getOrCreateUserNotebookDir(username), relativeFileName);
        if (!noteFile.exists()) {
            return  null;
        }
        String content = fileService.getContentFromFile(noteFile);
        return content;
    }

    /**
     * 载入预览
     * @param key
     * @return
     */
    private NotePreviewInfo loadPreview(UserNoteKey key){
        String content = userNoteCache.get(key);
        if (content == null) {
            return  null;
        }
        //从文章实体中获取文章信息
        ArticleDo articleDo = articleService.findByNotebookAndNoteTitle(key.getNotebookName(), key.getNoteTitle());
        //previewInfo读取content中60个字符内的内容
        NotePreviewInfo previewInfo =
                new NotePreviewInfo().setPreviewContent(content.substring(0, Math.min(60, content.length())));
        //在查询到文章实体的情况下 将文章id设置进预览信息
        if (articleDo != null) {
            previewInfo.setArticleId(articleDo.getId());
        }
        return previewInfo;
    }

    /**
     * 获取用户笔记目录
     * @param username
     * @return
     */
    private File getOrCreateUserNotebookDir(String username){
        File dir = new File(notesDir, username);
        if (dir.exists()){
            return dir;
        }
        dir.mkdir();
        return dir;
    }

    /**
     * 获取文件名
     * @param notebookName
     * @param noteTitle
     * @return
     */
    private String getRelativeFileName(String notebookName, String noteTitle) {

        return notebookName + "/" + noteTitle+".md";
    }

    public static void setTokenExpireTimeInHour(int tokenExpireTimeInHour) {
        CacheConfig.tokenExpireTimeInHour = tokenExpireTimeInHour;
    }

    @Override
    public void setEnvironment(Environment environment) {
        tokenExpireTimeInHour = Objects.requireNonNull(environment.getProperty("tokenExpireTimeInHour", Integer.class));
    }
}
