package com.monkeyzi.oauth.utils;

import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 阿里oss工具类
 */
@Slf4j
@Component
public class AliOssUtils {

    private  static final String  BACKET_NAME="monkeyzi";

    private  static final String  ENDPOINT="oss-cn-beijing.aliyuncs.com";

    private  static final String  ACCESS_KEY="LTAIC5Ixo6r7DuW9";

    private  static final String  ACCESS_SECRET="vnqKO63Uhjil5qjGOiLN0NfTEbGyGR";

    public   static final String  FOLDER_NAME="image/";

    private  static final String  HTTP="http://";


    /**
     * 文件流形式上传
     * @param file
     * @param fileName
     * @return
     */
    public String AliossInputStreamUpload(FileInputStream file,String fileName,String folder){
        OSSClient ossClient=new OSSClient(HTTP+ENDPOINT,ACCESS_KEY,ACCESS_SECRET);
        ossClient.putObject(BACKET_NAME,folder+fileName,file);
        ossClient.shutdown();
        return HTTP+BACKET_NAME+"."+ENDPOINT+"/"+folder+fileName;
    }

    /**
     * 文件路径形式上传
     * @param filePath
     * @param fileName
     * @return
     */
    public String AliossFileUpload(String filePath,String fileName,String folder){
        OSSClient ossClient=new OSSClient(HTTP+ENDPOINT,ACCESS_KEY,ACCESS_SECRET);
        ossClient.putObject(BACKET_NAME,folder+fileName,new File(filePath));
        ossClient.shutdown();
        return HTTP+BACKET_NAME+"."+ENDPOINT+"/"+folder+fileName;
    }


    /**
     * 复制文件
     * @param oldKey 源文件名
     * @param newKey 新的文件名
     * @return
     */
    public String AliossCopyFile(String oldKey,String newKey,String folder){
        OSSClient ossClient=new OSSClient(HTTP+ENDPOINT,ACCESS_KEY,ACCESS_SECRET);
        ossClient.copyObject(BACKET_NAME,folder+oldKey,BACKET_NAME,folder+newKey);
        ossClient.shutdown();
        return HTTP+BACKET_NAME+"."+ENDPOINT+"/"+folder+newKey;
    }

    /**
     * 重命名文件
     * @param sourceKey 源文件名
     * @param newKey    新文件名
     * @return
     */
    public String AliossRenameFile(String sourceKey,String newKey,String folder){
        //先复制
        AliossCopyFile(sourceKey,newKey,folder);
        //删除
        AliossDeleteFile(folder,sourceKey);
        return HTTP+BACKET_NAME+"."+ENDPOINT+"/"+folder+newKey;
    }

    /**
     * 删除文件
     * @param folder
     * @param key
     */
    public void AliossDeleteFile(String folder,String key){
        OSSClient ossClient=new OSSClient(HTTP+ENDPOINT,ACCESS_KEY,ACCESS_SECRET);
        ossClient.deleteObject(BACKET_NAME,folder+key);
        ossClient.shutdown();
    }


    public static void main(String[] args) throws FileNotFoundException {
       // AliOssUtils li=new AliOssUtils();
      FileInputStream file=new FileInputStream("D:\\bgImage\\timg.jpg");
        AliOssUtils li=new AliOssUtils();
        String result=li.AliossInputStreamUpload(file,"hhh.jpg",FOLDER_NAME);
        System.out.println(result);
       //li.AliossCopyFile("我爱你.jpg","love.jpg",FOLDER_NAME);
    }

}
