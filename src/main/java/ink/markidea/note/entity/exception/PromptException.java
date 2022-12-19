package ink.markidea.note.entity.exception;

/**
 * 统一异常处理
 */
public class PromptException extends RuntimeException {
    public PromptException(String message) {
        super(message);
    }
}
