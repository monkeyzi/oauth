package com.monkeyzi.oauth.common;

public class GlobalConstant {
   /**
    * ip信息
    */
   public static final String UNKNOWN = "unknown";
   public static final String X_FORWARDED_FOR = "X-Forwarded-For";
   public static final String X_REAL_IP = "X-Real-IP";
   public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
   public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
   public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
   public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
   public static final String LOCALHOST_IP = "127.0.0.1";
   public static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
   public static final int MAX_IP_LENGTH = 15;

   /**
    * 符号
    */
   public static final  class Symbol{

      /**
       * 禁止被外部实例化
       */
      private Symbol(){

      }

      public static final String COMMA = ",";
      public static final String MH = ":";
    }
}
