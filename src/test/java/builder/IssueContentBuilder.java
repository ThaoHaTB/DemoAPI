package builder;

import model.IssueBody;

public class IssueContentBuilder {
    public static String build(String projectKey,String taskTypeID, String summary ){
        IssueBody.IssueType issueType=new IssueBody.IssueType(taskTypeID);
        IssueBody.Project project=new IssueBody.Project(projectKey);
        IssueBody.Fields fields=new IssueBody.Fields(project,issueType,summary);
        IssueBody issueBody=new IssueBody(fields);

        return  BodyJSONBuilder.getJSONContent(issueBody);
    }
}
