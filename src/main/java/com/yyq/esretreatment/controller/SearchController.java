package com.yyq.esretreatment.controller;

import com.yyq.esretreatment.entity.repository.EnAndCh;
import com.yyq.esretreatment.repository.DomainRepository;
import com.yyq.esretreatment.repository.EnAndChRepository;
import com.yyq.esretreatment.util.FileUtils;
import com.yyq.esretreatment.util.IOtools;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author XiangChao
 * @date 2018/10/11
 */
@RestController
@Component
public class SearchController {

    @Autowired
    DomainRepository domainRepository;

    @Autowired
    EnAndChRepository repo;

    /**
     * 获取es数据存到txt
     *
     * @param indexName
     * @param domain
     * @param subDomain
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/get_data")
    public ResponseEntity getData(@RequestParam("indexName") String indexName, @RequestParam("domain") Long domain,
                                  @RequestParam(value = "subDomain",required = false) Long subDomain) throws Exception {
        int maxSize = 10000;
        Integer total = getTotal(indexName, domain, subDomain, 12, maxSize);
        String fullSubSpecialtyName = domainRepository.findBypecialtyId(subDomain);
        String fullSpecialtyName = domainRepository.findBypecialtyId(domain);
        String dir = "D:\\language\\" + fullSpecialtyName + "\\";
        FileUtils.createDir(dir);
        String pathEn = dir + fullSubSpecialtyName + "en.txt";
        String pathCh = dir + fullSubSpecialtyName + "ch.txt";

        if(subDomain==null){
            List<Object[]> subDomainNames = domainRepository.getSubDomainNames(domain);
           for(Object[] r:subDomainNames){
               pathEn = dir + r[1] + "en.txt";
               pathCh = dir + r[1] + "ch.txt";
               for (int i = 0; i <= total / maxSize; i++) {
                   List<EnAndCh> dataByPage = getDataByPage(indexName, domain, Long.valueOf((Integer)r[0]), i, maxSize);
                   try {
                       FileUtils.writeToFile(pathEn, pathCh, dataByPage);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           }
        }else {
            for (int i = 0; i <= total / maxSize; i++) {
                List<EnAndCh> dataByPage = getDataByPage(indexName, domain, subDomain, i, maxSize);
                try {
                    FileUtils.writeToFile(pathEn, pathCh, dataByPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ResponseEntity.ok().body(1);
    }

    /**
     * 根据subDomain下载对应的英文txt
     *
     * @param request
     * @param response
     * @param subDomain
     * @throws Exception
     */
    @GetMapping(value = "/downloads_en")
    public void downloadsEn(HttpServletRequest request, HttpServletResponse response, @RequestParam("subDomain") Long subDomain) throws Exception {
        String fullSpecialtyName = domainRepository.findBypecialtyId(subDomain);
        String pathEn = "D:\\language\\" + domainRepository.findPNameBySpecialtyId(String.valueOf(subDomain)) + "\\" + fullSpecialtyName + "en.txt";
        downloadFile(request, response, pathEn);
    }

    /**
     * 根据subDomain下载对应的中文txt
     *
     * @param request
     * @param response
     * @param subDomain
     * @throws Exception
     */
    @GetMapping(value = "/downloads_ch")
    public void downloadsCh(HttpServletRequest request, HttpServletResponse response, @RequestParam("subDomain") Long subDomain) throws Exception {
        String fullSpecialtyName = domainRepository.findBypecialtyId(subDomain);
        String pathCh = "D:\\language\\" + domainRepository.findPNameBySpecialtyId(String.valueOf(subDomain)) + "\\" + fullSpecialtyName + "ch.txt";
        downloadFile(request, response, pathCh);

    }
    @PostMapping(value = "/handle_file")
    public void handleFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileName") MultipartFile file) throws Exception {
        if(file.isEmpty()){
            return;
        }
        /**
         * 将文件转化为text
         */
//        InputStreamReader inputReader = new InputStreamReader(file.getInputStream());
//        BufferedReader bf = new BufferedReader(inputReader);
//        // 按行读取字符串
//        String str;
//        while ((str = bf.readLine()) != null) {
//
//        }
//        bf.close();
//        inputReader.close();

        /**
         * 下载文件
         */
        response.setContentType("application/force-download");
        //设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("GBK"), "ISO8859-1"));
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(file.getInputStream());
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 根据domain下载对应的压缩包
     *
     * @param request
     * @param response
     * @param domain
     * @throws Exception
     */
    @GetMapping(value = "/downloads_ch_batch")
    public void batchDownloadsCh(HttpServletRequest request, HttpServletResponse response, @RequestParam("domain") Long domain) throws Exception {
        String fullSpecialtyName = domainRepository.findBypecialtyId(domain);
        String path = "D:\\language\\" + fullSpecialtyName + ".rar";
        ArrayList<File> files = IOtools.getFiles("D:\\language\\" + fullSpecialtyName);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(path)));
        IOtools.zipFile(files, zipOutputStream);
        zipOutputStream.close();
        IOtools.downloadFile(new File(path), response, false);
    }


    public List<EnAndCh> getDataByPage(String indexName, Long domain, Long subDomain, Integer pageNo, Integer pageSize) {
        MatchQueryBuilder queryBuilderDomain = new MatchQueryBuilder("domain", domain);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (domain != null) {
            boolQueryBuilder.must(queryBuilderDomain);
        }
        if (subDomain != null) {
            MatchQueryBuilder queryBuilderSubDomain = new MatchQueryBuilder("sub_domain", subDomain);
            boolQueryBuilder.must(queryBuilderSubDomain);
        }
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        PageRequest page = null;
        if (pageNo != null && pageSize != null) {
            page = PageRequest.of(pageNo, pageSize);
        }
        nativeSearchQueryBuilder.withPageable(page);
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Page<EnAndCh> pages = repo.search(query);
        List<EnAndCh> content = pages.getContent();
        return content;
    }

    public Integer getTotal(String indexName, Long domain, Long subDomain, Integer pageNo, Integer pageSize) {
        MatchQueryBuilder queryBuilderDomain = new MatchQueryBuilder("domain", domain);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (domain != null) {
            boolQueryBuilder.must(queryBuilderDomain);
        }
        if (subDomain != null) {
            MatchQueryBuilder queryBuilderSubDomain = new MatchQueryBuilder("sub_domain", subDomain);
            boolQueryBuilder.must(queryBuilderSubDomain);
        }
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        PageRequest page = null;
        if (pageNo != null && pageSize != null) {
            page = PageRequest.of(pageNo, pageSize);
        }
        nativeSearchQueryBuilder.withPageable(page);
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Page<EnAndCh> pages = repo.search(query);

        int total = (int) pages.getTotalElements();


        return total;
    }

    /**
     * 下载文件
     *
     * @param request
     * @param response
     * @param fileName
     * @return
     * @throws Exception
     */
    private String downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {

        if (fileName != null) {
            //设置文件路径
            File file = new File(fileName);
            int length = fileName.split("\\\\").length;
            fileName = fileName.split("\\\\")[length - 1];
            if (file.exists()) {
                //设置强制下载不打开
                response.setContentType("application/force-download");
                //设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }


}
