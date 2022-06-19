package com.example.ssmdemo.Controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryExtensionsKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.w3c.dom.html.HTMLDirectoryElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.zip.ZipFile;

@Controller
@RequestMapping(value = "/file",produces = "application/json;charset=utf-8")
public class FileController {

    @RequestMapping(value = "/upFile",method = RequestMethod.POST)
    @ResponseBody
    public String upFile(HttpServletRequest request, HttpServletResponse response,@RequestParam("files") MultipartFile[] file) throws IOException {
        String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/files";
        MultipartFile multipartFile = null;
        int j =0;
        if (file != null && file.length > 0) {

            for (int i = 0; i < file.length; ++i) {
                multipartFile = file[i];
                if (multipartFile != null && multipartFile.getSize() != 0) {
                    String fileName = multipartFile.getOriginalFilename();
                    File file1 = new File(filePath);
                    if (!file1.exists())
                        file1.mkdirs();
                    File file2 = new File(filePath + File.separator + fileName);
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                    multipartFile.transferTo(file2);
                    j++;
                }
            }
        }
        System.out.println(j+"个文件上传成功");

        return j+"个文件上传成功";
    }

}
