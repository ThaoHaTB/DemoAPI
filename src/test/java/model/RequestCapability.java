package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0sZ42a105uLowDqXu5QrfQu1uaYR0yKhherp12tOTqK7pEpFI6fp2HQijO9d0g9qRjdXFSel2d9xlI3XlH2LoPQtoYB8RBgrir85i_P5TfQtbDgwLoGz4CvGMqDV0lfSJTNy3uL34PyKDHG3bx2ijmGDWw_whIobr-WvVC0yxqWs=53233B78";
    Header defaultHeader = new Header("Content-type", "application/json;charset=UTF-8");
    Header acceptJsonHeader=new Header("Accept","application/json");

    static Header getAuthenticateHeader(String encodedCredStr) {
        if (encodedCredStr == null) {
            throw new IllegalArgumentException("[Error] encodedCredStr can't be null");
        }
        return new Header("Authorization", "Basic " + encodedCredStr);
    }

    Function<String, Header> getAuthenticateHeader = encodedCredStr -> {
        if (encodedCredStr == null) {
            throw new IllegalArgumentException("[Error] encodedCredStr can't be null");
        }
        return new Header("Authorization", "Basic " + encodedCredStr);

    };
}
