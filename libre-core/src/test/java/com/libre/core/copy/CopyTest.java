package com.libre.core.copy;

import cn.hutool.extra.cglib.CglibUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhao.cheng
 * @date 2021/4/16 9:22
 */
public class CopyTest {

    @Test
    public void copy() {
        SampleBean bean = new SampleBean();
        bean.setValue("Hello world");
        OtherSampleBean otherBean = new OtherSampleBean();
        CglibUtil.copy(bean, otherBean);
        Assert.assertEquals(bean.getValue(), otherBean.getValue());

    }

    @Test
    public void copyClass() {
        SampleBean bean = new SampleBean();
        bean.setValue("Hello world");
        OtherSampleBean otherBean2 = CglibUtil.copy(bean, OtherSampleBean.class);
        System.out.println(otherBean2);
        Assert.assertEquals(bean.getValue(), otherBean2.getValue());
    }
}
