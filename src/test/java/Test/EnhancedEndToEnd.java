package Test;

import model.AuthorBody;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class EnhancedEndToEnd {

    public static void main(String[] args) {
        String baseURI="https://simple-books-api.glitch.me";
        int bookID=1;
        String customerName="Harry Potter";
        String customerEmail="harrypotter12@gmail.com";
        String updateName="John Potter";
        String token=getAuthorKey( baseURI,  customerName,  customerEmail);
        System.out.println(token);
        if(token!=null) {
            //Request
            RequestSpecification request = given();
            request.header("Content-type", "application/json; charset=UTF-8")
                    .header("Authorization", "Bearer " + token);
            request.baseUri(baseURI);
//            BookFlow bookFlow = new BookFlow(request, baseURI, bookID, customerName, updateName);
//            bookFlow.placeAnOrder(request,bookID, customerName);
//            bookFlow.patchMethod(request,updateName);
//            bookFlow.deleteOder(request,baseURI,token);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private static String getAuthorKey(String baseURL, String clientName, String clientEmail) {
        String basePath = "/api-clients/";
        // Create http request
        RequestSpecification request = given();
        request.baseUri(baseURL);
        request.basePath(basePath);
        // Content type
        request.header("Content-type", "application/json; charset=UTF-8");
        // Request body
        Gson gson= new Gson();
        AuthorBody authorBody= new AuthorBody();
        authorBody.setClientName(clientName);
        authorBody.setClientEmail(clientEmail);
        // Send Post request
        Response response = request.body(gson.toJson(authorBody)).post();
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("accessToken");
    }

}

