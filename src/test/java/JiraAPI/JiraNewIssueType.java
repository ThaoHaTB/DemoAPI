package JiraAPI;

import builder.BodyJSONBuilder;
import builder.IssueContentBuilder;
import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueBody;
import utils.AuthenticationHender;
import model.RequestCapability;
import utils.ProjectInfo;

import static io.restassured.RestAssured.given;
import static model.RequestCapability.*;

public class JiraNewIssueType {
    public static void main(String[] args) {
        String baseUri = "https://testinglily.atlassian.net";
        String projectKey = "RL";
        String path = " /rest/api/3/issue";
        String email = "hathaok37cntt@gmail.com";
        String apiToken = "ATATT3xFfGF0Tn4pFCz0f3YNaIC2zA7AkieSV9F9_fLnHtn6v8Z41DTMc19w_ImJl8pvhKhwTshWK6ryMyMnGcdrA7siE9HlVScDCGWWj9LhywVMABBbW27dgpdG91A8UWjhrSbkLmx4vB75VeUxV3F2MKObspxHojMtPHwSN0EPSe6Cka_wDPA=F52D7775";
        String encodedCredStr = AuthenticationHender.encodeCredStr(email,apiToken);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJsonHeader);
        request.header(getAuthenticateHeader.apply(encodedCredStr));

        //Body data
        String summary="Create login API";
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        String taskTypeID= projectInfo.getIssueTypeId("task");
        String issueBody= IssueContentBuilder.build(projectKey,taskTypeID,summary);

        // Send request
        Response response= request.body(issueBody).post(path);
        response.prettyPrint();
    }

}
