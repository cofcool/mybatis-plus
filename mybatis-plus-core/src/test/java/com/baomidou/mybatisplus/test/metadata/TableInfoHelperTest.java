package com.baomidou.mybatisplus.test.metadata;

import static org.assertj.core.api.Assertions.assertThat;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TableInfoHelperTest {

    @Data
    private static class BaseModel {

        private Long id;

        private String test;
    }


    @Data
    private static class ModelOne {

        @Id
        private Long id;

        private String name;
    }

    @Data
    private static class ModelTwo {

        private Long id;

        private String name;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @Table
    private static class ModelThree extends BaseModel {

        private String sex;

        private String name;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @Table
    private static class ModelFour extends BaseModel {

        private String sex;

        private String name;
    }


    @Test
    void testIsExistTableId() {
        Assertions.assertThat(TableInfoHelper.isExistTableId(Arrays.asList(ModelOne.class.getDeclaredFields()))).isTrue();
        assertThat(TableInfoHelper.isExistTableId(Arrays.asList(ModelTwo.class.getDeclaredFields()))).isFalse();
    }


    @Test
    void testMoreTableId() {
        Exception ex = null;
        try {
            TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), ModelFive.class);
        } catch (Exception e) {
            ex = e;
        }
        assertThat(ex).isNotNull();
        assertThat(ex).isInstanceOf(MybatisPlusException.class);
        System.out.println(ex.getMessage());
    }

    @Test
    void testPriorityTableId() {
        TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), ModelSex.class);
        assertThat(tableInfo.havePK()).isTrue();
        assertThat(tableInfo.getKeyProperty()).isEqualTo("realId");
    }

    @Test
    void testVersion() {
        Exception ex = null;
        try {
            TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Versions.class);
        } catch (Exception e) {
            ex = e;
        }
        assertThat(ex).isNotNull();
        assertThat(ex).isInstanceOf(MybatisPlusException.class);
        System.out.println(ex.getMessage());
    }

    @Test
    void testLogic() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        GlobalConfig config = GlobalConfigUtils.defaults();
        config.getDbConfig().setLogicDeleteField("logic");
        GlobalConfigUtils.setGlobalConfig(configuration, config);
        TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(configuration, ""), Logic.class);
        assertThat(tableInfo.isWithLogicDelete()).isTrue();
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        List<TableFieldInfo> logic = fieldList.stream().filter(TableFieldInfo::isLogicDelete).collect(Collectors.toList());
        assertThat(logic.size()).isEqualTo(1);
        assertThat(logic.get(0).getProperty()).isEqualTo("deleted");
    }

    @Test
    void testColumnFormat() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        GlobalConfig config = GlobalConfigUtils.defaults();
        config.getDbConfig().setColumnFormat("pxx_%s");
        GlobalConfigUtils.setGlobalConfig(configuration, config);
        TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(configuration, ""), Logic.class);
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        fieldList.forEach(i -> {
            assertThat(i.getColumn()).startsWith("pxx_");
        });
    }

    @Data
    private static class ModelFive {

        @Id
        private String id1;

        @Id
        private String id2;
    }

    @Data
    private static class ModelSex {

        @Id
        private String realId;
    }

    @Data
    private static class Logic {

        private Integer logic;

        @TableLogic
        private Integer deleted;
    }

    @Data
    private static class Versions {

        @Version
        private Integer version1;
        @Version
        private Integer version2;
    }
}
