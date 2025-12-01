package bitxon.aws.spring;

import bitxon.aws.spring.model.Order;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Import(TestcontainersConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderLambdaSpringTest {

    @LocalServerPort
    Integer port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void test() {
        var in = new Order("Birthday Gift");

        //@formatter:off
        given()
            .contentType("application/json")
            .body(in)
        .when()
            .post()
        .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("name", is("BIRTHDAY GIFT"));
        //@formatter:on
    }

}
