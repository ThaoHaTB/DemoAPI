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
        String apiToken = "ATATT3xFfGF0Tn4pFCz0f3YNaIC2zA7AkieSV9F9_fLnHtn6v8Z41DTMc19w_ImJl8pvhKhwTshWK6ryMyMnGcdrA7siE9HlVScDCGWWj9LhywVMABBbW27dgpdG91A8UWjhrSbkLmx4vB75VeUxV3F2MKObspxHojMtPHwSN0EPSe6Cka_wDPA=F52D7775";
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
