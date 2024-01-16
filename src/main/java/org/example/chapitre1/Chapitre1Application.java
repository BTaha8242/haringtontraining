package org.example.chapitre1;

import org.example.chapitre1.config.RsaKeysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeysConfig.class)
public class Chapitre1Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapitre1Application.class, args);
    }

}
