package com.monkeyzi.oauth.im.util;

import org.tio.utils.json.Json;

import java.io.IOException;

public class ByteUtil {

   private static  final String  CHARSET="utf-8";

    /**
     * 对象转byte[]
     * @param obj
     * @return
     * @throws IOException
     */
   public static byte[] toBytes(Object obj) throws IOException {
       if (obj==null){
           return null;
       }
       if (obj instanceof String){
           return ((String) obj).getBytes(CHARSET);
       }
       return Json.toJson(obj).getBytes(CHARSET);
   }

    /**
     * byte[] 转字符串
     * @param bytes
     * @return
     * @throws IOException
     */
   public static String toText(byte[] bytes) throws  IOException{
      if (bytes==null||bytes.length==0){
          return null;
      }
      return new String(bytes,CHARSET);
   }
}
