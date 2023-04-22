package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0UKQ8_7ZgFtknWKQoCiVoMpmhRwmmJAnBrt0efBYylhSDLb4XETRcl-aVikelHta-Tvy3ceHb8_UtLpS7JaSGxFGyqJwRA_LtYYYlTNcdwWAhXIepPv3oFNb7_ggwHmKQB4upqcPZ6ZdIWucuc4MgOwuLQYJ9caTC7X3ngOEmPIY=830A2588";
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
