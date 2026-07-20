package com.qa.api.amadeus.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class AmadeusAPITest extends BaseTest {

    private String accessToken;

    @BeforeMethod
    public void getOAuth2Token() {
        Response response = restClient.post(BASE_URL_OAUTH2_AMADEUS, AMADEUS_OAUTH2_TOKEN_ENDPOINT,
                ConfigManager.get("clientid"), ConfigManager.get("clientsecret"), ConfigManager.get("granttype"), ContentType.URLENC);

        accessToken = response.jsonPath().getString("access_token");
        System.out.println("Access Token: " + accessToken);
        ConfigManager.set("bearertoken", accessToken);
    }

    @Test
    public void getFlightDetailsTest() {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("origin", "PAR");
        queryParams.put("maxPrice", "200");

        Response response = restClient.get(BASE_URL_OAUTH2_AMADEUS, AMADEUS_FLIGHT_DEST_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.ANY);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

}
