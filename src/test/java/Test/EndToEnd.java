package Test;

import model.PatchBody;
import model.PostBody;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class EndToEnd {
    public static void main(String[] args) {
        // Set up Base URL
        String baseURL = "https://simple-books-api.glitch.me";

        // Create http request
        RequestSpecification request = RestAssured.given();
        request.baseUri(baseURL);

        // Get token
        String token = getAuthorKey(request);
        System.out.println("Token:"+token);
        request.header("Content-type", "application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + token);
        Gson gson = new Gson();

        //Place an order
        String orderID = placeAnOrder(request,gson, token, 1, "Andy");
        System.out.println("OrderID:"+orderID);

        // Edit an order
        patchMethodUsingParam(request, gson, token, orderID);

        // Delete an order
        deleteOder( baseURL,  token,  orderID);
    }

    private static String getAuthorKey(RequestSpecification request) {
        String basePath = "/api-clients/";
        request.basePath(basePath);
        // Content type
        request.header("Content-type", "application/json; charset=UTF-8");
        // Request body
        String postBody = "{\n" +
                "  \"clientName\": \"Tester\",\n" +
                "  \"clientEmail\": \"tester1234@gmail.com\"\n" +
                "}";

        // Send Post request
        Response response = request.body(postBody).post();
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("accessToken");

    }
    public static String placeAnOrder(RequestSpecification request,Gson gson, String token, int id, String customerName) {
        String basePath = "/orders/";
        request.basePath(basePath);
        // Content type and authorization

        PostBody postBody = new PostBody();
        postBody.setBookID(id);
        postBody.setCustomerName(customerName);
        Response response = request.body(gson.toJson(postBody)).post();
        System.out.println("Response of Post method");
        response.then().log().all();
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("orderId");
    }
    public static void patchMethod(RequestSpecification request,Gson gson,String token, String orderID) {
        String basePath = "/orders/";
        request.basePath(basePath);
        PatchBody patchBody=new PatchBody();
        patchBody.setUpdateName("name 1");
        Response response = request.body(gson.toJson(patchBody)).patch(orderID);
        System.out.println("Response of Patch method");
        response.then().log().all();
        response.then().statusCode(equalTo(204));

    }
    public static void patchMethodUsingParam(RequestSpecification request,Gson gson, String token, String orderID) {
        String basePath = "/orders/";
        request.basePath(basePath);
        PatchBody patchBody1=new PatchBody("Name 1");
        PatchBody patchBody2=new PatchBody("Name 2");
        PatchBody patchBody3=new PatchBody("Name 3");
        List<PatchBody> patchBodies= Arrays.asList(patchBody1,patchBody2,patchBody3);
        for(PatchBody patchBody:patchBodies) {
            Response response = request.body(gson.toJson(patchBody)).patch(orderID);
            System.out.println("Response of Patch method");
            response.then().log().all();
            response.then().statusCode(equalTo(204));
        }
    }
    public static void deleteOder(String baseURL, String token, String orderID){
        RequestSpecification request= RestAssured.given();
        String basePath = "/orders/";
        request.baseUri(baseURL);
        request.basePath(basePath);
        request.header("Content-type", "application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + token);
        Response response = request.delete(orderID);
        response.then().log().all();
        response.then().statusCode(equalTo(204));
    }

}
