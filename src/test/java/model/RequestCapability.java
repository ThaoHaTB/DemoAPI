package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0uLKj8eBNVNo7fomVzqmGc34PCeDCluUuCwLE75Gj5z2prpwP82UZZiYqrv-SPkIQkJCvqJud_K3XqyGg3AcGMiZc4_Isl38jzl0goTVxfcOGghE-A7bfvgGvxTNQOmAQb-_x1zJwoxtGStooLJfA9UuBtSldBHF3sKhz-P-jLYA=3A35EF28";
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
