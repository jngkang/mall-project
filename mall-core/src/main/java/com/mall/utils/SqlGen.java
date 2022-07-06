package com.mall.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.mall.annotation.Column;
import com.mall.annotation.EQ;
import com.mall.annotation.IN;
import com.mall.annotation.Ignore;
import com.mall.annotation.Like;
import com.mall.annotation.NE;
import com.mall.annotation.PK;
import com.mall.annotation.Table;
import com.mall.model.AbstractQuery;
import com.mall.model.Range;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Slf4j
public class SqlGen {
    public static final String SELECT = "select";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";

    private static Map<String, String> genSql = new HashMap<>();

    /**
     * 需要过滤掉的列
     */
    private static final HashSet<String> pageFields = new HashSet<String>() {
        {
            add("pageNum");
            add("pageSize");
        }
    };

    /**
     * 拼接查询SQL
     *
     * @param query 查询条件的参数
     * @return java.lang.String 返回对应的SQL语句
     */
    public String select(AbstractQuery query) {
        // 获取query类名
        String queryClassName = query.getClass().getName();
        // TODO 根更据不同项目的规定，此处需要根据项目需求设置包路径
        // 根据query类获取实体类名
        String modelClassName = StrUtil.sub(queryClassName, 0, queryClassName.length() - 5).replace(".query", "");
        //String modelClassName = StrUtil.sub(queryClassName, 0, queryClassName.length() - 5).replace("query", "model");
        // 先去查询是否已经生成过SQL语句，如果没有再进行生成sql语句
        String res = genSql.get(modelClassName + SqlGen.SELECT);
        if (StrUtil.isNotBlank(res)) {
            return res;
        }
        log.info(modelClassName + "执行了" + SqlGen.SELECT + "方法");
        // 开始拼接
        final StringBuilder sb = new StringBuilder();
        // 注：<script></script>标签前后不能有空格
        sb.append("<script>");
        sb.append(" select ");
        // 根据实体类拼接查询字段
        Class<?> modeClass = ClassUtil.loadClass(modelClassName);
        Field[] fields0 = ReflectUtil.getFields(modeClass);
        for (Field field : fields0) {
            // 如果变量中添加了@Ignore注解则直接忽略掉
            Ignore ignore = AnnotationUtil.getAnnotation(field, Ignore.class);
            if (ignore != null) {
                continue;
            }
            String popName = field.getName();
            String columnName = popName;
            Column column = AnnotationUtil.getAnnotation(field, Column.class);
            if (column != null) {
                columnName = column.value();
            }
            if (columnName.equalsIgnoreCase(popName)) {
                sb.append(StrUtil.format(" {} ,", columnName, popName));
            } else {
                sb.append(StrUtil.format(" {} as {},", columnName, popName));
            }
        }
        sb.deleteCharAt(sb.toString().length() - 1);
        sb.append(" from ");
        // 拼接表名
        Table table = AnnotationUtil.getAnnotation(modeClass, Table.class);
        if (table == null) {
            table = AnnotationUtil.getAnnotation(query.getClass(), Table.class);
        }
        Assert.notNull(table, "{} 没有设置具体表名", modelClassName);
        sb.append(table.value());
        sb.append(" <where> ");
        // 拼接查询条件
        Field[] fields = ReflectUtil.getFields(query.getClass());
        for (Field field : fields) {
            // 过滤指定指定
            if (pageFields.contains(field.getName())) {
                continue;
            }
            // 判断此字段是否需要忽略
            Ignore ignore = AnnotationUtil.getAnnotation(field, Ignore.class);
            if (ignore != null) {
                continue;
            }
            String popName = field.getName();
            String columnName = popName;
            Column column = AnnotationUtil.getAnnotation(field, Column.class);
            if (column != null) {
                columnName = column.value();
            }
            Annotation[] anns = AnnotationUtil.getAnnotations(field, true);
            if (anns.length == 0 || (anns.length == 1 && column != null)) {
                if (!ClassUtil.isAssignable(Range.class, field.getType())) {
                    sb.append(StrUtil.format(" <if test='{} != null'><![CDATA[ and {} = #{{}} ]]></if> ", popName, columnName, popName));
                    continue;
                }
            }
            if (ClassUtil.isAssignable(Range.class, field.getType())) {
                sb.append(StrUtil.format("" +
                        " <choose>" +
                        "<when test='{} != null and {}.left != null and {}.leftEQ == true'>" +
                        "<![CDATA[  and {} >= #{{}.left} ]]>" +
                        "</when>" +
                        "<when test='{} != null and {}.left != null and {}.leftEQ == false'>" +
                        "<![CDATA[  and {} > #{{}.left} ]]>" +
                        "</when>" +
                        "</choose> ", popName, popName, popName, columnName, popName, popName, popName, popName, columnName, popName, popName, popName, popName, columnName, popName, popName, popName, popName, columnName, popName));
                sb.append(StrUtil.format("" +
                        " <choose>" +
                        "<when test='{} != null and {}.right != null and {}.rightEQ == true'>" +
                        "<![CDATA[  and {} <= #{{}.right} ]]>" +
                        "</when>" +
                        "<when test='{} != null and {}.right != null and {}.rightEQ == false'>" +
                        "<![CDATA[  and {} < #{{}.right} ]]>" +
                        "</when>" +
                        "</choose> ", popName, popName, popName, columnName, popName, popName, popName, popName, columnName, popName, popName, popName, popName, columnName, popName, popName, popName, popName, columnName, popName));
            }
            EQ EQ = AnnotationUtil.getAnnotation(field, com.mall.annotation.EQ.class);
            if (EQ != null) {
                sb.append(StrUtil.format(" <if test='{} != null'><![CDATA[ and {} = #{{}} ]]></if> ", popName, columnName, popName));
            }
            NE NE = AnnotationUtil.getAnnotation(field, com.mall.annotation.NE.class);
            if (NE != null) {
                sb.append(StrUtil.format(" <if test='{} != null'><![CDATA[ and {} != #{{}} ]]></if> ", popName, columnName, popName));
            }
            IN IN = AnnotationUtil.getAnnotation(field, com.mall.annotation.IN.class);
            if (IN != null) {
                if (field.getType().isAssignableFrom(List.class)) {
                    sb.append(StrUtil.format("" +
                            " <if test='{} != null and {}.size() >0 '> and ({} in " +
                            "<foreach item='item' index='index' collection='{}' open='(' close=')'>" +
                            "<choose>" +
                            "<when test='index == 0'>#{item}</when>" +
                            "<when test='index % 1000 == 0'>) OR {} IN( #{item}</when>" +
                            "<otherwise>,#{item}</otherwise>" +
                            "</choose>" +
                            "</foreach>)" +
                            "</if> ", popName, popName, columnName, popName, columnName));
                } else {
                    sb.append(StrUtil.format(" <if test='{} != null and {}.length > 0'>" +
                            " and ({} in " +
                            "<foreach item='item' index='index' collection='{}' open='('  close=')'>" +
                            "<choose>" +
                            "<when test='index == 0'>#{item}</when>" +
                            "<when test='index % 1000 == 0'>) OR {} IN ( #{item}</when>" +
                            "<otherwise>,#{item}</otherwise>" +
                            "</choose>" +
                            "</foreach>)" +
                            "</if> ", popName, popName, columnName, popName, columnName));
                }
            }
            Like LIKE = AnnotationUtil.getAnnotation(field, Like.class);
            if (LIKE != null) {
                sb.append(StrUtil.format(" <if test=\"{} != null and {} !=''\"><![CDATA[ AND {} like concat('%',#{{}},'%') ]]></if> ", popName, popName, columnName, popName));
            }
        }
        sb.append("</where>");
        sb.append(StrUtil.format(" <if test=\"{} != null and {} !=''\"><![CDATA[ order by ${{}} ]]></if> ", "orderByBlock", "orderByBlock", "orderByBlock"));
        sb.append("</script>");
        // 注：<script></script>标签前后不能有空格
        String finalSql = sb.toString().trim();
        // 保存新生成的sql语句
        genSql.put(modelClassName + SqlGen.SELECT, finalSql);
        return finalSql;
    }

