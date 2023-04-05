package JiraAPI;
import utils.ProjectInfo;

public class JiraIssueType {
    public static void main(String[] args) {
        String baseUri = "https://testinglily.atlassian.net";
        String projectKey = "RL";
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        System.out.println("Task ID:" + projectInfo.getIssueTypeId("epic"));
    }
}
