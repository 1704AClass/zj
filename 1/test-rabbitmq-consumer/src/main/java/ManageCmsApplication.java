import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by 周周 on 2020/2/11.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ningmeng.test"})
@ComponentScan(basePackages = {"com.ningmeng.frameword"})
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }
}
