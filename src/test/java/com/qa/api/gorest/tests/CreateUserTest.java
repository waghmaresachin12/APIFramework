package com.qa.api.gorest.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class CreateUserTest extends BaseTest {

    private String tokenId;

    @BeforeClass
    public void setUpToken(){
        tokenId = "demo-token";
        ConfigManager.set("bearertoken", tokenId);
    }

    @DataProvider
    public Object[][] getUserData(){
        return new Object[][]{
                {"Sachin", "male", "active"},
                {"Ranjit", "male", "active"},
                {"Alvin", "male", "active"}
        };
    }

    @DataProvider
    public Object[][] getUserExcelData(){
        return ExcelUtil.readData(AppConstants.CREATE_USER_SHEET_NAME);
    }

    @Test(dataProvider = "getUserExcelData")
    public void createAUserTest(String name, String gender, String status){
        User user =  new User(null, name, StringUtil.getRandomEmailId(), gender,status);
        Response response = restClient.post(BASE_URL_GOREST,GOREST_USERS_ENDPOINT,user,null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.jsonPath().getString("name"), name);
        Assert.assertEquals(response.jsonPath().getString("gender"), gender);
        Assert.assertEquals(response.jsonPath().getString("status"), status);
        Assert.assertNotNull(response.jsonPath().getString("id"));
    }

    @Test
    public void createAUserTestWithJsonString(){
        String email = StringUtil.getRandomEmailId();
        String userJson = "{\n" +
                "    \"name\": \"Kamal Kumar\",\n" +
                "    \"email\": \""+email+"\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";
        Response response = restClient.post(BASE_URL_GOREST,GOREST_USERS_ENDPOINT,userJson,null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.jsonPath().getString("name"), "Kamal Kumar");
        Assert.assertNotNull(response.jsonPath().getString("id"));
    }

    @Test
    public void createAUserTestUsingJsonfile(){
        File userFile = new File("./src/test/resources/jsons/user.json");
        Response response = restClient.post(BASE_URL_GOREST,GOREST_USERS_ENDPOINT,userFile,null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.jsonPath().getString("name"), "Mohit Kumar");
        Assert.assertNotNull(response.jsonPath().getString("id"));
    }
}
