package com.yyq.esretreatment.repository;

import com.yyq.esretreatment.entity.repository.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by XiangChao on 2018/10/10
 */
@Component
public interface DomainRepository extends JpaRepository<Domain, String> {
    /**
     * 根据id查询领域实体
     * @param specialtyId
     * @return
     */
    Domain findDomainBySpecialtyId(Long specialtyId);

    /**
     * 根据id查询名称
     * @param specialtyId
     * @return
     */
    @Query(nativeQuery = true, value = "select full_specialty_name from domain where specialty_id = ?1")
    String findBypecialtyId(Long specialtyId);
    /**
     * 根据父id查询名称集合
     * @param pSpecialtyId
     * @return
     */
    @Query(nativeQuery = true, value = "select full_specialty_name from domain where p_specialty_id = ?1")
    List<String> findByPSecialtyId(Long pSpecialtyId);

    /**
     * 根据id查询父名称
     * @param subdomain
     * @return
     */
    @Query(nativeQuery = true, value = "select full_specialty_name from domain where specialty_id = (select p_specialty_id from domain where specialty_id = ?1)")
    String findPNameBySpecialtyId(String subdomain);

    /**
     * 根据父id查询所有字领域
     * @param domain
     * @return
     */
    @Query(nativeQuery = true,value = "select specialty_id,full_specialty_name from domain where p_specialty_id = ?1")
    List<Object[]> getSubDomainNames(Long domain);
}
