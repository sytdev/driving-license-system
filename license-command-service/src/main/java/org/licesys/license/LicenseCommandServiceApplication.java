package org.licesys.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.licesys.*")
public class LicenseCommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseCommandServiceApplication.class, args);
    }

}
