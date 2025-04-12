package week3.day2.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

public class CreateStub_ResponseJsonAsFile {
    static WireMockServer wireMockServer = new WireMockServer(8282);

    public static void main(String[] args) {
        wireMockServer.start();
        ResponseDefinitionBuilder responseDefinitionBuilder =
                WireMock.aResponse().withStatus(200)

                        .withBodyFile("MockResponse.json");

        MappingBuilder mappingBuilder =
                WireMock.get("/java/welcome")
                        .willReturn(responseDefinitionBuilder);

        wireMockServer.stubFor(mappingBuilder);

    }
}
