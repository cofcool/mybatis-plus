package com.baomidou.mybatisplus.test.h2.idgenerator.model;

import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "t_id_generator_int")
public class IntegerIdGeneratorModel {

    private Integer id;

    private String name;

    public IntegerIdGeneratorModel(String name) {
        this.name = name;
    }
}
