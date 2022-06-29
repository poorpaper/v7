package com.poorpaper.v7userservice;

import com.poorpaper.api.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class V7UserServiceApplicationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void encodeTest() {
        // MD5 不可逆
        String encode1 = passwordEncoder.encode("123456");
        String encode2 = passwordEncoder.encode("123456");
        System.out.println(encode1);
        System.out.println(encode2);
    }

    @Test
    public void decodeTest() {
        System.out.println(passwordEncoder.matches("123456", "$2a$10$xpW/0cqEGPUQ/pXylcaQKO3hL78eKtvw/hOy/rHubIvLA3P3paci6"));
        System.out.println(passwordEncoder.matches("123456", "$2a$10$gp4Jk5pr68Kc7Fo/GFha1OhCZvl7j4EQ0JxmJ89iPmKURh6s/MTMa"));
    }
}
