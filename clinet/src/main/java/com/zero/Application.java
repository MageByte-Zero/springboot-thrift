package com.zero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.CountDownLatch;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
@EnableScheduling
public class Application {

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        final CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                closeLatch.countDown();
            }
        });
        try {
            closeLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}