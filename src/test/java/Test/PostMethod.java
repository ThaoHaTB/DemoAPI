package Test;

import model.PostBody;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostMethod {
    public static void main(String[] args){
        String baseURL="https://simple-books-api.glitch.me";
        String token=getAuthorKey(baseURL);
        System.out.println(token);
        getOderID(baseURL,token);
    }

    private static String getAuthorKey(String baseURL){
        String basePath="/api-clients/";

        // Create http request
        RequestSpecification request = RestAssured.given();
        request.baseUri(baseURL);
        request.basePath(basePath);

        // Content type
        request.header( "Content-type", "application/json; charset=UTF-8");

        // Request body
        String postBody="{\n" +
                "  \"clientName\": \"Tester\",\n" +
                "  \"clientEmail\": \"testingAPI@gmail.com\"\n" +
                "}";

        // Send Post request
        Response response = request.body(postBody).post();
        JsonPath jsonPath=response.jsonPath();
        return jsonPath.getString("accessToken");

    }
    public static void getOderID(String baseURL, String token) {
        String basePath = "/orders/";

        RequestSpecification request = RestAssured.given();
        request.baseUri(baseURL);
        request.basePath(basePath);
        // Content type and authorization
        request.header("Content-type", "application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + token);

        Gson gson = new Gson();
        PostBody postBody = new PostBody();
        postBody.setBookID(1);
        postBody.setCustomerName("Tester");

        Response response = request.body(gson.toJson(postBody)).post();
        response.then().log().all();
//        JsonPath jsonPath=response.jsonPath();
//        return jsonPath.getString("orderId");
    }

}
