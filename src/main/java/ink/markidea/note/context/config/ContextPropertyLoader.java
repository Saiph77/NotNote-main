package ink.markidea.note.context.config;

import com.fasterxml.jackson.core.type.TypeReference;
import ink.markidea.note.entity.dto.WebsiteConfigDto;
import ink.markidea.note.util.FileUtil;
import ink.markidea.note.util.JsonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户目录地址创建配置
 */
public class ContextPropertyLoader implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ApplicationHome home = new ApplicationHome(getClass());

        Map<String,Object> map = new HashMap<>();

        String path;
        path = home.getDir().getAbsolutePath();
        addPathAndInitDir(map, path);
        // PropertySource 是一个name/value 的property对。
        // MapPropertySource是一个单个的key-Map<name, properties>的结构
        PropertySource propertySource = new MapPropertySource("markidea-sys",map);
        environment.getPropertySources().addLast(propertySource);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 9;
    }

    private void addPathAndInitDir(Map<String,Object> map, String basePath){
        map.put("baseDir",basePath);

        //存储数据库表
        File dbDir = new File(basePath, "db");
        checkAndCreateDirIfNecessary(dbDir);
        map.put("dbDir", dbDir.getAbsolutePath());

        //存储sshkey
        File prvKeysDir = new File(basePath, ".ssh");
        checkAndCreateDirIfNecessary(prvKeysDir);
        map.put("sshKeysDir", prvKeysDir.getAbsolutePath());

        //用户笔记根文件夹
        File notesDir = new File(basePath, "notes");
        checkAndCreateDirIfNecessary(notesDir);
        map.put("notesDir", notesDir.getAbsolutePath());

        //本地资源存放路径
        File staticDir = new File(basePath,"static");
        checkAndCreateDirIfNecessary(staticDir);
        map.put("staticDir", staticDir.getAbsolutePath());

        //前端资源
        File frontDir = new File(basePath, "front");
        checkAndCreateDirIfNecessary(frontDir);
        transferFrontResourceIfNecessary(new ClassPathResource("front.zip"), frontDir);
        map.put("frontDir", frontDir.getAbsolutePath());

        //用户文件上传保存路径
        File fileDir = new File(staticDir, "file");
        checkAndCreateDirIfNecessary(fileDir);
        map.put("fileDir", fileDir.getAbsolutePath());

        //配置文件
        File configDir = new File(staticDir, "config");
        checkAndCreateDirIfNecessary(configDir);
        map.put("configDir", configDir.getAbsolutePath());

        //加载网络配置信息
        File websiteConfigFile = new File(configDir, "website-config.json");
        WebsiteConfigDto websiteConfig;
        if (websiteConfigFile.exists()) {
            String jsonStr = FileUtil.readFileAsString(websiteConfigFile);
            websiteConfig = JsonUtil.stringToObj(jsonStr, WebsiteConfigDto.class);
            File indexHtmlFile = new File(staticDir, "index.html");
            String indexHtmlStr  = FileUtil.readFileAsString(indexHtmlFile);
            String newIndexHtml = indexHtmlStr.replace("<title>" + "MarkIdea" + "</title>", "<title>" + "NotNote" + "</title>");
            FileUtil.writeStringToFile(newIndexHtml, indexHtmlFile);
        } else {
            websiteConfig = new WebsiteConfigDto();
        }

        Map<String, String> configProperties = JsonUtil.stringToObj(JsonUtil.objToString(websiteConfig), new TypeReference<Map<String, String>>() {});
        assert configProperties != null;
        map.putAll(configProperties);


    }

    //解压文件方法
    private void transferFrontResourceIfNecessary(ClassPathResource resource, File frontDir) {
        if (frontDir.listFiles() != null && frontDir.listFiles().length > 0){
            FileUtil.deleteChildFiles(frontDir);
        }
        if (!resource.exists()){
            return ;
        }
        try {
            FileUtil.unzip(resource.getInputStream(), frontDir.getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException("Can't init front dir");
        }
    }

    private void checkAndCreateDirIfNecessary(File dir) {
        if (dir.exists()){
            if (dir.isDirectory()){
                return ;
            }
            throw new IllegalStateException("not a directory");
        }

        if (!dir.mkdir()){
            throw new IllegalStateException("create a directory failed");

        }
    }
}
