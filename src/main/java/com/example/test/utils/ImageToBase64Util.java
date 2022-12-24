package com.example.test.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class ImageToBase64Util {
    public static  Map<String,String> fileHeader = new HashMap<>(){{
        put("/9j",".jpg");
        put("iVB",".jpg");
        put("Qk0",".bmp");
    }};
    /***  本地文件(图片、excel等)转换成Base64字符串 */
    public static String convertFileToBase64(String imgPath) {
        //读取图片字节数组
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(imgPath);
            System.out.println("文件大小(字节)=" + in.available());
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组进行Base64编码，得到Base64编码的字符串
        return new String(Objects.requireNonNull(Base64.encodeBase64(data)));
    }

    /*** 将base64字符串，生成文件 */
    public static File convertBase64ToFile(String fileBase64String, String filePath) {
        String fileName;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            Random random = new Random();
            random.setSeed(now.toEpochSecond(ZoneOffset.of("+8")));
            fileName = now.format(fm) + (random.nextInt()%10000);
            AtomicReference<String> suffix = new AtomicReference<>();
            suffix.set("");
            fileHeader.forEach((k,v)->{
                if(fileBase64String.substring(0,3).equals(k)){
                    suffix.set(v);
                }
            });
            if(suffix.get() == null){
                suffix.set(".png");
            }
            fileName = fileName+suffix.get();
            File dir = new File(filePath);
            //判断文件目录是否存在
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File files = new File(fileName);
            while(files.exists()){
                fileName =  now.format(fm) + (random.nextInt()%10000)+suffix.get();
                files = new File(fileName);
            }
            byte[] bfile = Base64.decodeBase64(fileBase64String);
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /*** MultipartFile转成InputStream 将图片转换成Base64编码 */
    public static String imgToBase64(MultipartFile uploadFiles) {
        InputStream in;
        byte[] data = null;
        //读取图片字节数组
        try {
            byte[] byteArr = uploadFiles.getBytes();
            in = new ByteArrayInputStream(byteArr);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.getMessage();
            e1.printStackTrace();
        }

        return new String(Objects.requireNonNull(Base64.encodeBase64(data)));
    }

}
