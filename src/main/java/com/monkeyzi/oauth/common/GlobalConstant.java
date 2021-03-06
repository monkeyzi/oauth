package com.monkeyzi.oauth.common;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

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
    * 系统常量
    */
   public static final  class Sys{

      private Sys(){

      }

      /**
       * 当前登录人
       */
      public static final String CURRENT_AUTH_USER="CURRENT_AUTH_USER";
      /**
       * 系统超级管理员 用户Id
       */
      public static final String SYS_SUPER_ADMIN_USER_ID="0";

      /**
       * 系统超级管理员 角色ID
       */
      public static final String SYS_SUPER_ADMIN_ROEL_ID="0";

      /**
       * 菜单权限根目录节点
       */
      public static final  String SYS_MENU_PERMISSION_ID="0";
      /**
       * 文件夹根目录节点
       */
      public static final  String SYS_FOLDER_ID="0";
      /**
       * 系统部门根目录
       */
      public static final  String SYS_DEPT_ID="0";
      /**
       * 全局限流 key
       */
     public static final   String SYS_RATER_LIMIT_KEY="MONKEYZI_LIMIT_KEY";

      /**
       * 登录错误次数 key
       */
     public static final  String SYS_LOGIN_LIMIT_COUNT="LOGIN_TIME_LIMIT";

      /**
       * 连续登录错误次数 超过限制 key
       */
     public static final  String SYS_LOGIN_FAIL_LIMIT="LOGIN_FAIL_LIMIT";


   }
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
      public final static String SHORT_LINE = "-";
    }


   /**
    * oss文件存储
    */
   public static  final class Oss{
       private Oss(){

       }

      /**
       * 当前使用的oss
       */
      public static final String CURRENT_OSS="CORRENT_OSS";
      /**
       * 阿里oss
       */
      public static final String ALI_OSS="ALI_OSS";
      /**
       * 七牛oss
       */
      public static final String QINIU_OSS="QINIU_OSS";
      /**
       * 存储图片的文件夹
       */
      public static final String OSS_IMG="image/";
      /**
       * 存储视频的文件夹
       */
      public static final String OSS_VIDEO="video/";
      /**
       * 存储其他类型的文件夹 如:压缩文件
       */
      public static final String OSS_FILE="file/";
      /**
       * 存储txt,word,excel等的文件夹
       */
      public static final String OSS_WORD="word/";
      /**
       * 其他类型的文件
       */
      public static final String OSS_OTHER="other/";

      public static final List<String> IMAGE_LIST = Arrays.asList("jpg","png","jpeg","gif","svg");

      public static final List<String> VIDEO_LIST = Arrays.asList("mpeg","avi","flv","mp4","wmv");

      public static final List<String> FILE_LIST  = Arrays.asList("psd","tar","zip","exe","bat","sh","rar");

      public static final List<String> WORD_LIST  = Arrays.asList("txt","js","css","sass","doc",
              "docx","pdf","xlsx","xls");
   }
}
