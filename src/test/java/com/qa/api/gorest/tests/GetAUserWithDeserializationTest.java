package com.qa.api.gorest.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtil;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetAUserWithDeserializationTest extends BaseTest {

    private String tokenId;

    @BeforeClass
    public void setUpToken() {
        tokenId = "demo-token";
        ConfigManager.set("bearertoken", tokenId);
    }

    @Test
    public void createAUserTest() {
        User user = new User(null, "Sachin", StringUtil.getRandomEmailId(), "male", "active");
        Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.jsonPath().getString("name"), "Sachin");
        Assert.assertNotNull(response.jsonPath().getString("id"));

        String userId =  response.jsonPath().getString("id");

        //GET:
        //2. GET : Fetch the same user using the same user ID
        Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertTrue(responseGet.statusLine().contains("OK"));

        User userResponse = JsonUtils.deserialize(responseGet, User.class);
        Assert.assertEquals(userResponse.getName(), user.getName());
    }

}
