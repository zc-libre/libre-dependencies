package com.libre.mybatis.toolkit;

import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.libre.core.convert.MicaConverter;
import org.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.List;
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
        T t = null;
        try {
            t = target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        T finalT = t;
        List<T> records = CglibUtil.copyList(page.getRecords(), () -> finalT);
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
