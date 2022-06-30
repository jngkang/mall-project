package com.mall;

import com.mall.utils.EmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;

@SpringBootTest
class EmailUtilTest {

    @Resource
    EmailUtil emailUtil;

    @Test
    void email() {
        emailUtil.send(Collections.singletonList("wwk.jk@outlook.com"), "test", "test", false);
    }
}