package com.monkeyzi.oauth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.log4j.helpers.ThreadLocalMap;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocalMap
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadLocalMapUtils {


   private static ThreadLocal<Map<String,Object>>  THREAD_CONTEXT=new MapThreadLocal();

    /**
     * 往threadLocal 存储对象
     * @param key
     * @param object
     */
    public static void put(String key,Object object){
       getThreadContextMap().put(key, object);
   }

    /**
     * remove object
     * @param key
     * @return
     */
    public static Object remove(String key){
        return getThreadContextMap().remove(key);
    }

    /**
     * 根据key从ThreadLocal中取出
     * @param key
     * @return
     */
    public static Object get(String key){
        return getThreadContextMap().get(key);
    }

    /**
     * 取得 Thread context实例
     * @return
     */
    public static Map<String,Object> getThreadContextMap(){
       return THREAD_CONTEXT.get();
   }

    /**
     * 清理线程所有被hold住的对象。以便重用！
     */
    public static void remove() {
        getThreadContextMap().clear();
    }


    /**
     * Initial value map 初始化值
     */
    private static class MapThreadLocal extends ThreadLocal<Map<String, Object>> {
        /**
         * Initial value map.
         *
         * @return the map
         */
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>(8) {

                private static final long serialVersionUID = 3637958959138295593L;

                @Override
                public Object put(String key, Object value) {
                    return super.put(key, value);
                }
            };
        }
    }

}
