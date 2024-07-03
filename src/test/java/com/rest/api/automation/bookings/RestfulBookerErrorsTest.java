package com.rest.api.automation.bookings;

import com.aventstack.extentreports.Status;
import com.rest.api.automation.base.RestTestBase;
import com.rest.api.automation.requestBuilder.Bookings;
import com.rest.api.automation.utils.ConfigHelper;
import com.rest.api.automation.utils.TestConstants;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RestfulBookerErrorsTest extends RestTestBase {
    SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
    Bookings bookingReq = new Bookings();
    List<String[]> data = csv.readCsvData(TestConstants.BOOKING_CSV);
    String body, firstName = FAKER.name().firstName(), lastName = FAKER.name().lastName(), additionalNeeds = "Breakfast", totalPrice = "100",
            checkIn = formatter.format(FAKER.date().past(20, TimeUnit.DAYS)),
            checkOut = formatter.format(FAKER.date().future(5, TimeUnit.DAYS)), bookingId = csv.getSpecificCSVData(data, 0, 1);
    Response response;

    @Test
    public void verifyCreateBookingWithInvalidMethod() {
        extentLog.info(customReport(methodName + ": This test verifies Create Booking error response with invalid ReqMethod"));
        body = bookingReq.BookingDetailsBody(firstName, lastName, totalPrice, "true", additionalNeeds, checkIn, checkOut).toString();
        response = cm.doPUT(ConfigHelper.returnPropVal("config", "booking"), body);
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkErrorResponse(response, HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void verifyGetBookingDetailsWithInvalidMethod() {
        extentLog.info(customReport(methodName + ": This test verifies Get Booking Details Error Response With Invalid Method "));
        response = cm.doDELETE(ConfigHelper.returnPropVal("config", "booking") + "/" + bookingId, headersUtils.getHeaderMap());
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkErrorResponse(response, HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void verifyUpdateBookingWithInvalidMethod() {
        extentLog.info(customReport(methodName + ": This test updates booking details With Invalid Method"));
        headersUtils.setHeaders(TestConstants.COOKIE_HEADER, "token=abc+y35647");
        body = bookingReq.BookingDetailsBody(csv.getSpecificCSVData(data, 1, 2), lastName, totalPrice, "true", additionalNeeds, checkIn, checkOut).toString();
        response = cm.doPATCH(ConfigHelper.returnPropVal("config", "booking") + "/" + bookingId, headersUtils.getHeaderMap(), body);
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkErrorResponse(response, HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void verifyDeleteBookingWithInvalidMethod() {
        extentLog.info(customReport(methodName + ": This test verifies delete booking With Invalid Method"));
        response = cm.doPUT(ConfigHelper.returnPropVal("config", "booking") + "/" + bookingId, headersUtils.getHeaderMap());
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkSuccessResponse(response, HttpStatus.SC_FORBIDDEN);
    }
}