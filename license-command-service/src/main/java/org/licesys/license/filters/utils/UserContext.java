package org.licesys.license.filters.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private static final ThreadLocal<String> authToken= new ThreadLocal<String>();
    private static final ThreadLocal<String> username = new ThreadLocal<String>();
    private static final ThreadLocal<String> fullName = new ThreadLocal<String>();

    public static String getAuthToken() { return authToken.get(); }
    public static void setAuthToken(String aToken) {authToken.set(aToken);}

    public static String getUsername() { return username.get(); }
    public static void setUsername(String aUsername) {username.set(aUsername);}

    public static String getFullName() { return fullName.get(); }
    public static void setFullName(String aFullName) {fullName.set(aFullName);}

}