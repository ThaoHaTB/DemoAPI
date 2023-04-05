package Test;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class ExtractBody {
    public static void main(String[] args){
        RequestSpecification request= given();
        Response response= request.get("https://jsonplaceholder.typicode.com/users");
        response.prettyPrint();
        //parseJsonResponseAsList(response);
    }

    public static void parseJsonResponseAsList(Response response){

        List<Map<String,? >> jsonResponse=  JsonPath.from(response.asString()).get();

        System.out.println(jsonResponse.size());

        for(Map<String, ?> jsonString:jsonResponse){
            //System.out.println(jsonString.get("name"));
            if(jsonString.get("username").equals("Elwyn.Skiles")){
                System.out.println(jsonString.get("email"));
                Map<String,?> address= (Map<String, ?>) jsonString.get("address");
                System.out.println("Street:"+address.get("street"));
                Map<String,String> geo= (Map<String, String>) address.get("geo");
                System.out.println(geo.get("lat"));
            }
        }
//        List<String> jsonResponse_Names = response.jsonPath().getList("username");
//        System.out.println(jsonResponse_Names);
//
//        String username_0 = response.jsonPath().getString("username[1]");
//        System.out.println(username_0);
//
//        String address_stress = response.jsonPath().getString("address[1].street");
//        System.out.println(address_stress);
    }
}
