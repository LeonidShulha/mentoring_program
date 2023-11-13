import DTO.PostUserResponse;
import DTO.User;
import Endpoints.ApiCallUtil;
import io.restassured.response.Response;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static io.restassured.mapper.ObjectMapperType.JACKSON_2;

public class AppTest {

    SoftAssert softAssert = new SoftAssert();
    private ApiCallUtil apiCallUtil = new ApiCallUtil();

    @Test
    public void verifyUserCreation() {
        User user = User.builder()
                .id(1)
                .userStatus(0)
                .username("testUser")
                .firstName("John")
                .lastName("Doe")
                .email("john_doe@mail.com")
                .phone("+123456789010")
                .password("qwerty")
                .build();
        Response createUserResponse = apiCallUtil.createUser(user);
        createUserResponse.then().spec(apiCallUtil.getResponseSpecification()).assertThat().statusCode(200);
        PostUserResponse postUserResponse = createUserResponse.getBody().as(PostUserResponse.class, JACKSON_2);
        softAssert.assertNotNull(postUserResponse.getMessage(), "Message is null");
        softAssert.assertEquals(postUserResponse.getCode(), 200, "Unexpected code:");
        softAssert.assertEquals(postUserResponse.getType(), "unknown", "Unexpected type");

        new FluentWait<>(this)
                .withTimeout(Duration.ofMillis(10000))
                .pollingEvery(Duration.ofMillis(100000))
                .until(content -> {
                    int statusCode = apiCallUtil.getUserByName(user.getUsername()).getStatusCode();
                    return statusCode == 200;
                });
        Response getUserResponse = apiCallUtil.getUserByName(user.getUsername());
        getUserResponse.then().spec(apiCallUtil.getResponseSpecification())
                .assertThat().statusCode(200);
        User returnedUser = getUserResponse.getBody().as(User.class, JACKSON_2);
        softAssert.assertEquals(user, returnedUser, "Returned user is not as expected");
        softAssert.assertAll();
    }
}
