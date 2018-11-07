package com.monkeyzi.oauth.utils;

/**
 * 密码强度校验工具类
 */
public class PasswordCheckUtils {

    private static final String regexZ = "\\d*";
    private static final String regexS = "[a-zA-Z]+";
    private static final String regexT = "\\W+$";
    private static final String regexZT = "\\D*";
    private static final String regexST = "[\\d\\W]*";
    private static final String regexZS = "\\w*";
    private static final String regexZST = "[\\w\\W]*";

    /**
     * 密码强度校验
     * @param password 明文的密码
     * @return
     */
    public static String CheckPass(String password){
        if (password.matches(regexZ)){
            return "弱";
        }
        if (password.matches(regexS)){
            return "弱";
        }
        if (password.matches(regexT)){
            return "弱";
        }
        if (password.matches(regexZT)){
            return "中";
        }
        if (password.matches(regexST)){
            return "中";
        }
        if (password.matches(regexZS)){
            return "中";
        }
        if (password.matches(regexZST)){
            return "强";
        }
        return "弱";
    }
}
