package cn.itcast.travel.util;

import javax.servlet.http.Cookie;

public class CookieUtils {

    public static Cookie findCookie(Cookie[] cookies, String str) {
        Cookie cookie = null;
        if (cookies != null) {
            for (Cookie ck : cookies) {
                if (str.equals(ck.getName())) {
                    cookie = ck;
                    break;
                }
            }
        }
        return cookie;
    }
}
