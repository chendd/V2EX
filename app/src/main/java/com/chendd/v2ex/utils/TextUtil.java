package com.chendd.v2ex.utils;

import android.text.Html;
import android.text.Spanned;

/**
 * @author admin
 * @Time 2016/6/27.
 */
public class TextUtil {

    private static final String HTTP = "http:";

    /**
     *  字符串半角转为全角
     * @param input
     * @return
     */
    private static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    private static Spanned fromHtml(String input) {
        return Html.fromHtml(input);
    }

    public static Spanned formatText(String input) {
        return fromHtml(toDBC(input));
    }

    public static String appendHttp(String input){
        return HTTP+input;
    }

}
