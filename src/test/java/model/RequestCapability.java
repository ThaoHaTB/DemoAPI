package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF04gElKl4QVxIXF6ICnrWjyMBMZH9s4cHWwe0C1rw_AH7_CPQMaxsIqJouvh2y73JwyadP7tEKktmRYcUZWEv8pOcI2xn3LtM9dHrzOkAnbj_XWfXj9sM25mNd8ygKYHltSwrY_sTBzb_xHXVqERhTidXFpErLuYm0TtkMvIGvy04=57DA4AD5";

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
