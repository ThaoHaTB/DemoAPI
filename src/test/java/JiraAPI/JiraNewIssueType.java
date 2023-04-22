package JiraAPI;

import builder.BodyJSONBuilder;
import builder.IssueContentBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueBody;
import model.TransitionBody;
import org.apache.commons.lang3.RandomStringUtils;
import utils.AuthenticationHender;
import utils.ProjectInfo;
import utils.TransitionsID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.restassured.RestAssured.given;
import static model.RequestCapability.*;

public class JiraNewIssueType {
    public static void main(String[] args) {
        String baseUri = "https://testinglily.atlassian.net";
        String projectKey = "RL";
        String path = " /rest/api/3/issue";
        String encodedCredStr = AuthenticationHender.encodeCredStr(EMAIL, API_TOKEN);

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
        // Send request
        Response response = request.body(issueBodyContent).post(path);
        //Check created task
        Map<String, String> responseBody = JsonPath.from(response.asString()).get();
        //Get ticket Key
        final String CREATE_ISSUE_KEY = responseBody.get("key");
        // create path
        String getIssuePath = "/rest/api/3/issue/" + CREATE_ISSUE_KEY;
        //Get summary and set expected Status
        IssueBody issueBody = issueContentBuilder.getIssueBody();
        String expectedSummary = issueBody.getFields().getSummary();
        String expectedStatus = "To Do";

        Function<String, Map<String, String>> getIssueInfo = issueKey -> {
            //read created jira ticket
            Response response_ = request.get(getIssuePath);
            Map<String, ?> fields = JsonPath.from(response_.getBody().asString()).get("fields");
            String actualSummary = fields.get("summary").toString();
            Map<String, ?> status = (Map<String, ?>) fields.get("status");
            Map<String, ?> statusCategory = (Map<String, ?>) status.get("statusCategory");
            String actualStatus = statusCategory.get("name").toString();

            Map<String, String> issueInfo=new HashMap<>();
            issueInfo.put("actualSummary",actualSummary);
            issueInfo.put("actualStatus",actualStatus);
            return issueInfo;
        };

        Map<String,String>issueInfo=getIssueInfo.apply(CREATE_ISSUE_KEY);

        System.out.println("expected Summary: " + expectedSummary);
        System.out.println("actual Summary: " + issueInfo.get("actualSummary"));

        System.out.println("expected Status: " + expectedStatus);
        System.out.println("actual Status: " + issueInfo.get("actualStatus"));

        //Update transition for created Jira task
        TransitionsID transitionID = new TransitionsID(baseUri, CREATE_ISSUE_KEY);
        // -> Get transitionID
        String transitionIDStr = transitionID.getTransitionsID("Done");
        String issueTransitionPath = "/rest/api/3/issue/" + CREATE_ISSUE_KEY + "/transitions";
        // -> Build body data
        TransitionBody.Transition transition=new TransitionBody.Transition(transitionIDStr);
        TransitionBody transitionBody=new TransitionBody(transition);
        String transitionBodyJSon= BodyJSONBuilder.getJSONContent(transitionBody);
        request.body(transitionBodyJSon).post(issueTransitionPath).then().statusCode(204);
        // verify status
        issueInfo=getIssueInfo.apply(CREATE_ISSUE_KEY);
        String lastedStatus=issueInfo.get("actualStatus");
        System.out.println("Status:"+lastedStatus);


        // Delete ticket
        String deleteIssuePath="/rest/api/3/issue/"+CREATE_ISSUE_KEY;
        response=request.delete(deleteIssuePath);
        //Verify issue is not existing
        response=request.get(getIssuePath);
        Map<String, List<String>> notExistingIssueRes=JsonPath.from(response.body().asString()).get();
        List<String>errorMessages=notExistingIssueRes.get("errorMessages");
        System.out.println(errorMessages);

    }


}
