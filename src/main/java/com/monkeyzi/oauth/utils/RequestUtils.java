package com.monkeyzi.oauth.utils;

import com.monkeyzi.oauth.common.GlobalConstant;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.codec.Base64;

import java.io.IOException;

public class RequestUtils {
    /**
     * 解析客户端token
     * @param header
     * @return
     * @throws IOException
     */
    public static String[] extractAndDecodeHeader(String header) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("解析 basic authentication token 失败");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(GlobalConstant.Symbol.MH);

        if (delim == -1) {
            throw new BadCredentialsException("非法的 basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}
