package com.baomidou.mybatisplus.test.h2.idgenerator.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "t_id_generator_string")
public class StringIdGeneratorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    private String name;

    public StringIdGeneratorModel(String name) {
        this.name = name;
    }
}
