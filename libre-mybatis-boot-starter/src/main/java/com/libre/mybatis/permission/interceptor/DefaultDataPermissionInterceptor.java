package com.libre.mybatis.permission.interceptor;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.libre.mybatis.permission.handler.LibreDataPermissionHandler;
import lombok.EqualsAndHashCode;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import java.sql.SQLException;


/**
 * @author zhao.cheng
 * @date 2021/4/20 13:42
 */
@EqualsAndHashCode(callSuper = true)
public class DefaultDataPermissionInterceptor extends DataPermissionInterceptor {

    private final LibreDataPermissionHandler libreDataPermissionHandler;

    public DefaultDataPermissionInterceptor(LibreDataPermissionHandler libreDataPermissionHandler) {
        this.libreDataPermissionHandler = libreDataPermissionHandler;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) {
            return;
        }
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(parserSingle(mpBs.sql(), ms.getId()));
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        libreDataPermissionHandler.getSqlSegment(plainSelect , (String) obj);
    }


}
