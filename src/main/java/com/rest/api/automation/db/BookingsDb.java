package com.rest.api.automation.db;

import com.rest.api.automation.pojo.BookingsPojo;
import com.rest.api.automation.utils.RestAPIUtils;
import com.rest.api.automation.utils.DbUtils;
import com.rest.api.automation.utils.TestConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookingsDb {
    private static String db_Name, query;
    BookingsPojo pojo;
    private static String env;
    private static ResultSet rs;

    public BookingsDb() {
        db_Name = TestConstants.BOOKINGS_DB;
        pojo = new BookingsPojo();
        env = RestAPIUtils.getDbEnvDetails();
    }

    public BookingsPojo validateBookingDetails(int bookingId) {
        Connection con = DbUtils.getDBConnection(RestAPIUtils.getDbName(db_Name, env));
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            validateBookingsTable(bookingId);
            rs = stmt.executeQuery(query);
            rs.next();
            pojo.setBookingId(rs.getInt("bookingId"));
            pojo.setFirstName(rs.getString("firstName"));
            pojo.setTotalPrice(rs.getInt("totalPrice"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pojo;
    }

    private void validateBookingsTable(int bookingId) {

        if (env.equalsIgnoreCase(TestConstants.ENV_QA) || env.equalsIgnoreCase(TestConstants.ENV_DEV)) {
            query = "SELECT * FROM booking_details where id='" + bookingId + "';";
        }
    }
}