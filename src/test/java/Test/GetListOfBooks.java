package Test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GetListOfBooks {
    public static void main(String[] args) {
        //Create request
        RequestSpecification request=given();
        //Endpoint
        request.baseUri("https://simple-books-api.glitch.me");
        request.basePath("/books");
        Response response=request.get();
        //response.prettyPrint();
        response.then().log().all();
    }
}
