package com.yyq.esretreatment.repository;

import com.yyq.esretreatment.entity.repository.EnAndCh;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface EnAndChRepository extends ElasticsearchRepository<EnAndCh, String> {

}
