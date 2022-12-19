package ink.markidea.note.util;

/**
 * 保存当前用户的登陆信息
 */
public class ThreadLocalUtil {

    private static ThreadLocal<String> threadLocalUsername = new ThreadLocal<>();

    public static void setUsername(String username) {
        threadLocalUsername.set(username);
    }

    public static String getUsername(){
        return threadLocalUsername.get();
    }

    public static void clearUsername(){
        threadLocalUsername.remove();
    }
}
