package com.yyq.esretreatment;

import com.yyq.esretreatment.controller.SearchController;
import com.yyq.esretreatment.entity.repository.EnAndCh;
import com.yyq.esretreatment.repository.DomainRepository;
import com.yyq.esretreatment.util.FileUtils;
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
    @Autowired
    DomainRepository domainRepository;

    @Test
    public void contextLoads() throws Exception {
        Integer total = searchController.getTotal("en-us", 48L, 52L, 12, 10000);

        for (int i = 0; i <= total / 10000; i++) {
            List<EnAndCh> dataByPage = searchController.getDataByPage("en-us", 48L, 52L, i, 10000);
            System.out.println(dataByPage);
            String pathEn = "D:\\english.txt";
            String pathCh = "D:\\chinese.txt";
            FileUtils.writeToFile(pathEn, pathCh, dataByPage);
        }

    }

    @Test
    public void test() throws Exception {
        List<Object[]> subDomainNames = domainRepository.getSubDomainNames(48L);
        subDomainNames.forEach(r->{
            System.out.println(r[0]);
            System.out.println(r[1]);
        });
    }


    public static void main(String[] args) {
        String str = "Even using the best acne medicines and cleansers won't get the job done";
        System.out.println(str.split(" ").length);
    }

}
