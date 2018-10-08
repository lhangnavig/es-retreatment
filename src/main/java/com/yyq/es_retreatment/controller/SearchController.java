package com.yyq.es_retreatment.controller;

import com.yyq.es_retreatment.entity.EnAndCh;
import com.yyq.es_retreatment.repository.EnAndChRepository;

import com.yyq.es_retreatment.util.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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
    EnAndChRepository repo;

    @GetMapping(value = "/get_data", produces = "application/json")
    public ResponseEntity getData(@RequestParam("indexName") String indexName, @RequestParam("domain") String domain,
                                  @RequestParam("subDomain") String subDomain) {
        return ResponseEntity.ok().body(null);
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
        int total = (int) pages.getTotalElements();
        System.out.println(total);
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
