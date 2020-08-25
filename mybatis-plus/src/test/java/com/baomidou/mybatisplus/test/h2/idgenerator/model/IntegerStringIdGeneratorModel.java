package com.baomidou.mybatisplus.test.h2.idgenerator.model;

import com.baomidou.mybatisplus.annotation.TableName;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value = "t_id_generator_int_string")
public class IntegerStringIdGeneratorModel {

    @Id
    @GeneratedValue
    private String id;

    private String name;

    public IntegerStringIdGeneratorModel(String name) {
        this.name = name;
    }
}
