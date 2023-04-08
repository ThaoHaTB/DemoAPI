package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProjectInfo implements RequestCapability {
    private String baseUri;
    private String projectKey;
    private List<Map<String, String>> issueTypes;
    private Map<String, List<Map<String, String>>> projectInfo;

    public ProjectInfo(String baseUri, String projectKey) {
        this.baseUri = baseUri;
        this.projectKey = projectKey;
        getProjectInfo();
    }

    public String getIssueTypeId(String issueTypeStr) {
        getIssueType();
        String issueTypeId = null;
        for (Map<String, String> issueType : issueTypes) {
            if (issueType.get("name").equalsIgnoreCase(issueTypeStr)) {
                issueTypeId = issueType.get("id");
                break;
            }
        }
        if (issueTypeId == null) {
            throw new RuntimeException("[Error] Could not find the id for" + issueTypeStr);
        }
        return issueTypeId;
    }

    private void getIssueType() {
        issueTypes = projectInfo.get("issueTypes");
    }

    private void getProjectInfo() {
        String path = "/rest/api/3/project/".concat(projectKey);
        String email = "hathaok37cntt@gmail.com";
        String apiToken = "ATATT3xFfGF0fIbPT5nou6_t6ZGnGBKRooQQqIgVYntAcus9xljESd274XOhSThfyX-u-ZeVtM2HpiLNHGn-VwlnByfBHtq9sYd8F3FDc9mZASWE68ck0g0MoTgy8Zl6sV7n-NEA_4Bf407GWFP0VaWIkbl2n3kcR5C4Z8IrcVJP4sjOG-FZlrw=9199FCBF";
        String encodedCredStr = AuthenticationHender.encodeCredStr(email,apiToken);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJsonHeader);
        request.header(getAuthenticateHeader.apply(encodedCredStr));
        Response response = request.get(path);
        projectInfo = JsonPath.from(response.asString()).get();

    }
}
