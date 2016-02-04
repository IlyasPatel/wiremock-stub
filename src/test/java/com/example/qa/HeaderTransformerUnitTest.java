package com.example.qa;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.jayway.restassured.RestAssured.given;

public class HeaderTransformerUnitTest {

    @Rule
    public WireMockRule sut = new WireMockRule(wireMockConfig().port(8080).extensions(new HeaderTransformer()));

    @Test
    public void shouldGenerateRandomIdTokenInHeader() {
        sut.stubFor(get(urlEqualTo("/test/userinfo"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Authorization", "Basic foo")));

        Response post = given()
                .when()
                .get("/test/userinfo");

        System.out.println(post.prettyPeek());

        sut.verify(getRequestedFor(urlEqualTo("/test/userinfo")));
    }


}
