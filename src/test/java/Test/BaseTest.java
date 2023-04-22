package Test;

import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import utils.AuthenticationHender;

import java.net.URLEncoder;

import static io.restassured.RestAssured.given;

public class BaseTest implements RequestCapability {
    protected String baseUri;
    protected String encodedCredStr;
    protected String projectKey;
    protected RequestSpecification request;

    @BeforeSuite
    public void beforeSuite() {
        encodedCredStr = AuthenticationHender.encodeCredStr(EMAIL, API_TOKEN);
        projectKey = "RL";
        String baseUriEnv = System.getProperty("baseUri");
        if (baseUriEnv != null) {
            baseUri = baseUriEnv;
        }
        if(baseUriEnv.isEmpty()){
            throw new RuntimeException("Please support base URL");
        }
        System.out.println(baseUriEnv);
    }

    @BeforeTest
    public void beforeTest() {
        request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJsonHeader);
        request.header(getAuthenticateHeader.apply(encodedCredStr));
    }
}
