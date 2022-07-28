package com.example.bookingorder.controller;

import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.bookingorder.model.BookingOrder;
import com.example.bookingorder.service.BookingOrderService;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("Orders")
public class BookingOrderController {
	@Autowired
	private BookingOrderService bookingOrderService;

	@PostMapping("addOrder")
	public String saveOrder(@Valid @RequestBody BookingOrder order) {
	BookingOrder BookingOrder=	bookingOrderService.addOrder(order);
	if(BookingOrder!=null) {
	return "Booked ticket with id :  " + order.getId();}
	else {
		return "Please try for another train";
	}
    }
	@GetMapping("getOrder/{id}")
	public Optional<BookingOrder> getOrder(@PathVariable String id){
		return  bookingOrderService.getOrder(id);
	}
	 @DeleteMapping("del/{id}")
	 public String deleteOrder (@PathVariable String id) {
		 bookingOrderService.deleteOrder(id);
		return "Your Booking canceled with ticket id "+id;
		}
	}
