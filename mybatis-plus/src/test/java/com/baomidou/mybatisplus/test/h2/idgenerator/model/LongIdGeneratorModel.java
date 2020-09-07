package com.baomidou.mybatisplus.test.h2.idgenerator.model;

import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "t_id_generator_long")
public class LongIdGeneratorModel {

    private Long id;

    private String name;

    public LongIdGeneratorModel(String name) {
        this.name = name;
    }
}
