package com.rest.api.automation.bookings;

import com.aventstack.extentreports.Status;
import com.rest.api.automation.base.RestTestBase;
import com.rest.api.automation.requestBuilder.Bookings;
import com.rest.api.automation.responseParser.BookingsBaseTest;
import com.rest.api.automation.utils.ConfigHelper;
import com.rest.api.automation.utils.RestAPIUtils;
import com.rest.api.automation.utils.TestConstants;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RestfulBookerE2EFlowSuccessTest extends RestTestBase {
    BookingsBaseTest bs = new BookingsBaseTest();
    Bookings bookingReq = new Bookings();
    SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
    List<String[]> data = csv.readCsvData(TestConstants.BOOKING_CSV);
    String body, firstName = csv.getSpecificCSVData(data, 1, 1), lastName = csv.getSpecificCSVData(data, 2, 1),
            totalPrice = csv.getSpecificCSVData(data, 3, 1), depositPaid = csv.getSpecificCSVData(data, 4, 1),
            additionalNeeds = csv.getSpecificCSVData(data, 5, 1), checkIn = formatter.format(FAKER.date().past(20, TimeUnit.DAYS)),
            checkOut = formatter.format(FAKER.date().future(5, TimeUnit.DAYS));
    Response response;
    private int bookingId;

    @Test(priority = 0)
    public void verifyCreateBooking() {
        extentLog.info(customReport(methodName + ": This test Creates a new booking"));
        body = bookingReq.BookingDetailsBody(firstName, lastName, totalPrice, depositPaid, additionalNeeds, checkIn, checkOut).toString();
        response = cm.doPOST(ConfigHelper.returnPropVal("config", "booking"), body);
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkSuccessResponse(response, HttpStatus.SC_OK);
        bs.validateCreateBookingResponse(response, firstName, lastName, totalPrice, depositPaid, additionalNeeds, checkIn, checkOut);
        bookingId = RestAPIUtils.getSpecificIntegerJsonAttribute(response, new String[]{"bookingid"})
                .get("bookingid");
    }

    @Test(priority = 1)
    public void verifyGetBookingDetails() {
        extentLog.info(customReport(methodName + ": This test verifies Get Booking Details "));
        response = cm.doGET(ConfigHelper.returnPropVal("config", "booking") + "/" + bookingId);
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkSuccessResponse(response, HttpStatus.SC_OK);
        bs.validateGetBookingResponse(response);
    }

    @Test(priority = 2)
    public void verifyUpdateBooking() {
        extentLog.info(customReport(methodName + ": This test updates booking details"));
        headersUtils.setHeaders(TestConstants.COOKIE_HEADER, "token=" + bookingReq.getToken());
        body = bookingReq.BookingDetailsBody(csv.getSpecificCSVData(data, 1, 2), lastName, totalPrice, depositPaid, additionalNeeds, checkIn, checkOut).toString();
        response = cm.doPUT(ConfigHelper.returnPropVal("config", "booking") + "/" + bookingId, headersUtils.getHeaderMap(), body);
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkSuccessResponse(response, HttpStatus.SC_OK);
        bs.validateUpdateBookingResponse(response, csv.getSpecificCSVData(data, 1, 2));
    }

    @Test(priority = 3)
    public void verifyPartialUpdateBooking() {
        extentLog.info(customReport(methodName + ": This test updates booking details"));
        headersUtils.setHeaders(TestConstants.COOKIE_HEADER, "token=" + bookingReq.getToken());
        body = bookingReq.partialUpdateBooking(csv.getSpecificCSVData(data, 1, 3), csv.getSpecificCSVData(data, 3, 2)).toString();
        response = cm.doPATCH(ConfigHelper.returnPropVal("config", "booking") + "/" + bookingId, headersUtils.getHeaderMap(), body);
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkSuccessResponse(response, HttpStatus.SC_OK);
        bs.validatePartialUpdateBookingResponse(response, csv.getSpecificCSVData(data, 1, 3), csv.getSpecificCSVData(data, 3, 2));
    }

    @Test(priority = 4)
    public void verifyDeleteBooking() {
        extentLog.info(customReport(methodName + ": This test verifies delete booking"));
        headersUtils.setHeaders(TestConstants.COOKIE_HEADER, "token=" + bookingReq.getToken());
        response = cm.doDELETE(ConfigHelper.returnPropVal("config", "booking") + "/" + bookingId, headersUtils.getHeaderMap());
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkSuccessResponse(response, HttpStatus.SC_CREATED);
    }

    @Test(priority = 5)
    public void verifyBookingIsDeleted() {
        extentLog.info(customReport(methodName + ": This test verifies booking is deleted or not"));
        response = cm.doGET(ConfigHelper.returnPropVal("config", "booking") + "/" + bookingId);
        extentLog.log(Status.INFO, customReport("Click here to the view the Request", requestWriter.toString()));
        extentLog.log(Status.INFO, customReport("Click here to the view the Response",
                response.asString()));
        checkErrorResponse(response, HttpStatus.SC_NOT_FOUND);
    }
}