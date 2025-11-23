package bitxon.aws.quarkus;

import bitxon.aws.quarkus.model.Order;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Disabled // TODO implement with testcontainers-localstack
@QuarkusTest
public class OrderLambdaQuarkusTest {

    @Test
    public void test() {
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
