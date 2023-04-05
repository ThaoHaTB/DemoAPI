package bookFlow;

import model.PatchBody;
import model.PostBody;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.CoreMatchers.equalTo;

public class BookFlow {
    private String baseURL;
    private RequestSpecification request;
    private Response response;
    private String token;
    private String orderID="";
    private int bookID;
    private String clientName;
    private String updateName;
    Gson gson = new Gson();
    public BookFlow (RequestSpecification request,String baseURL, int bookID, String clientName, String updateName, String jraType){
        this.request=request;
        this.baseURL=baseURL;
        this.bookID= bookID;
        this.clientName=clientName;
        this.updateName=updateName;
    }

    public  void placeAnOrder(RequestSpecification request,int id, String customerName) {
        String basePath = "/orders/";
        request.basePath(basePath);
        PostBody postBody = new PostBody();
        postBody.setBookID(id);
        postBody.setCustomerName(customerName);
        Response response = request.body(gson.toJson(postBody)).post();
        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        orderID= jsonPath.getString("orderId");
        System.out.println(orderID);
    }
    public  void patchMethod(RequestSpecification request,String updateName) {
        String basePath = "/orders/";
        request.basePath(basePath);
        Gson gson = new Gson();
        PatchBody patchBody=new PatchBody();
        patchBody.setUpdateName(updateName);
        Response response = request.body(gson.toJson(patchBody)).patch(orderID);
        response.then().log().all();
        response.then().statusCode(equalTo(204));
    }
    public  void deleteOder(RequestSpecification request,String baseURL, String token){
        String basePath = "/orders/"+orderID;
        request.basePath(basePath);
        Response response = request.delete();
        response.then().log().all();
        response.then().statusCode(equalTo(204));
    }

}
