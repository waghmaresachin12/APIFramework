package com.qa.api.utils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class JsonPathValidatorUtil {

    private static String getJsonResponseAsString(Response response) {
        return response.getBody().asString();
    }


    //For specific single value

    /**
     * This method is used to get only specific single value
     * @param response
     * @param jsonPath
     * @return
     * @param <T>
     */
    public static <T> T read(Response response, String jsonPath) { // $.id -- 123
        ReadContext ctx = JsonPath.parse(getJsonResponseAsString(response));
        return ctx.read(jsonPath);
    }

    //For list
    public static <T> List<T> readList(Response response, String jsonPath) { // $.id -- 123
        ReadContext ctx = JsonPath.parse(getJsonResponseAsString(response));
        return ctx.read(jsonPath);
    }

    //for map of list
    public static <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath) { // $.id -- 123
        ReadContext ctx = JsonPath.parse(getJsonResponseAsString(response));
        return ctx.read(jsonPath);
    }
}
