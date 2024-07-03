package com.rest.api.automation.utils;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class RestAPIUtils {
    static String env;

    //Get the environment details to run the tests
    public static String getEnvToRun() {
        String url = null;
        if (System.getProperty("environment") != null) {
            env = System.getProperty("environment");
        } else {
            env = ConfigHelper.returnPropVal("config", "environment");
        }
        switch (env.toLowerCase()) {
            case "qa":
                url = ConfigHelper.GetBaseUrl("qaUrl");
                break;
            case "dev":
                url = ConfigHelper.GetBaseUrl("devUrl");
                break;
            default:
                System.out.println("Invalid Environment Value");
        }
        return url;
    }

    // Get Db Environment Details
    public static String getDbEnvDetails() {
        String dbEnv = null;
        if (System.getProperty("environment") != null) {
            env = System.getProperty("environment");
        } else {
            env = ConfigHelper.returnPropVal("config", "environment");
        }
        switch (env.toLowerCase()) {
            case "qa":
                dbEnv = TestConstants.ENV_QA;
                break;
            case "dev":
                dbEnv = TestConstants.ENV_DEV;
                break;
            default:
                System.out.println("Invalid Environment in Config.properties file");
        }
        return dbEnv;
    }

    public static String getDbName(String db_Name, String environment) {
        if (environment.equalsIgnoreCase("")) {
            System.out.println("Inappropriate or empty Environment");
        } else if (environment.equalsIgnoreCase(TestConstants.ENV_QA)
                || environment.equalsIgnoreCase(TestConstants.ENV_DEV)) {
            return db_Name;
        }
        return "Invalid or null DB name";
    }

    /**
     * @param response
     * @param key
     * @return Returns json Attribute to any number of keys, The keys can be 1 to
     * anyNumber, the method will handle all keys and returns the required
     * attributes from response
     */
    public static LinkedHashMap<String, String> getSpecificStringJsonAttribute(Response response, String[] key) { //specific data from response
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        JSONObject json = new JSONObject(response.asString());
        for (int i = 0; i < key.length; i++) {
            map.put(key[i], json.getString(key[i]));
        }
        return map;
    }

    public static LinkedHashMap<String, Integer> getSpecificIntegerJsonAttribute(Response response, String[] key) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        JSONObject json = new JSONObject(response.asString());
        for (int i = 0; i < key.length; i++) {
            map.put(key[i], json.getInt(key[i]));
        }
        return map;
    }
}