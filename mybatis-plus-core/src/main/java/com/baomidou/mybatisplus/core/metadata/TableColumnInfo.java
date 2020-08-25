package com.baomidou.mybatisplus.core.metadata;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import java.lang.reflect.Field;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 处理 {@code jakarta.persistence-api} 相关注解
 */
@Getter
public class TableColumnInfo {

    private final Id id;
    private final GeneratedValue generatedValue;

    private final ColumnInfo column;

    private final boolean persistence;

    protected TableColumnInfo(Id id, GeneratedValue generatedValue, ColumnInfo column, Transient aTransient) {
        Objects.requireNonNull(column);

        this.id = id;
        this.generatedValue = generatedValue;
        this.persistence = aTransient == null;
        this.column = column;
    }

    /**
     *
     * @param field
     * @return
     */
    public static TableColumnInfo from(Field field) {
        return new TableColumnInfo(field.getAnnotation(Id.class), field.getAnnotation(GeneratedValue.class), new ColumnInfo(field), field.getAnnotation(Transient.class));
    }

    /**
     * 是否是主键
     * @return 是否是主键
     */
    public boolean isPrimaryKey() {
        return id != null;
    }

    /**
     * 获取字段名
     * @return 字段名
     */
    public String getColumnName() {
        return column.getName();
    }

    /**
     * 映射关系:
     * <pre>
     *     TABLE, SEQUENCE -> IdType.ASSIGN_ID
     *     IDENTITY -> IdType.AUTO
     *     AUTO -> IdType.NONE
     * </pre>
     * @return 主键生成 ID 类型
     */
    public IdType getIdType() {
        if (generatedValue == null) {
            return IdType.NONE;
        }

        switch (generatedValue.strategy()) {
            case TABLE:
            case SEQUENCE:
                return IdType.ASSIGN_ID;
            case IDENTITY:
                return IdType.AUTO;
            case AUTO:
                return IdType.NONE;
            default:
                throw new IllegalStateException("do not support other strategy");
        }
    }

    public FieldFill fill() {
        return null;
    }

    public String update() {
        return null;
    }

    public JdbcType jdbcType() {
        return null;
    }

    public FieldStrategy insertStrategy() {
        return null;
    }

    public FieldStrategy updateStrategy() {
        return null;
    }

    public FieldStrategy whereStrategy() {
        return null;
    }

    public String condition() {
        return null;
    }

    public boolean select() {
        return false;
    }

    public boolean keepGlobalFormat() {
        return false;
    }

    public boolean javaType() {
        return false;
    }

    public String numericScale() {
        return null;
    }

    public Class<? extends TypeHandler> typeHandler() {
        return null;
    }

    @Data
    static class ColumnInfo {
        String name;

        public ColumnInfo(Field field) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                this.name = field.getName();
            } else {
                this.name = column.name();
            }
        }
    }
}
