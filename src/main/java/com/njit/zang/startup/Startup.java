package com.njit.zang.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Startup implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
            log.info("welcome to topic_discuss ：)");
    }
}
