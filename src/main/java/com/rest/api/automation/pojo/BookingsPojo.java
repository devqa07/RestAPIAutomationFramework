package com.rest.api.automation.pojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookingsPojo {
	int bookingId;
	String firstName;
	int totalPrice;
}