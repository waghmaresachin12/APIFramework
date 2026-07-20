package com.qa.api.utils;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class XmlPathUtil {

    private static XmlPath getXmlPath(Response response) {
        String responseBody = response.body().asString();
        return new XmlPath(responseBody);
    }

    public static <T> T read(Response response, String xmlPathExpression) {
        XmlPath xmlPath = getXmlPath(response);
        return xmlPath.get(xmlPathExpression);
    }

    public static <T> List<T> readList(Response response, String xmlPathExpression) {
        XmlPath xmlPath = getXmlPath(response);
        return xmlPath.getList(xmlPathExpression);
    }

}
