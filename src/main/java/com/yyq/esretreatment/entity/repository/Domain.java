package com.yyq.esretreatment.entity.repository;

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
    /**
     * 名称
     */
    private String specialtyName;
    /**
     * id
     */
    private Long specialtyId;
    /**
     * 全称
     */
    private String fullSpecialtyName;
    /**
     * 父id
     */
    private Long pSpecialtyId;
    /**
     * 是否可用(1 可用 0 不可用)
     */
    private Boolean state;
}
