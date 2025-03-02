package week3.day2.wiremock.RequestjsonAsFile;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WireMockJsonFileStub {
    public static void main(String[] args) throws Exception {
        // Start WireMock server
        WireMockServer wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        // Read JSON file as a string
        String requestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/request.json")));

        // Define WireMock stub with JSON file content
        stubFor(post(urlEqualTo("/api/users"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson(requestBody, true, true)) // Ignore extra fields, whitespace
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"message\": \"User created successfully\" }")
                        .withHeader("Content-Type", "application/json"))
        );

        System.out.println("WireMock stub with JSON file is running...");
    }
}

