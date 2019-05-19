package com.ss.vv.common;
import java.io.FileOutputStream;
import java.io.InputStream;

public class IOUtils {

    /**
     *
     * @param path 需要下载的文件路径，包括后缀名
     * @param inStream 输入流
     */
    public static void download(String path,InputStream inStream){
        FileOutputStream fs;
        try {
            fs = new FileOutputStream(path);
            byte[] buffer = new byte[1204];

            int byteread = 0;

            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            System.out.println(path + "保存成功！");

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}