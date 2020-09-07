package com.baomidou.mybatisplus.test.h2.idgenerator.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "t_id_generator_int_string")
public class IntegerStringIdGeneratorModel {

    @Id
    @GeneratedValue
    private String id;

    private String name;

    public IntegerStringIdGeneratorModel(String name) {
        this.name = name;
    }
}
