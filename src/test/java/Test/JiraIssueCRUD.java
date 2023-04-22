package Test;

import api_flow.IssueFlow;
import org.testng.annotations.Test;

public class JiraIssueCRUD extends BaseTest{
    @Test
    public void testE2E() {
        IssueFlow issueFlow= new IssueFlow(request,baseUri,projectKey,"task");
        System.out.println("---> Create");
        issueFlow.createIssue();
        System.out.println("---> Read");
        issueFlow.verifyIssueDetail();
        System.out.println("---> Update");
        issueFlow.updateIssue("Done");
        issueFlow.verifyIssueDetail();
        System.out.println("---> Delete");
        issueFlow.deleteIssue();
    }
    @Test
    public void createBug() {
        IssueFlow issueFlow= new IssueFlow(request,baseUri,projectKey,"bug");
        System.out.println("---> Create");
        issueFlow.createIssue();
        System.out.println("---> Read");
        issueFlow.verifyIssueDetail();
    }

    @Test
    public void updateIssue() {
        IssueFlow issueFlow= new IssueFlow(request,baseUri,projectKey,"bug");
        issueFlow.createIssue();
        issueFlow.verifyIssueDetail();
        issueFlow.updateIssue("In Progress");
    }
    @Test
    public void delete() {
        IssueFlow issueFlow= new IssueFlow(request,baseUri,projectKey,"task");
        issueFlow.createIssue();
        issueFlow.verifyIssueDetail();
        issueFlow.deleteIssue();
    }

}
