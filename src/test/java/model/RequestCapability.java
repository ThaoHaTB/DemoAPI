package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0t3OR9UeKZEjSwC5UPxEiIMQpV3Yv9nKT_xTe403tIHvdwFjoxO0L7zQcT8cYqIEg0yB1YKEJqR3GCaeefyvJQG1WvWCm5eLdZDBuCp4Vlw5vKcSrQZ7OeZa59AsbUElOWHQlSRPPIogtBbR8eJX68DcZQQjtmD2ExKvWtzQLFuM=F11CE1CC";
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
