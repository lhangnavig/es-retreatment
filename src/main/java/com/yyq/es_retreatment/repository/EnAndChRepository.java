package com.yyq.es_retreatment.repository;

import com.yyq.es_retreatment.entity.EnAndCh;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface EnAndChRepository extends ElasticsearchRepository<EnAndCh, String> {

}
