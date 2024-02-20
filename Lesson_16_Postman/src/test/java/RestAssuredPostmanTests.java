import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class RestAssuredPostmanTests {
    @Test
    public void testPostmanGet() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-forwarded-proto", "https");
        headers.put("host", "postman-echo.com");
        headers.put("accept", "*/*");
        headers.put("accept-encoding", "gzip, deflate");
        headers.put("cache-control", "no-cache");
        headers.put("postman-token", "5c27cd7d-6b16-4e5a-a0ef-191c9a3a275f");
        headers.put("user-agent", "PostmanRuntime/7.6.1");
        headers.put("x-forwarded-port", "443");

        Map<String, String> args = new HashMap<>();
        args.put("foo1", "bar1");
        args.put("foo2", "bar2");

        Response response = given()
                .baseUri("https://postman-echo.com")
                .headers(headers)
                .queryParams(args)
                .when()
                .log().all()
                .get("/get");

        assertEquals(200, response.getStatusCode());

        assertEquals("bar1", response.jsonPath().getString("args.foo1"));
        assertEquals("bar2", response.jsonPath().getString("args.foo2"));

        assertEquals("https", response.jsonPath().getString("headers.x-forwarded-proto"));
        assertEquals("postman-echo.com", response.jsonPath().getString("headers.host"));
        assertEquals("*/*", response.jsonPath().getString("headers.accept"));
        assertEquals("gzip, deflate", response.jsonPath().getString("headers.accept-encoding"));
        assertEquals("no-cache", response.jsonPath().getString("headers.cache-control"));
        assertEquals("5c27cd7d-6b16-4e5a-a0ef-191c9a3a275f", response.jsonPath().getString("headers.postman-token"));
        assertEquals("PostmanRuntime/7.6.1", response.jsonPath().getString("headers.user-agent"));
        assertEquals("443", response.jsonPath().getString("headers.x-forwarded-port"));
        assertEquals("https://postman-echo.com/get?foo1=bar1&foo2=bar2", response.jsonPath().getString("url"));
    }
    @Test
    public void testPostmanPost() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-forwarded-proto", "https");
        headers.put("x-forwarded-port", "443");
        headers.put("host", "postman-echo.com");
        headers.put("x-amzn-trace-id", "Root=1-63e0c942-584c7dab4a1c52d34d581b03");
        headers.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("user-agent", "PostmanRuntime/7.30.1");
        headers.put("accept", "*/*");
        headers.put("cache-control", "no-cache");
        headers.put("postman-token", "159c6495-8e5f-4ce0-bacd-ac399a86ec03");
        headers.put("accept-encoding", "gzip, deflate, br");

        Map<String, String> formData = new HashMap<>();
        formData.put("foo1", "bar1");
        formData.put("foo2", "bar2");

        Response response = given()
                .baseUri("https://postman-echo.com")
                .headers(headers)
                .formParams(formData)
                .when()
                .log().all()
                .post("/post");

        assertEquals(200, response.getStatusCode());

        assertEquals("bar1", response.jsonPath().getString("form.foo1"));
        assertEquals("bar2", response.jsonPath().getString("form.foo2"));

        assertEquals("https", response.jsonPath().getString("headers.x-forwarded-proto"));
        assertEquals("443", response.jsonPath().getString("headers.x-forwarded-port"));
        assertEquals("postman-echo.com", response.jsonPath().getString("headers.host"));
//        assertEquals("Root=1-63e0c942-584c7dab4a1c52d34d581b03", response.jsonPath().getString("headers.x-amzn-trace-id"));
        assertEquals("application/x-www-form-urlencoded; charset=UTF-8", response.jsonPath().getString("headers.content-type"));
        assertEquals("PostmanRuntime/7.30.1", response.jsonPath().getString("headers.user-agent"));
        assertEquals("*/*", response.jsonPath().getString("headers.accept"));
        assertEquals("no-cache", response.jsonPath().getString("headers.cache-control"));
        assertEquals("159c6495-8e5f-4ce0-bacd-ac399a86ec03", response.jsonPath().getString("headers.postman-token"));
        assertEquals("gzip, deflate, br", response.jsonPath().getString("headers.accept-encoding"));
        assertEquals("https://postman-echo.com/post", response.jsonPath().getString("url"));

//      assertTrue(response.getHeader("x-amzn-trace-id").contains("Root=1-63e0c942-584c7dab4a1c52d34d581b03"));
        String traceIdHeader = response.getHeader("x-amzn-trace-id");
        if (traceIdHeader != null) {
            assertTrue(traceIdHeader.contains("Root=1-63e0c942-584c7dab4a1c52d34d581b03"));
        } else {
            System.out.println("x-amzn-trace-id header нет в ответе.");
        }
    }
}