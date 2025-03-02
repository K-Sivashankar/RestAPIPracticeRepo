package week3.day2.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

public class CreateStub_ResponseJsonAsString {
    static WireMockServer wireMockServer = new WireMockServer(8181);

    public static void main(String[] args) {
        wireMockServer.start();
        ResponseDefinitionBuilder builder =
                WireMock.aResponse().withStatus(200)
                        .withBody("""
                                
                                {
                                  "message": "Hi Welcome to Java version 17"
                                } 
                                
                                """);
//                        .withBodyFile("src/main/resources/MockResponse.json");

        MappingBuilder mappingBuilder =
                WireMock.get("/java/welcome")
                        .willReturn(builder);

        wireMockServer.stubFor(mappingBuilder);
        //WireMock.stubFor(mappingBuilder);
        //---------------------------------------------------
        // above code called via WireMock will work if server is launched manually
        //If server is lauched via WireMockServer instance,
        // then call stubFor method using WireMockServer instance only.
        //-----------------------------------------------------------
    }
}
//  Steps to be followed
// Initiate wiremock server manually
// If you want to launch in different port , initiate via code
//Run this file
//Then go to postman
//check if stub is configured correctly by hitting this api
//      http://localhost:8181/__admin
//check response by sending request to mock url
//  http://localhost:8181/java/welcome