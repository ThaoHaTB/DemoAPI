package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
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
