package ink.markidea.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class NoteApplication {
    //http://127.0.0.1:18090/index.html#/login
    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }
}
