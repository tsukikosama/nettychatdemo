package org.example.nettychart;

import lombok.extern.slf4j.Slf4j;
import org.example.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@Slf4j
@SpringBootApplication
public class NettychartApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(NettychartApplication.class);
        NettyServer.start();
    }


}
