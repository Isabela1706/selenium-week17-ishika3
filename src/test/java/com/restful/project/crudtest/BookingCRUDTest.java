package com.restful.project.crudtest;


import com.restful.project.constant.EndPoints;
import com.restful.project.model.BookingPojo;
import com.restful.project.testbase.TestBase;
import com.restful.project.utils.TestUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BookingCRUDTest extends TestBase {

    static String id;

    @Test(priority = 0)
    public void verifyBookingCreatedSuccessfully() {
        String fName = "Prem" + TestUtils.getRandomValue();
        String lName = "Patel" + TestUtils.getRandomValue();
        int totalPrice = 1000;
        boolean depositPaid = true;
        HashMap<String, String> bookingDates = new HashMap<>();
        String checkIn = "2020-05-03";
        String checkOut = "2020-05-10";
        bookingDates.put("checkin", checkIn);
        bookingDates.put("checkout", checkOut);
        String additionalNeeds = "Lunch";

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(fName);
        bookingPojo.setLastname(lName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(depositPaid);
        bookingPojo.setBookingdates(bookingDates);
        bookingPojo.setAdditionalneeds(additionalNeeds);

        Response response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post(EndPoints.POST_BOOKING);

        id = response.jsonPath().getString("bookingid");

        response.prettyPrint();
        response.then().log().ifValidationFails().statusCode(200);
    }

    @Test(priority = 1)
    public void verifyBookingReadSuccessfully() {
        Response response = given()
                .pathParam("id",id)
                .when()
                .get(EndPoints.GET_ALL_BOOKINGS);
        response.prettyPrint();
        response.then().statusCode(200);
    }


    @Test(priority = 2)
    public void verifyBookingUpdateSuccessfully(){
        String fName = "Prem" + "Updated";
        String lName = "Patel" + "Updated";
        int totalPrice = 1000;
        boolean depositPaid = true;

        HashMap<String, String> bookingDates = new HashMap<>();
        String checkIn = "2020-05-03";
        String checkOut = "2020-05-10";
        bookingDates.put("checkin", checkIn);
        bookingDates.put("checkout", checkOut);
        String additionalNeeds = "breakfast,dinner";

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(fName);
        bookingPojo.setLastname(lName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(depositPaid);
        bookingPojo.setBookingdates(bookingDates);
        bookingPojo.setAdditionalneeds(additionalNeeds);

        Response response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .header("Connection", "keep-alive")
                        .pathParam("id",id)
                        .body(bookingPojo)
                        .when()
                        .put(EndPoints.UPDATE_BOOKING_BY_ID);
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test(priority = 3)
    public void verifyBookingDeleteSuccessfully() {
        Response response = given().log().all()
                .pathParam("id", id)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete(EndPoints.DELETE_Booking_BY_ID);
        response.then().statusCode(201);
        response.prettyPrint();
    }


}
