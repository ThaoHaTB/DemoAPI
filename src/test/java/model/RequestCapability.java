package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0eoyhMAHQWvLB4_osNbWokbSZW0WB-1DbQaCpjfS_a4yLILK2I5XOD6v-DspTeQlB3Oms92neohtB8ErQVD5mFl4iLL5kcOiPyFTBPZWOwCwwPbk3hzq9D4KH_zmTUl43BKcO1Cae73Ni-8XjScJFsZHY6wXrgRdVhoMlTG2ztbw=88DE4F99";
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
