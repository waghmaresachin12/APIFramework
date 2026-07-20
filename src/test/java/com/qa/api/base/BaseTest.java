package com.qa.api.base;

import com.qa.api.client.RestClient;
import com.qa.api.manager.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

//@Listeners(ChainTestListener.class)
public class BaseTest {

    protected RestClient restClient;

    //Easy to access this URLs and EndPoints in child test class, no need to call from properties file
    //******* API Base URLs *******//
    protected final static String BASE_URL_GOREST = "https://www.gorest.in";
    protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
    protected final static String BASE_URL_REQRES = "https://regres.in";
    protected final static String BASE_URL_BASIC_AUTH = "https://the-internet.herokuapp.com";
    protected final static String BASE_URL_PRODUCTS = "https://fakestoreapi.com";
    protected final static String BASE_URL_OAUTH2_AMADEUS = "https://test.api.amadeus.com";
    protected final static String BASE_URL_ERGAST_CIRCUIT= "http://ergast.com";

    //******* API Endpoints *******//
    protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
    protected final static String CONTACTS_LOGIN_ENDPOINT = "/users/login";
    protected final static String CONTACTS_ENDPOINT = "/contacts";
    protected final static String REQRES_ENDPOINT = "/api/users";
    protected final static String BASIC_AUTH_ENDPOINT = "/basic_auth";
    protected final static String PRODUCTS_ENDPOINT = "/products";
    protected final static String AMADEUS_OAUTH2_TOKEN_ENDPOINT = "/v1/security/oauth2/token";
    protected final static String AMADEUS_FLIGHT_DEST_ENDPOINT = "/v1/shopping/flight-destinations";
    protected final static String EARGAST_CIRCUIT_ENDPOINT = "/api/f1/2017/circuits.xml";


    @BeforeSuite
    public void initSetup(){
        RestAssured.filters(new AllureRestAssured());
//        BASE_URL_GOREST = ConfigManager.get("baseurl.gorest").trim();
//
    }

    @BeforeTest
    public void setup(){
        //created RestClient class object
        restClient = new RestClient();
    }

    //@After

}
