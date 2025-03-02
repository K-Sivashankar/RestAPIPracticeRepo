package week3.day2.wiremock.RequestBodyAsAnUploadFile;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestFileStub {
    public static void main(String[] args) throws Exception {
        // Read file content as bytes
        byte[] fileBytes = Files.readAllBytes(Paths.get("src/test/resources/sample.json"));

        stubFor(post(urlEqualTo("/upload"))
                .withRequestBody(binaryEqualTo(fileBytes)) // Matches the exact binary content
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("File uploaded successfully"))
        );
    }
}

