package com.yyq.es_retreatment.util;

import com.yyq.es_retreatment.entity.EnAndCh;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class FileUtils {
    public static void writeToFile(String englishPath,String chinesePath, List<EnAndCh> enAndChList)throws Exception{
        FileWriter writerEnglish = new FileWriter(englishPath,true);
        FileWriter writerChinese = new FileWriter(chinesePath,true);
        BufferedWriter bwEn = new BufferedWriter(writerEnglish);
        BufferedWriter bwCh = new BufferedWriter(writerChinese);
        for (EnAndCh enAndCh : enAndChList){
            bwEn.write(enAndCh.getEnglish());
            bwEn.newLine();
            bwCh.write(enAndCh.getChinese());
            bwCh.newLine();
        }
        bwEn.close();
        bwCh.close();
        writerEnglish.close();
        writerChinese.close();
    }

//    public static void main(String[] args)throws Exception {
//        writeToFile("",new String[]{"1212","werwer","我们见得"});
//    }
}
