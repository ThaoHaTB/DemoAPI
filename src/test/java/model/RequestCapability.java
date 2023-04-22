package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {
    public static final String EMAIL = "hathaok37cntt@gmail.com";
    public static final String API_TOKEN = "ATATT3xFfGF0OM80BlOdfs1smnkY8tVVKZnzfA-Ai4m8wy8Ade7jfq9wWvKYFEej8E824bPwXiQ3WmHeB6DOnboeLVOOeD1d17UmLd_dHm0qhjTrIiskP4C2g92vvdbjOMCJkXwXyRFkNABJA63KDXW4cGT-fUL2KyWChaN3bro4ICPOf7mOnlE=A414A57E";
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
