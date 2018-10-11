package com.yyq.es_retreatment.entity.repository;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * create by XiangChao on 2018/10/10
 */
@Data
@Entity
public class Domain {
    @Id
    private String id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String specialtyName;
    private Long specialtyId;
    private String fullSpecialtyName;
    private Long pSpecialtyId;
    private Boolean state;
}
