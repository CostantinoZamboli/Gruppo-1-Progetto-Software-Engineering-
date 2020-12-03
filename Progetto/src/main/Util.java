package main;

import java.nio.charset.StandardCharsets;

public class Util {
    public static String removeLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }
    public static String utf8Encode(String rawString){
        byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
        String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
        return utf8EncodedString;
    }
}

