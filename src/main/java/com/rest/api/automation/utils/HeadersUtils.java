package com.rest.api.automation.utils;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class HeadersUtils {
    public Map<String, String> headerMap = new HashMap<String, String>();
    static ArrayList<Header> dataHeaders = new ArrayList<>();
    static ArrayList<Header> hppHeaders = new ArrayList<>();
    public Headers headers;
    public String uid = "";

    public HeadersUtils setHeaders(String key, String value) {
        headerMap.put(key, value);
        return this;
    }

    public Headers getHeaders() {
        return headers;
    }

    public HeadersUtils clearHeaders() {
        dataHeaders.clear();
        hppHeaders.clear();
        headerMap.clear();
        return this;
    }
}