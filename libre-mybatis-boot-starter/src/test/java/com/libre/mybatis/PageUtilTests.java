package com.libre.mybatis;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.libre.core.toolkit.JSONUtil;
import com.libre.mybatis.toolkit.PageUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author zhao.cheng
 * @date 2021/4/16 9:57
 */

public class PageUtilTests {

    @Test
    public void toPage() {
        Page<User> page = new Page<>();
        List<User> list = Lists.newArrayList();
        list.add(new User("1"));
        list.add(new User("2"));
        list.add(new User("3"));
        page.setRecords(list);
        page.setSize(10);

        Page<User> newPage = PageUtil.toPage(page, User.class);
        System.out.println(JSONUtil.toJson(newPage));
        Assert.assertNotNull(newPage);
    }
}
