package com.rest.api.automation.utils;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CommonMethods {
    String endPoint;
    Response response;

    public Response doPOST(String endpoint, String body) {
        endPoint = RestAPIUtils.getEnvToRun() + endpoint;
        response = RestAssured.given().with().contentType("application/json")
                .filter(new RequestLoggingFilter(TestConstants.REQUEST_CAPTURE)).when().log().all().body(body)
                .post(endPoint);
        System.out.println(
                "=================================================RESPONSE=======================================================================");
        response.prettyPrint();

        System.out.println(
                "================================================================================================================================");
        return response;
    }

    public Response doPUT(String endpoint, String body) {
        endPoint = RestAPIUtils.getEnvToRun() + endpoint;
        response = given().header("Content-Type", "application/json").filter(new RequestLoggingFilter(TestConstants.REQUEST_CAPTURE)).when().log().all().body(body).put(endPoint);
        System.out.println(
                "================================================RESPONSE=======================================================================");
        response.prettyPrint();
        System.out.println(
                "===============================================================================================================================");
        return response;
    }

    public Response doPUT(String endpoint, Map<String, String> headerMap, String body) {
        endPoint = RestAPIUtils.getEnvToRun() + endpoint;
        response = given().header("Content-Type", "application/json").headers(headerMap)
                .filter(new RequestLoggingFilter(TestConstants.REQUEST_CAPTURE)).when().log().all().body(body)
                .put(endPoint);
        System.out.println(
                "================================================RESPONSE=======================================================================");
        response.prettyPrint();
        System.out.println(
                "===============================================================================================================================");
        return response;
    }

    public Response doGET(String endpoint) {
        endPoint = RestAPIUtils.getEnvToRun() + endpoint;
        response = given().header("Content-Type", "application/json")
                .filter(new RequestLoggingFilter(TestConstants.REQUEST_CAPTURE)).when().log().all().get(endPoint);
        System.out.println(
                "================================================RESPONSE=======================================================================");
        response.prettyPrint();
        System.out.println(
                "===============================================================================================================================");
        return response;
    }

    public Response doPATCH(String endpoint, Map<String, String> headerMap, String body) {
        endPoint = RestAPIUtils.getEnvToRun() + endpoint;
        response = given().header("Content-Type", "application/json").headers(headerMap)
                .filter(new RequestLoggingFilter(TestConstants.REQUEST_CAPTURE)).when().log().all().body(body)
                .patch(endPoint);
        System.out.println(
                "================================================RESPONSE=======================================================================");
        response.prettyPrint();
        System.out.println(
                "===============================================================================================================================");
        return response;
    }

    public Response doDELETE(String endpoint, Map<String, String> headerMap) {
        endPoint = RestAPIUtils.getEnvToRun() + endpoint;
        response = given().header("Content-Type", "application/json").headers(headerMap)
                .filter(new RequestLoggingFilter(TestConstants.REQUEST_CAPTURE)).when().log().all().delete(endPoint);
        System.out.println(
                "================================================RESPONSE=======================================================================");
        response.prettyPrint();
        System.out.println(
                "===============================================================================================================================");
        return response;
    }

    public Response doPUT(String endpoint, Map<String, String> headerMap) {
        endPoint = RestAPIUtils.getEnvToRun() + endpoint;
        response = given().header("Content-Type", "application/json").headers(headerMap)
                .filter(new RequestLoggingFilter(TestConstants.REQUEST_CAPTURE)).when().log().all()
                .put(endPoint);
        System.out.println(
                "================================================RESPONSE=======================================================================");
        response.prettyPrint();
        System.out.println(
                "===============================================================================================================================");
        return response;
    }
}