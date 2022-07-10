package com.example.test.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.ByteArrayOutputStream;

public class unZipToString {
    public static String unZipToString(byte[] ICzip){
        String ic = null;
        if (ICzip!=null) {
            byte[] bytes = uncompressed(ICzip);
            ic = new String(bytes, StandardCharsets.UTF_8);
        }
        return ic;
    }
    private static byte[] uncompressed(byte[] data) {
        try (ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(data));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ){
            ZipEntry nextEntry = zipIn.getNextEntry();
            byte[] buffer = new byte[4096];
            if (nextEntry != null) {
                int length = 0;
                while ((length = zipIn.read(buffer, 0, buffer.length)) > 0) {
                    byteArrayOutputStream.write(buffer, 0, length);
                }
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
