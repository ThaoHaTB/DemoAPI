package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.GlobalConstants;
import model.RequestCapability;

import javax.lang.model.element.NestingKind;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TransitionsID implements RequestCapability {
    private String baseUri;
    private String issueKey;

    public TransitionsID(String baseUri, String issueKey) {
        this.baseUri = baseUri;
        this.issueKey = issueKey;
    }

    public String getTransitionsID(String transName){
        String transID = null;
        String encodedCredStr = AuthenticationHender.encodeCredStr(GlobalConstants.EMAIL, GlobalConstants.API_TOKEN);
        String getIssueTransitions="/rest/api/3/issue/"+issueKey+"/transitions";
        RequestSpecification request=given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJsonHeader);
        request.header(getAuthenticateHeader.apply(encodedCredStr));
        Response response=request.get(getIssueTransitions);
        Map<String, List<Map<String, String>>> bodyData= JsonPath.from(response.asString()).get();
        List<Map<String, String>> listTransition=bodyData.get("transitions");
        for(Map<String,String>trans:listTransition){
            if(trans.get("name").equalsIgnoreCase(transName)){
                transID=trans.get("id");
                break;
            }
        }
        return transID;
    }

}
