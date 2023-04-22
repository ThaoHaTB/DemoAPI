package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0xCYRCARrkicZ7pzKHrSjJb924UNkfTGWY-X4TMESBiXUcafPfECbTBAUQRbsYDsJFsLhalPx_SYNuk1GWM7VWgY-DqsuIs-Cc3nj-ES7winaIb1n2kdhudF2QGqd6Tk2vFOSp5dJj0C4iLxqHgEzE1CEMCEQ25tlSEaQx8piQgk=2C6DB80C";
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
