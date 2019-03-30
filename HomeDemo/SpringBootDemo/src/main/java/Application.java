import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 托管包
 */
//@SpringBootApplication->@springbootApplication 注解失效的情况下，推荐使用@CompentScan 和@EnableAutoConfiguration进行代替；
@EnableSwagger2
@EnableAutoConfiguration
@ComponentScan("com")
public class Application {
    public static void main(String  []args){
        SpringApplication.run(Application.class,args);
    }
}
