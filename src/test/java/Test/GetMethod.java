package Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetMethod {
    public static void main(String[] args){

        String baseURL="https://simple-books-api.glitch.me";
        // Create http request
        RequestSpecification request= RestAssured.given();
        request.baseUri(baseURL);
        request.basePath("/books/");
        Response response=request.get("2");
        response.prettyPrint();
        response.then().statusCode(200)
                .body("id",equalTo(3))
                .body("name",equalTo("Just as I Am"))
                .body("type",equalTo("non-fiction"))
                .time(Matchers.lessThan(3000L));

        long responseTime=response.time();
        System.out.println("response Time:"+responseTime);
    }
}
