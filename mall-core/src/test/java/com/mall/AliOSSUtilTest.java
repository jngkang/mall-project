package com.mall;

import com.mall.utils.AliOSSUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

class AliOSSUtilTest {

    @Test
    void listFileName() {
        List<String> res = AliOSSUtil.listFileName("");
        res.forEach(System.out::println);
    }

}