package com.yyq.es_retreatment.repository;

import com.yyq.es_retreatment.entity.Domain;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

/**
 * create by XiangChao on 2018/10/10
 */
@Component
public interface DomainRepository extends JpaRepository<Domain,String> {

    Domain findDomainBySpecialtyId(Long specialtyId);

    @Query(nativeQuery=true,value="select full_specialty_name from domain where specialty_id = ?1")
    String findBypecialtyId(Long specialtyId);
}
