package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.apache.commons.codec.binary.Base64;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static model.RequestCapability.defaultHeader;
import static model.RequestCapability.getAuthenticateHeader;

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
        String apiToken = "ATATT3xFfGF0VkaR7p2h09qsLjVo-IMidSrXf4DbUOnXIbW0Bj8EArioSXS-YbKYf8Z26F_zHDVnbTJyte4Cs4VanlFj8GJY9c5eIIU68efzP2xr8uc6PWPIgYbjfpvCLHnpWHGH3wBraJ0whurbnJKr4IsIEf7w_DdJQ3FIRP7W5WOhTQQsmxQ=16413BD0";
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
