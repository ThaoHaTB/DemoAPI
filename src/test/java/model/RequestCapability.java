package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0y9UB9YnOkIHxZec-_x8mmou-MYQ7UVYB06Gnep1XVVRaBwOqGOLexTjse6c1l9X7VsSu7wA40Q9yiOfbCufkyUzYfqSvguUkpFNbeZF9J7qSloYanUSd1tlHDKmyIVeUTeHxrUs13g8P9meQ3fsfg6qlsD5TaX1IAvXQoDQQ7C4=72708EDF";
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
