package com.yyq.esretreatment.entity.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * create by LiHang on 2018/10/10
 */
@Data
@Document(indexName = "en-us",type="data")
public class EnAndCh {

    @Id
    private String id;
    /**
     * 领域
     */
    private String domain;
    /**
     * 子领域
     */
    private String subDomain;
    /**
     * 中文句子
     */
    private String chinese;
    /**
     * 英文句子
     */
    private String english;

}
