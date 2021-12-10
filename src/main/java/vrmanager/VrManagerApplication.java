package vrmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("vrmanager.mapper")
public class VrManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VrManagerApplication.class, args);
    }

}
