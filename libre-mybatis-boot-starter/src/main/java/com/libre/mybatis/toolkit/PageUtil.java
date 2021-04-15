package com.libre.mybatis.toolkit;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author zhao.cheng
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class PageUtil {

    public static <T> Page<T> toPage(IPage<?> page, Class<T> target) {
        if (ObjectUtils.isEmpty(page)) {
            return null;
        }
        List<T> records = new ArrayList<>();
        Assert.notNull(page.getRecords(), "pages must not be null!");
        for (Object record : page.getRecords()) {
            records.add((T) record);
        }
        return toPage(page, records);
    }

    public static <T> Page<T> toPage(IPage<?> page, List<T> records) {
        Page<T> pageResult = new Page();
        pageResult.setCurrent(page.getCurrent());
        pageResult.setSize(page.getSize());
        pageResult.setPages(page.getPages());
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(records);
        return pageResult;
    }


    public static <T, R> Page<R> toPage(IPage<T> page, Function<T, R> function) {
        List<R> records = new ArrayList<>();
        for (T record : page.getRecords()) {
            records.add(function.apply(record));
        }
        return toPage(page, (List)records);
    }
}
