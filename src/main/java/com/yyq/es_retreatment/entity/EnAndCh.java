package com.yyq.es_retreatment.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "en-us",type="data")
public class EnAndCh {

    @Id
    private String id;

    private String domain;
    private String sub_domain;
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

    public String getSub_domain() {
        return sub_domain;
    }

    public void setSub_domain(String sub_domain) {
        this.sub_domain = sub_domain;
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
                ", sub_domain='" + sub_domain + '\'' +
                ", chinese='" + chinese + '\'' +
                ", english='" + english + '\'' +
                '}';
    }
}
