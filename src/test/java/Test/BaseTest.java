package Test;

import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import utils.AuthenticationHender;

import static io.restassured.RestAssured.given;

public class BaseTest implements RequestCapability {
    protected String baseUri;
    protected String encodedCredStr;
    protected String projectKey;
    protected RequestSpecification request;

    @BeforeSuite
    public void beforeSuite() {
        encodedCredStr = AuthenticationHender.encodeCredStr(EMAIL, API_TOKEN);
        baseUri = "https://testinglily.atlassian.net";
        projectKey = "RL";
    }

    @BeforeTest
    public void beforeTest() {
        String baseUriEnv = System.getProperty("baseUri");
        if (baseUriEnv != null) {
            baseUri = baseUriEnv;
        }
        if(baseUriEnv.isEmpty()){
            throw new RuntimeException("Please support base URL");
        }
        System.out.println(baseUriEnv);
        request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJsonHeader);
        request.header(getAuthenticateHeader.apply(encodedCredStr));
    }
}
