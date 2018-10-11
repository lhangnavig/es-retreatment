package com.yyq.es_retreatment.entity.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * create by LiHang on 2018/10/10
 */
@Document(indexName = "en-us",type="data")
public class EnAndCh {

    @Id
    private String id;

    private String domain;
    private String subDomain;
    private String chinese;
    private String english;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Override
    public String toString() {
        return "EnAndCh{" +
                "id='" + id + '\'' +
                ", domain='" + domain + '\'' +
                ", subDomain='" + subDomain + '\'' +
                ", chinese='" + chinese + '\'' +
                ", english='" + english + '\'' +
                '}';
    }
}
