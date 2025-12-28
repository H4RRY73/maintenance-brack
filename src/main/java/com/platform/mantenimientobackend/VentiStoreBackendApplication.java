package com.platform.mantenimientobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VentiStoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VentiStoreBackendApplication.class, args);
    }

}
