To build the wiremock stub:

`mvn clean assembly:assembly -DdescriptorId=jar-with-dependencies`

To run the stub:

`java -cp "target/wiremock-id-stub-1.0-SNAPSHOT.jar;wiremock-2.0.8-beta-standalone.jar" com.github.tomakehurst.wiremock.standalone.WireMockServerRunner --extensions com.example.qa.HeaderTransformer`

End-point
`http://localhost:8080/idp/oauth2/userinfo`