    /**
     * 生成插入SQL
     *
     * @param model 待插入的数据
     * @return java.lang.String 返回对应的SQL语句
     */
    public String insert(Object model) {
        String modelClassName = model.getClass().getName();
        // 先去查询是否已经生成过sql语句，如果没有再进行生成sql语句
        String res = genSql.get(modelClassName + SqlGen.INSERT);
        if (StrUtil.isNotBlank(res)) {
            return res;
        }
        log.info(modelClassName + "执行了" + SqlGen.INSERT + "方法");
        // 开始拼接
        final StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append(" insert into ");
        // 拼接表名
        Class<?> modeClass = model.getClass();
        String table = AnnotationUtil.getAnnotationValue(modeClass, Table.class, "value");
        Assert.notEmpty(table, "{} 没有设置具体表名", modeClass);
        sb.append(table);
        // 拼接列名
        sb.append(" ( ");
        Field[] fields0 = ReflectUtil.getFields(modeClass);
        for (Field field : fields0) {
            Ignore ignore = AnnotationUtil.getAnnotation(field, Ignore.class);
            if (ignore != null) {
                continue;
            }
            String popName = field.getName();
            String columnName = popName;
            Column column = AnnotationUtil.getAnnotation(field, Column.class);
            if (column != null) {
                columnName = column.value();
            }
            PK pk = AnnotationUtil.getAnnotation(field, PK.class);
            if (pk != null && pk.autoIncrement() == true) {
                continue;
            }
            sb.append(StrUtil.format(" {},", columnName));
        }
        sb.deleteCharAt(sb.toString().length() - 1);
        // 拼接值
        sb.append(" ) values ( ");
        for (Field field : fields0) {
            Ignore ignore = AnnotationUtil.getAnnotation(field, Ignore.class);
            if (ignore != null) {
                continue;
            }
            String popName = field.getName();
            String columnName = popName;
            Column column = AnnotationUtil.getAnnotation(field, Column.class);
            if (column != null) {
                columnName = column.value();
            }
            PK pk = AnnotationUtil.getAnnotation(field, PK.class);
            if (pk != null && pk.autoIncrement() == true) {
                continue;
            }
            sb.append(StrUtil.format(" #{{}},", popName));
        }
        sb.deleteCharAt(sb.toString().length() - 1);
        sb.append(" ) ");
        sb.append("</script>");
        // 注：<script></script>标签前后不能有空格
        String finalSql = sb.toString().trim();
        // 保存新生成的sql语句
        genSql.put(modelClassName + SqlGen.INSERT, finalSql);
        return finalSql;
    }

