package com.libre.mybatis;

import com.libre.mybatis.config.DataPermissionAutoConfiguration;
import com.libre.mybatis.config.LibreMyBatisAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author zhao.cheng
 * @date 2021/4/20 14:37
 */
@SpringBootTest(classes = {LibreMyBatisAutoConfiguration.class, DataPermissionAutoConfiguration.class})
public class MyBatisTests {

    @Test
    void test() {

    }
}
