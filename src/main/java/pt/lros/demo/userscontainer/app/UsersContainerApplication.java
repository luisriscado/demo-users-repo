package pt.lros.demo.userscontainer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pt.lros.demo.userscontainer")
public class UsersContainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersContainerApplication.class, args);
    }

}
