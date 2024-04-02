package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class NettychartApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(NettychartApplication.class);
        NettyServer.start();
    }


}
