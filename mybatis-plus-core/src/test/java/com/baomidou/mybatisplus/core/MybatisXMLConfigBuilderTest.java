package com.baomidou.mybatisplus.core;

import static org.assertj.core.api.Assertions.assertThat;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.io.IOException;
import javax.persistence.Table;
import lombok.Data;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * @author miemie
 * @since 2020-06-15
 */
class MybatisXMLConfigBuilderTest {

    @Test
    void parse() throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:/MybatisXMLConfigBuilderTest.xml");
        MybatisXMLConfigBuilder builder = new MybatisXMLConfigBuilder(resource.getInputStream(), null);
        Configuration configuration = builder.parse();
        assertThat(configuration).isInstanceOf(MybatisConfiguration.class);
        assertThat(configuration.getMappedStatement("com.baomidou.mybatisplus.core.MybatisXMLConfigBuilderTest$EntityMapper.selectCount"))
            .isNotNull();
    }

    interface EntityMapper extends BaseMapper<Entity> {

    }

    @Data
    @Table
    static class Entity {
        private Long id;
        private String name;
    }
}
