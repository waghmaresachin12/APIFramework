package com.qa.api.contacts.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactsAPITest extends BaseTest {

    private String tokenId;

    @BeforeMethod
    public void getToken() {
        ContactsCredentials creds = ContactsCredentials.builder()
                .email("sachinw@gmail.com")
                .password("Test@123")
                .build();

        Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, creds, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(response.statusCode(),200);
        tokenId = response.jsonPath().getString("token");
        System.out.println("Contacts login token --->  " + tokenId);
        ConfigManager.set("bearertoken", tokenId);
    }

    @Test
    public void getAllContactsTest(){
        Response response = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN,ContentType.JSON);
        Assert.assertEquals(response.statusCode(),200);
    }

}
