package com.yyq.es_retreatment;

import com.yyq.es_retreatment.controller.SearchController;
import com.yyq.es_retreatment.entity.EnAndCh;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsRetreatmentApplicationTests {
    @Autowired
    SearchController searchController;
    @Test
    public void contextLoads() {
        List<EnAndCh> dataByPage = searchController.getDataByPage("en-us", 48L, 52L, 1, 20);
        System.out.println(dataByPage);
    }

}
