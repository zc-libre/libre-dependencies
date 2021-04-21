package com.libre.mybatis.permission.handler;

import net.sf.jsqlparser.statement.select.PlainSelect;


/**
 * @author zhao.cheng
 * @date 2021/4/20 16:24
 */
public interface LibreDataPermissionHandler {

    /**
     * 获取数据权限 SQL 片段
     * @param plainSelect – 待执行 SQL Where 条件表达式
     * @param mappedStatementId mappedStatementId – Mybatis MappedStatement Id 根据该参数可以判断具体执行方法
     */
    void getSqlSegment(PlainSelect plainSelect, String mappedStatementId);


}
