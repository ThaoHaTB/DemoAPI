package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0ARF39XlW5AJi8RUjX2PqnGWAIG-3b5OvitB6obRYVxTt-OxS4T0moh7WPX8SQUDs_W0o-OTFapYHiWo7YizTVyRFdhj1SAzvg2VA9Gn2ANNHkp59Zr3WdNSA9aY-iZ1nTBgr3H21fk_5jUJHOPArtK2ndNilmlT6eTzOJR3QU_Y=31526F71";
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
