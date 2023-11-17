import DTO.*;
import Endpoints.ApiCallUtil;
import io.restassured.response.Response;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Collections;
import java.util.List;


public class AppTest {
    private static final String SUCCESS_LOGIN = "logged in user sesson:" + "\\d+";
    private static final String SUCCESS_FILE_UPLOAD = "File uploaded to (.*), (\\d+) bytes";
    private static SoftAssert softAssert;
    private ApiCallUtil apiCallUtil = new ApiCallUtil();


    @BeforeMethod
    public void initSoftAssert() {
        softAssert = new SoftAssert();
    }

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
        DefaultGetApiResponse defaultGetApiResponse = apiCallUtil.convertResponseToClass(createUserResponse, DefaultGetApiResponse.class);
        softAssert.assertNotNull(defaultGetApiResponse.getMessage(), "Message is null");
        softAssert.assertEquals(defaultGetApiResponse.getCode(), 200, "Unexpected code:");
        softAssert.assertEquals(defaultGetApiResponse.getType(), "unknown", "Unexpected type");
        softAssert.assertAll();
        try {
            new FluentWait<>(this)
                    .withTimeout(Duration.ofMillis(10000))
                    .pollingEvery(Duration.ofMillis(1000))
                    .until(content -> {
                        int statusCode = apiCallUtil.getUserByName(user.getUsername()).getStatusCode();
                        return statusCode == 200;
                    });
        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for the condition to be met");
        }
        Response getUserResponse = apiCallUtil.getUserByName(user.getUsername());
        getUserResponse.then().spec(apiCallUtil.getResponseSpecification())
                .assertThat().statusCode(200);
        User returnedUser = apiCallUtil.convertResponseToClass(getUserResponse, User.class);
        Assert.assertEquals(user, returnedUser, "Returned user is not as expected");
    }

    @Test
    public void verifyUserLogin() {
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

        Response response = apiCallUtil.userLogin(user);
        DefaultGetApiResponse defaultGetApiResponse = apiCallUtil.convertResponseToClass(response, DefaultGetApiResponse.class);
        response.then().spec(apiCallUtil.getResponseSpecification())
                .assertThat().statusCode(200);
        softAssert.assertTrue(defaultGetApiResponse.getMessage().matches(SUCCESS_LOGIN), "Message doesn't match expected format %s.".formatted(SUCCESS_LOGIN));
        softAssert.assertEquals(defaultGetApiResponse.getCode(), 200, "Unexpected code:");
        softAssert.assertEquals(defaultGetApiResponse.getType(), "unknown", "Unexpected type");
        softAssert.assertAll();
    }

    @Test
    public void verifyListOfUsersCreation() {
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

        List<User> userList = Collections.singletonList(user);
        Response response = apiCallUtil.createUserList(userList);
        DefaultGetApiResponse defaultGetApiResponse = apiCallUtil.convertResponseToClass(response, DefaultGetApiResponse.class);
        response.then().spec(apiCallUtil.getResponseSpecification())
                .assertThat().statusCode(200);
        softAssert.assertEquals(defaultGetApiResponse.getMessage(), "ok", "Unexpected Message");
        softAssert.assertEquals(defaultGetApiResponse.getCode(), 200, "Unexpected code:");
        softAssert.assertEquals(defaultGetApiResponse.getType(), "unknown", "Unexpected type");
        softAssert.assertAll();
    }

    @Test
    public void verifyUserLogout() {
        Response response = apiCallUtil.userLogout();
        DefaultGetApiResponse defaultGetApiResponse = apiCallUtil.convertResponseToClass(response, DefaultGetApiResponse.class);
        response.then().spec(apiCallUtil.getResponseSpecification())
                .assertThat().statusCode(200);
        softAssert.assertEquals(defaultGetApiResponse.getMessage(), "ok", "Unexpected Message");
        softAssert.assertEquals(defaultGetApiResponse.getCode(), 200, "Unexpected code:");
        softAssert.assertEquals(defaultGetApiResponse.getType(), "unknown", "Unexpected type");
        softAssert.assertAll();
    }

    @Test
    public void verifyAddPet() {
        Pet pet = Pet.builder()
                .id(1)
                .name("Pes Patron")
                .status("available")
                .photoUrls(Collections.singletonList("test.com"))
                .category(
                        Category.builder()
                                .id(2)
                                .name("Dog")
                                .build())
                .tags(Collections.singletonList(
                        Tag.builder()
                                .id(2)
                                .name("Tag1")
                                .build()))
                .build();
        Response response = apiCallUtil.createPet(pet);
        Pet actualPet = apiCallUtil.convertResponseToClass(response, Pet.class);
        response.then().spec(apiCallUtil.getResponseSpecification())
                .assertThat().statusCode(200);
        Assert.assertEquals(pet, actualPet, "Wrong pet details are returned in responce body");
    }

    @Test
    public void verifyUpladPetImage() {
        Pet pet = Pet.builder()
                .id(1)
                .name("Pes Patron")
                .status("available")
                .photoUrls(Collections.singletonList("test.com"))
                .category(
                        Category.builder()
                                .id(2)
                                .name("Dog")
                                .build())
                .tags(Collections.singletonList(
                        Tag.builder()
                                .id(2)
                                .name("Tag1")
                                .build()))
                .build();
        Response response = apiCallUtil.uploadPetImage(pet, "src/main/resources/PesPatron.png");
        DefaultGetApiResponse defaultGetApiResponse = apiCallUtil.convertResponseToClass(response, DefaultGetApiResponse.class);
        response.then().spec(apiCallUtil.getResponseSpecification())
                .assertThat().statusCode(200);
        softAssert.assertTrue(defaultGetApiResponse.getMessage().matches("additionalMetadata: test\n" + SUCCESS_FILE_UPLOAD), "Message doesn't match expected format");
        softAssert.assertEquals(defaultGetApiResponse.getCode(), 200, "Unexpected code:");
        softAssert.assertEquals(defaultGetApiResponse.getType(), "unknown", "Unexpected type");
        softAssert.assertAll();
    }
}