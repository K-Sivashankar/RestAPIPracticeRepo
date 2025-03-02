package week3.day2.wiremock.RequestjsonAsString;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;

public class WireMockJsonStubString {
    public static void main(String[] args) {
        // Start WireMock server on port 8080
        WireMockServer wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        // Define stub for a POST request with JSON body
        stubFor(post(urlEqualTo("/api/users"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{ \"name\": \"John\", \"age\": 30 }"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"message\": \"User created successfully\" }")
                        .withHeader("Content-Type", "application/json"))
        );

        System.out.println("WireMock Stub for JSON body is running...");
    }
}

