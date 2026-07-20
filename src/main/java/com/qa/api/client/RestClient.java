package com.qa.api.client;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.Base64;
import java.util.Map;

public class RestClient {

    //Maintain restAssured code in restClient - like all CRUD operations

    //define Response Specs:
    private ResponseSpecification responseSpec200 = expect().statusCode(200);
    private ResponseSpecification responseSpec201 = expect().statusCode(201);
    private ResponseSpecification responseSpec204 = expect().statusCode(204);
    private ResponseSpecification responseSpec400 = expect().statusCode(400);
    private ResponseSpecification responseSpec404 = expect().statusCode(404);
    private ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
    private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));

    private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {

        ChainTestListener.log("API base url : " + baseUrl);

        RequestSpecification request = RestAssured.given().log().all()
                .baseUri(baseUrl)
                .contentType(contentType)
                .accept(contentType);

        switch (authType) {
            case BEARER_TOKEN:
                request.header("Authorization", "Bearer " + ConfigManager.get("bearertoken"));
                break;
            case BASIC_AUTH:
                request.header("Authorization", "Basic " + generateBasicAuthToken());
                break;
            case API_KEY:
                request.header("x-api-key", "api key");
                break;
            case NO_AUTH:
                System.out.println("Auth is not required...");
                break;
            default:
                System.out.println("this auth is not supported... please pass the right AuthType...");
                throw new APIException("===Invalid Auth===");
        }
        return request;
    }

    private String generateBasicAuthToken() {
        String credentials = ConfigManager.get("basicauthusername") + ":" + ConfigManager.get("basicauthpassword");
        //admin:admin ---> "YWRtaW46YWRtaw4="  (base64 encoded value)
        //Generate the encoded string in java
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    private void applyParams(RequestSpecification
                                     request, Map<String, String> queryParams, Map<String, String> pathParams) {
        //log
        ChainTestListener.log("Query Params : " + queryParams);
        ChainTestListener.log("Path Params : " + pathParams);

        if (queryParams != null) {
            request.queryParams(queryParams);
        }
        if (pathParams != null) {
            request.pathParams(pathParams);
        }
    }

    //CRUD operations - this methoda will be exposed to testng and testng will verify
    //get:

    /**
     * This method is used to call get apis
     *
     * @param baseUrl
     * @param endPoint
     * @param queryParams
     * @param pathParams
     * @param authType
     * @param contentType
     * @return it returns get api call response
     */
    public Response get(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);
        //Before going to testng we are verifying the status code and then extract the response
        Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();
        response.prettyPrint();
        return response;
    }

    //post:
    // T body : T is reserved keyward and means 'type' - T body means any type
    //T Body because body type can be anything except FILE body & add <T> before return type in method
    public <T> Response post(String baseUrl, String endPoint, T body, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);
        Response response = request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
        response.prettyPrint();
        return response;
    }

    //Post for File body - Json file request (T will not support to File type body)
    public Response post(String baseUrl, String endPoint, File file, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);
        Response response = request.body(file).post(endPoint).then().spec(responseSpec200or201).extract().response();
        response.prettyPrint();
        return response;
    }

    //only for Oauth2 apis
    public Response post(String baseUrl, String endPoint, String clientId, String clientSecret, String grantType, ContentType contentType) {

        Response response = RestAssured.given()
                .contentType(contentType)
                .formParam("grant_type", grantType)
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .when()
                .post(baseUrl + endPoint);

        response.prettyPrint();
        return response;
    }

    public <T> Response put(String baseUrl, String endPoint, T body, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);
        Response response = request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
        response.prettyPrint();
        return response;
    }

    public <T> Response patch(String baseUrl, String endPoint, T body, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);
        Response response = request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
        response.prettyPrint();
        return response;
    }

    //body is not required in delete call
    public Response delete(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);
        Response response = request.delete(endPoint).then().spec(responseSpec204).extract().response();
        response.prettyPrint();
        return response;
    }


}

