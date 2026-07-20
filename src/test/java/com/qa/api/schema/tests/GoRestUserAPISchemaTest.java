package com.qa.api.schema.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoRestUserAPISchemaTest extends BaseTest {

    @Test
    public void getUsersAPISchemaTest() {

        ConfigManager.set("bearertoken", "demo_token");

        Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);

        Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/getuserschema.json"));
    }

    @Test
    public void createAUserAPISchemaTest() {

        ConfigManager.set("bearertoken", "demo_token");

        User user = User.builder()
                .name("Ajay api")
                .status("active")
                .email(StringUtil.getRandomEmailId())
                .gender("male")
                .build();

        Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/createuserschema.json"));
    }
}
