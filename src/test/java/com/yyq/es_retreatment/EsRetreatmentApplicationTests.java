package com.yyq.es_retreatment;

import com.yyq.es_retreatment.controller.SearchController;
import com.yyq.es_retreatment.entity.EnAndCh;
import com.yyq.es_retreatment.util.FileUtils;
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
    public void contextLoads() throws Exception {
        Integer total = searchController.getTotal("en-us", 48L, 52L, 12, 1000);

        for (int i = 0; i <= total / 1000; i++) {
            List<EnAndCh> dataByPage = searchController.getDataByPage("en-us", 48L, 52L, i, 1000);
            System.out.println(dataByPage);
            String pathEn = "D:\\javaWorkStation\\es-retreatment\\src\\main\\resources\\english.txt";
            String pathCh = "D:\\javaWorkStation\\es-retreatment\\src\\main\\resources\\chinese.txt";
            FileUtils.writeToFile(pathEn,pathCh,dataByPage);
        }

    }

    public static void main(String[] args) {

    }

}
