package com.example.qa;


import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeaderTransformer extends ResponseDefinitionTransformer {

    private final Pattern interpolationPattern = Pattern.compile("\\$\\(.*?\\)");

    private String responseJsonSample = "{" +
            "  \"updated_at\": \"1439801564\"," +
            "  \"sub\": \"$(var)\"" +
            "}";

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource, Parameters parameters) {
        return ResponseDefinitionBuilder
                .like(responseDefinition)
                .but()
                .withBody(transformResponse(responseJsonSample))
                .build();
    }

    /*@Override
    public Response transform(Request request, Response response, FileSource fileSource, Parameters parameters) {
        return Response
                .response()
                .body(transformResponse(responseJsonSample))

    }*/

    private String transformResponse(String response) {
        String modifiedResponse = response;

        Matcher matcher = interpolationPattern.matcher(response);

        while (matcher.find()) {
            String group = matcher.group();
            modifiedResponse = modifiedResponse.replace(group, getValue());
        }

        return modifiedResponse;
    }

    private long getRandomNumberInRange() {
        return (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
    }

    private String getValue() {
        return String.valueOf(getRandomNumberInRange());
    }

    public String name() {
        return "idp-transformer";
    }
}
