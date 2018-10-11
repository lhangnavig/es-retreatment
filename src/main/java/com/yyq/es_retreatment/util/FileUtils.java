package com.yyq.es_retreatment.util;

import com.yyq.es_retreatment.entity.repository.EnAndCh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class FileUtils {
    public static void writeToFile(String englishPath, String chinesePath, List<EnAndCh> enAndChList) throws Exception {
        FileWriter writerEnglish = new FileWriter(englishPath, true);
        FileWriter writerChinese = new FileWriter(chinesePath, true);
        BufferedWriter bwEn = new BufferedWriter(writerEnglish);
        BufferedWriter bwCh = new BufferedWriter(writerChinese);
        for (EnAndCh enAndCh : enAndChList) {
            //过滤不符规定的数据
            boolean filter = !StringUtils.isHaveEnglishWord(enAndCh.getChinese()) && enAndCh.getChinese().length() > 4 && !StringUtils.isHaveChineseWord(enAndCh.getEnglish()) && enAndCh.getEnglish().split(" ").length > 4;
            if (filter) {
                bwEn.write(enAndCh.getEnglish());
                bwEn.newLine();
                bwCh.write(enAndCh.getChinese());
                bwCh.newLine();
            }
        }
        bwEn.close();
        bwCh.close();
        writerEnglish.close();
        writerChinese.close();
    }

    /**
     * 创建文件夹
     *
     * @param dirPath
     */
    public static void createDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void main(String[] args) {
        System.out.println("d:\\aaa\\ddf.txt".split("\\\\")[2]);
    }

}
