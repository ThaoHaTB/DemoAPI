package Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static model.RequestCapability.defaultHeader;
import static model.RequestCapability.getAuthenticateHeader;

public class ExtractJiraAPI {
    public static void main(String[] args) {
        String baseUri = "https://testinglily.atlassian.net";
        String path = "/rest/api/3/project/".concat("RL");
        String email = "hathaok37cntt@gmail.com";
        String apiToken = "ATATT3xFfGF013McSo2jkZNI8FtH3DBlHcPubRYx-bvcWWXsklSbvzHTOcGsqXW2LUSyBaPe3yOOn4ojLOywlHVU6PpWqerEkKjjCvI4aK8Nruwbx07myUHRSdz71wPdOq_9m4oy3imwX5nT-K0Y17kLJtgGHnnrMyg7h8miuDU4RqidxhJvVpA=2FEF35C7";
        String cred = email.concat(":").concat(apiToken);
        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        String encodedCredStr = new String(encodedCred);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(getAuthenticateHeader.apply(encodedCredStr));
        Response response = request.get(path);
        Map<String,Map<String, ?>> projectInfo = JsonPath.from(response.asString()).get();
        Map<String, ?> lead=projectInfo.get("lead");
        Map<String, ?> avatarUrls= (Map<String, ?>) lead.get("avatarUrls");
        System.out.println(avatarUrls.get("24x24"));

    }
}
