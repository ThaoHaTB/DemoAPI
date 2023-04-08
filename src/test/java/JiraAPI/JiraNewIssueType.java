package JiraAPI;

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
        String apiToken = "ATATT3xFfGF0fIbPT5nou6_t6ZGnGBKRooQQqIgVYntAcus9xljESd274XOhSThfyX-u-ZeVtM2HpiLNHGn-VwlnByfBHtq9sYd8F3FDc9mZASWE68ck0g0MoTgy8Zl6sV7n-NEA_4Bf407GWFP0VaWIkbl2n3kcR5C4Z8IrcVJP4sjOG-FZlrw=9199FCBF";
        String encodedCredStr = AuthenticationHender.encodeCredStr(email,apiToken);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJsonHeader);
        request.header(getAuthenticateHeader.apply(encodedCredStr));

        //Body data
        String summary="This is my summary";
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        String taskTypeID= projectInfo.getIssueTypeId("task");
        IssueBody.IssueType issueType=new IssueBody.IssueType(taskTypeID);
        IssueBody.Project project=new IssueBody.Project("RL");
        IssueBody.Fields fields=new IssueBody.Fields(project,issueType,summary);
        IssueBody issueBody=new IssueBody(fields);
        System.out.println(new Gson().toJson(issueBody));
        // Send request
        Response response= request.body(new Gson().toJson(issueBody)).post(path);
        response.prettyPrint();
    }

}
