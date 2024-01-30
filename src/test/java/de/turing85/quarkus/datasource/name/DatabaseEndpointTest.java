package de.turing85.quarkus.datasource.name;

import jakarta.ws.rs.core.Response;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(DatabaseEndpoint.class)
class DatabaseEndpointTest {
  @Test
  void getExistingDatasource() {
    //@formatter:off
    RestAssured
        .given().queryParam("name", "foo")
        .when().get()
        .then()
            .statusCode(is(Response.Status.OK.getStatusCode()))
            .body(is("I have a datasource with name \"foo\""));
    //@formatter:on
  }

  @Test
  void getNonExistingDatasource() {
    //@formatter:off
    RestAssured
        .given().queryParam("name", "bar")
        .when().get()
        .then()
            .statusCode(is(Response.Status.NOT_FOUND.getStatusCode()))
            .body(is("I have no datasource with name \"bar\""));
    //@formatter:on
  }
}
