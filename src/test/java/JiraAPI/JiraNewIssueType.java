package JiraAPI;

import builder.BodyJSONBuilder;
import builder.IssueContentBuilder;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.GlobalConstants;
import model.IssueBody;
import model.TransitionBody;
import org.apache.commons.lang3.RandomStringUtils;
import utils.AuthenticationHender;
import model.RequestCapability;
import utils.ProjectInfo;
import utils.TransitionsID;

import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static model.RequestCapability.*;

public class JiraNewIssueType {
    public static void main(String[] args) {
        String baseUri = "https://testinglily.atlassian.net";
        String projectKey = "RL";
        String path = " /rest/api/3/issue";
        String encodedCredStr = AuthenticationHender.encodeCredStr(GlobalConstants.EMAIL, GlobalConstants.API_TOKEN);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJsonHeader);
        request.header(getAuthenticateHeader.apply(encodedCredStr));

        //Body data
        // Get project info
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        //Get Issue Type
        String taskTypeID = projectInfo.getIssueTypeId("task");
        //Random Summary
        int desiredLength = 20;
        boolean hasLetter = true;
        boolean hasNumber = true;
        String randomSummary = RandomStringUtils.random(desiredLength, hasLetter, hasNumber);
        // form up payload to sent request
        IssueContentBuilder issueContentBuilder = new IssueContentBuilder();
        String issueBodyContent = issueContentBuilder.build(projectKey, taskTypeID, randomSummary);

        // get payload to verify with response body
//        IssueBody issueBody=issueContentBuilder.getIssueBody();
//        System.out.println( issueBody.getFields().getSummary());
//        System.out.println(issueBody.getFields().getProject().getKey());
//        System.out.println(issueBody.getFields().getIssueType().getId());

        // Send request
        Response response = request.body(issueBodyContent).post(path);


        //Check created task
        Map<String, String> responseBody = JsonPath.from(response.asString()).get();
        final String CREATE_ISSUE_KEY=responseBody.get("key");
        String getIssuePath = "/rest/api/3/issue/" + responseBody.get("key");

        //read created jira ticket
        response=request.get(getIssuePath);
        IssueBody issueBody = issueContentBuilder.getIssueBody();
        String expectedSummary = issueBody.getFields().getSummary();
        String expectedStatus = "To Do";
        //System.out.println(issueBody.getFields().getProject().getKey());
        //System.out.println(issueBody.getFields().getIssueType().getId());

        Map<String, ?> fields= JsonPath.from(response.getBody().asString()).get("fields");
        String actualSummary=fields.get("summary").toString();
        Map<String, ?> status= (Map<String, ?>) fields.get("status");
        Map<String,?> statusCategory= (Map<String, ?>) status.get("statusCategory");
        String actualStatus=statusCategory.get("name").toString();

        System.out.println("expected Summary: "+expectedSummary);
        System.out.println("actual Summary: "+actualSummary);

        System.out.println("expected Status: "+expectedStatus);
        System.out.println("actual Status: "+actualStatus);

        //Update created Jira task
        TransitionsID transitionsID=new TransitionsID(baseUri,CREATE_ISSUE_KEY);
        String transitionID=transitionsID.getTransitionsID("In Progress");
        TransitionBody body=new TransitionBody(transitionID);

    }

}
