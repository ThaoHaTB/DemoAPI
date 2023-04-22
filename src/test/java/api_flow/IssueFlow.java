package api_flow;

import builder.BodyJSONBuilder;
import builder.IssueContentBuilder;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueBody;
import model.TransitionBody;
import org.apache.commons.lang3.RandomStringUtils;
import utils.ProjectInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueFlow {
    private static Map<String, String> transitionTypeMap = new HashMap<>();
    private static String issuePathPrefix = "/rest/api/3/issue";
    private String baseUri;
    private RequestSpecification request;
    private Response response;
    private String createdIssueKey;
    private String issueTypeStr;
    private IssueBody issueBody;
    String status;
    private String projectKey;

    static {
        transitionTypeMap.put("11", "To Do");
        transitionTypeMap.put("21", "In progress");
        transitionTypeMap.put("31", "Done");
    }

    public IssueFlow(RequestSpecification request, String baseUri, String projectKey, String issueTypeStr) {
        this.request = request;
        this.baseUri = baseUri;
        this.issueTypeStr = issueTypeStr;
        this.projectKey = projectKey;
        this.status = "To Do";
    }
    @Step("Create Jira issue")
    public void createIssue() {
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        //Get Issue Type
        String taskTypeID = projectInfo.getIssueTypeId(issueTypeStr);
        //Random Summary
        int desiredLength = 20;
        boolean hasLetter = true;
        boolean hasNumber = true;
        String randomSummary = RandomStringUtils.random(desiredLength, hasLetter, hasNumber);
        // form up payload to sent request
        IssueContentBuilder issueContentBuilder = new IssueContentBuilder();
        String issueBodyContent = issueContentBuilder.build(projectKey, taskTypeID, randomSummary);
        issueBody = issueContentBuilder.getIssueBody();
        // Send request
        this.response = request.body(issueBodyContent).post(issuePathPrefix);
        Map<String, String> responseBody = JsonPath.from(response.asString()).get();
        //Get ticket Key
        createdIssueKey = responseBody.get("key");
    }
    @Step("Verify the details")
    public void verifyIssueDetail() {
        String expectedSummary = issueBody.getFields().getSummary();
        String expectedStatus = status;
        Map<String, String> issueInfo = getIssueInfo();
        String actualSummary = issueInfo.get("actualSummary");
        String actualStatus = issueInfo.get("actualStatus");

        System.out.println("expected Summary: " + expectedSummary);
        System.out.println("actual Summary: " + actualSummary);
        System.out.println("expected Status: " + expectedStatus);
        System.out.println("actual Status: " + actualStatus);
    }

    private Map<String, String> getIssueInfo() {
        String getIssuePath = issuePathPrefix + "/" + createdIssueKey;
        Response response_ = request.get(getIssuePath);
        Map<String, ?> fields = JsonPath.from(response_.getBody().asString()).get("fields");
        String actualSummary = fields.get("summary").toString();
        Map<String, ?> status = (Map<String, ?>) fields.get("status");
        Map<String, ?> statusCategory = (Map<String, ?>) status.get("statusCategory");
        String actualStatus = statusCategory.get("name").toString();

        Map<String, String> issueInfo = new HashMap<>();
        issueInfo.put("actualSummary", actualSummary);
        issueInfo.put("actualStatus", actualStatus);
        return issueInfo;
    }
    @Step("Update the Issue")
    public void updateIssue(String issueStatusStr) {
        String targetTransitionID = null;
        for (String transitionId : transitionTypeMap.keySet()) {
            if (transitionTypeMap.get(transitionId).equalsIgnoreCase(issueStatusStr)) {
                targetTransitionID = transitionId;
                break;
            }
        }
        if (targetTransitionID == null) {
            throw new RuntimeException("[ERR] issue status string is not supported");
        }
        String issueTransitionPath = issuePathPrefix+"/" + createdIssueKey + "/transitions";

        TransitionBody.Transition transition = new TransitionBody.Transition(targetTransitionID);
        TransitionBody transitionBody = new TransitionBody(transition);
        String transitionBodyJSon = BodyJSONBuilder.getJSONContent(transitionBody);

        request.body(transitionBodyJSon).post(issueTransitionPath).then().statusCode(204);
        // verify status
        Map<String, String> issueInfo = getIssueInfo();
        String actualIssueStatus = issueInfo.get("actualStatus");
        String expectedIssueStatus = transitionTypeMap.get(targetTransitionID);
        this.status=actualIssueStatus;
        System.out.println("Actual status: " + actualIssueStatus);
        System.out.println("Expected status: " + expectedIssueStatus);
    }
    @Step("Delete the Issue")
    public void deleteIssue() {
        String path = issuePathPrefix+"/" + createdIssueKey;
        response = request.delete(path);
        //Verify issue is not existing
        response = request.get(path);
        Map<String, List<String>> notExistingIssueRes = JsonPath.from(response.body().asString()).get();
        List<String> errorMessages = notExistingIssueRes.get("errorMessages");
        System.out.println(errorMessages);
    }
}
