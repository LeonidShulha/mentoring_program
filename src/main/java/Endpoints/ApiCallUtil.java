package Endpoints;

import DTO.Pet;
import DTO.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.mapper.ObjectMapperType.JACKSON_2;

@AllArgsConstructor
@Getter
public class ApiCallUtil {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setConfig(RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig(JACKSON_2)))
            .setBaseUri(BASE_URL)
            .setContentType("application/json")
            .setAccept("application/json")
            .log(LogDetail.ALL)
            .build();

    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    public Response createUser(User user) {
        return given().spec(requestSpecification)
                .basePath("/user")
                .body(user)
                .post();
    }

    public Response createUserList(List<User> userList) {
        return given().spec(requestSpecification)
                .basePath("/user/createWithList")
                .body(userList)
                .post();
    }

    public Response getUserByName(String username) {
        return given().spec(requestSpecification)
                .basePath("/user/" + username)
                .get();
    }

    public Response userLogin(User user) {
        return given().spec(requestSpecification)
                .basePath("/user/login")
                .queryParam("username", user.getUsername())
                .queryParam("password", user.getPassword())
                .get();
    }

    public Response userLogout() {
        return given().spec(requestSpecification)
                .basePath("/user/logout")
                .get();
    }

    public Response createPet(Pet pet) {
        return given().spec(requestSpecification)
                .basePath("/pet")
                .body(pet)
                .post();
    }

    public Response uploadPetImage(Pet pet, String filePath) {
        return given().spec(requestSpecification)
                .contentType("multipart/form-data")
                .basePath("/pet/{petId}/uploadImage")
                .multiPart("file", new File(filePath))
                .pathParam("petId", pet.getId())
                .formParam("additionalMetadata", "test")
                .post();
    }

    public static <T> T convertResponseToClass(Response response, Class<T> targetClass) {
        return response.getBody().as(targetClass, JACKSON_2);
    }

}