    /**
     * 生成更新SQL
     *
     * @param model 待更新的数据
     * @return java.lang.String 返回对应的SQL语句
     */
    public String update(Object model) {
        String modelClassName = model.getClass().getName();
        // 先去查询是否已经生成过sql语句，如果没有再进行生成sql语句
        String res = genSql.get(modelClassName + SqlGen.UPDATE);
        if (StrUtil.isNotBlank(res)) {
            return res;
        }
        log.info(modelClassName + "执行了" + SqlGen.UPDATE + "方法");
        // 开始拼接
        final StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append(" update ");
        // 拼接表名
        Class<?> modeClass = model.getClass();
        String table = AnnotationUtil.getAnnotationValue(modeClass, Table.class, "value");
        Assert.notEmpty(table, "{} 没有设置具体表名", modeClass);
        sb.append(table);
        // 拼接赋值语句
        sb.append(" set ");
        Field[] fields0 = ReflectUtil.getFields(modeClass);
        String pkWhere = StrUtil.EMPTY;
        for (Field field : fields0) {
            Ignore ignore = AnnotationUtil.getAnnotation(field, Ignore.class);
            if (ignore != null) {
                continue;
            }
            String popName = field.getName();
            String columnName = popName;
            Column column = AnnotationUtil.getAnnotation(field, Column.class);
            if (column != null) {
                columnName = column.value();
            }
            PK pk = AnnotationUtil.getAnnotation(field, PK.class);
            if (pk != null) {
                pkWhere = StrUtil.format(" where {} = #{{}} ", columnName, popName);
                continue;
            }
            sb.append(StrUtil.format(" {} = #{{}},", columnName, popName));
        }
        // 删除最后一个逗号
        sb.deleteCharAt(sb.toString().length() - 1);
        sb.append(pkWhere);
        sb.append("</script>");
        // 注：<script></script>标签前后不能有空格
        String finalSql = sb.toString().trim();
        // 保存新生成的sql语句
        genSql.put(modelClassName + SqlGen.UPDATE, finalSql);
        return finalSql;
    }
}