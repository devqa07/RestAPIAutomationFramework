package com.rest.api.automation.requestBuilder;

import com.rest.api.automation.base.RestTestBase;
import com.rest.api.automation.utils.ConfigHelper;
import com.rest.api.automation.utils.RestAPIUtils;
import com.rest.api.automation.utils.TestConstants;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;

public class Bookings extends RestTestBase {
    List<String[]> data = csv.readCsvData(TestConstants.BOOKING_CSV);
    String token;

    public JSONObject BookingDetailsBody(String firstName, String lastName, String totalPrice, String depositPaid,
                                         String additionalNeeds, String checkIn, String checkOut) {
        JSONObject body = new JSONObject();
        body.put("firstname", firstName);
        body.put("lastname", lastName);
        body.put("totalprice", Integer.parseInt(totalPrice));
        body.put("depositpaid", Boolean.parseBoolean(depositPaid));
        body.put("additionalneeds", additionalNeeds);
        body.put("bookingdates", getDates(checkIn, checkOut));
        return body;
    }

    private JSONObject getDates(String checkIn, String checkOut) {
        JSONObject dates = new JSONObject();
        dates.put("checkin", checkIn);
        dates.put("checkout", checkOut);
        return dates;
    }

    public JSONObject createToken(String userName, String password) {
        JSONObject token = new JSONObject();
        token.put("username", userName);
        token.put("password", password);
        return token;
    }

    public JSONObject partialUpdateBooking(String firstName, String totalPrice) {
        JSONObject partialUpdate = new JSONObject();
        partialUpdate.put("firstname", firstName);
        partialUpdate.put("totalprice", Integer.parseInt(totalPrice));
        return partialUpdate;
    }

    public String getToken() {
        String authBody = createToken(csv.getSpecificCSVData(data, 6, 1), csv.getSpecificCSVData(data, 7, 1)).toString();
        Response tokenResp = cm.doPOST(ConfigHelper.returnPropVal("config", "createToken"), authBody);
        token = RestAPIUtils.getSpecificStringJsonAttribute(tokenResp, new String[]{"token"})
                .get("token");
        return token;
    }
}