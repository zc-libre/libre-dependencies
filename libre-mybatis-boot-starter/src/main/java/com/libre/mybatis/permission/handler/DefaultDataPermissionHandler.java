package com.libre.mybatis.permission.handler;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.libre.core.toolkit.ClassUtil;
import com.libre.core.toolkit.StringUtil;
import com.libre.mybatis.annotation.DataPermission;
import com.libre.mybatis.permission.enums.DataPermissionEnum;
import com.libre.mybatis.permission.prop.DataPermissionProperties;
import com.libre.mybatis.permission.provider.DataPermissionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author zhao.cheng
 * @date 2021/4/20 13:45
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultDataPermissionHandler implements LibreDataPermissionHandler, ApplicationContextAware {

    private final JdbcTemplate jdbcTemplate;
    private final ConcurrentMap<String, DataPermission> dataAuthMap = new ConcurrentHashMap<>(8);
    private final DataPermissionProperties dataPermissionProperties;
    private final DataPermissionProcessor dataPermissionProcessor;
    private ApplicationContext applicationContext;

    @Override
    public void  getSqlSegment(PlainSelect plainSelect, String mappedStatementId) {
        if (!dataPermissionProperties.getEnabled()) {
            return;
        }
        log.info("plainSelect: {}, mappedStatementId: {}", plainSelect, mappedStatementId);
        DataPermission dataPermission = findDataAuthAnnotation(mappedStatementId);
        if (dataPermission == null) {
            return;
        }
        String column = dataPermission.column();
        int type = dataPermission.type().getType();
        String[] fields = dataPermission.fields();
        Expression where = plainSelect.getWhere();

        // 数据权限如果是所有,直接返回
        if(type == DataPermissionEnum.ALL.getType()) {
            return;
        }

        setSelectItems(fields, plainSelect);
        DataPermissionEntity dataPermissionEntity = new DataPermissionEntity(column, type, Lists.newArrayList(fields));
        dataPermissionEntity =  dataPermissionProcessor.processor(dataPermissionEntity);
        AndExpression andExpression = new AndExpression(where, buildExpression(dataPermissionEntity));
        setExpression(andExpression, plainSelect);
    }

    private Expression buildExpression(DataPermissionEntity dataPermissionEntity) {

        if (dataPermissionEntity.getPermissionType().equals(DataPermissionEnum.OWN.getType())) {
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column(dataPermissionEntity.getPermissionColumn()));
            equalsTo.setRightExpression(new LongValue(dataPermissionEntity.getValues().get(0)));
            return equalsTo;
        }

        List<Long> values = dataPermissionEntity.getValues();
        ItemsList itemsList = new ExpressionList(values.stream().map(value -> {
            StringValue stringValue = new StringValue();
            stringValue.setValue(String.valueOf(value));
            return stringValue;
        }).collect(Collectors.toList()));
        return new InExpression(new Column(dataPermissionEntity.getPermissionColumn()), itemsList);
    }

    private void setSelectItems(String[] fields, PlainSelect plainSelect) {
        if (ArrayUtil.isNotEmpty(fields) && !fields[0].equals(StringPool.STAR)) {
            List<SelectItem> selectItems = Lists.newArrayList();
            for (String field : fields) {
                SelectExpressionItem selectExpressionItem = new SelectExpressionItem(new Column(field));
                selectItems.add(selectExpressionItem);
            }
            plainSelect.setSelectItems(selectItems);
        }
    }
    private void setExpression(Expression expression, PlainSelect plainSelect) {
        if (expression != null) {
            plainSelect.setWhere(expression);
        }
    }

    private DataPermission findDataAuthAnnotation(String mappedStatementId) {
        return dataAuthMap.computeIfAbsent(mappedStatementId, (key) -> {

            String className = key.substring(0, key.lastIndexOf(StringPool.DOT));
            String mapperBean = StringUtil.firstCharToLower(ClassUtil.getShortName(className));
            Object mapper = applicationContext.getBean(mapperBean);
            String methodName = key.substring(key.lastIndexOf(StringPool.DOT) + 1);
            Class<?>[] interfaces = ClassUtil.getAllInterfaces(mapper);
            for (Class<?> mapperInterface : interfaces) {
                for (Method method : mapperInterface.getDeclaredMethods()) {
                    if (methodName.equals(method.getName()) && method.isAnnotationPresent(DataPermission.class)) {
                        return method.getAnnotation(DataPermission.class);
                    }
                }
            }
            return null;
        });
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
