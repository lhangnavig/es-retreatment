package com.yyq.es_retreatment.controller;

import com.yyq.es_retreatment.entity.Domain;
import com.yyq.es_retreatment.entity.EnAndCh;
import com.yyq.es_retreatment.repository.DomainRepository;
import com.yyq.es_retreatment.repository.EnAndChRepository;
import com.yyq.es_retreatment.util.FileUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Component
public class SearchController {
    //    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    DomainRepository domainRepository;

    @Autowired
    EnAndChRepository repo;

    @GetMapping(value = "/get_data")
    public ResponseEntity getData(@RequestParam("indexName") String indexName, @RequestParam("domain") Long domain,
                                  @RequestParam("subDomain") Long subDomain) {
        Integer total = getTotal(indexName, domain, subDomain, 12, 10000);
        String fullSpecialtyName = domainRepository.findBypecialtyId(subDomain);
        String pathEn = "D:\\"+fullSpecialtyName+"en.txt";
        String pathCh = "D:\\"+fullSpecialtyName+"ch.txt";
        for (int i = 0; i <= total / 10000; i++) {
            List<EnAndCh> dataByPage = getDataByPage(indexName, domain, subDomain, i, 10000);
            try {
                FileUtils.writeToFile(pathEn, pathCh, dataByPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok().body("success");
    }

    public List<EnAndCh> getDataByPage(String indexName, Long domain, Long subDomain, Integer pageNo, Integer pageSize) {
        MatchQueryBuilder queryBuilderDomain = new MatchQueryBuilder("domain", domain);
        MatchQueryBuilder queryBuilderSubDomain = new MatchQueryBuilder("sub_domain", subDomain);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (domain != null) {
            boolQueryBuilder.must(queryBuilderDomain);
        }
        if (subDomain != null) {
            boolQueryBuilder.must(queryBuilderSubDomain);
        }
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        PageRequest page = null;
        if (pageNo != null && pageSize != null) {
            page = new PageRequest(pageNo, pageSize);
        }
        nativeSearchQueryBuilder.withPageable(page);
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Page<EnAndCh> pages = repo.search(query);
        List<EnAndCh> content = pages.getContent();
        return content;
    }

    public Integer getTotal(String indexName, Long domain, Long subDomain, Integer pageNo, Integer pageSize) {
        MatchQueryBuilder queryBuilderDomain = new MatchQueryBuilder("domain", domain);
        MatchQueryBuilder queryBuilderSubDomain = new MatchQueryBuilder("sub_domain", subDomain);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (domain != null) {
            boolQueryBuilder.must(queryBuilderDomain);
        }
        if (subDomain != null) {
            boolQueryBuilder.must(queryBuilderSubDomain);
        }
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        PageRequest page = null;
        if (pageNo != null && pageSize != null) {
            page = new PageRequest(pageNo, pageSize);
        }
        nativeSearchQueryBuilder.withPageable(page);
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Page<EnAndCh> pages = repo.search(query);

        int total = (int) pages.getTotalElements();


        return total;
    }

}
