package com.yyq.es_retreatment.repository;

import com.yyq.es_retreatment.entity.repository.EnAndCh;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface EnAndChRepository extends ElasticsearchRepository<EnAndCh, String> {

}
