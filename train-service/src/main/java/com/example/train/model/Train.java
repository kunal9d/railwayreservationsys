package com.example.train.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Trains")
public class Train {
	
	@Id
	@NotBlank(message = "Train id should be mentioned")
	@Size(max=6, message="Id should not be more than 6")
	private String trainid;
	
	@NotBlank(message = "Train Name should be mentioned")
	private String trainName;
	
	@NotBlank(message = "Start Station should me mentioned")
	private String startStation;
	
	@NotBlank(message = "End Station should me mentioned")
	private String endStation;
	
//	@NotBlank(message = "Train seats should me mentioned")
	private int seats;
		
public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

public Train() {
	
}

public Train(String trainid, String trainName, String startStation, String endStation,int seats) {
	super();
	this.trainid = trainid;
	this.trainName = trainName;
	this.startStation = startStation;
	this.endStation = endStation;
	this.seats=seats;
}

public String getTrainid() {
	return trainid;
}

public void setTrainid(String trainid2) {
	this.trainid = trainid2;
}

public String getTrainName() {
	return trainName;
}

public void setTrainName(String trainName) {
	this.trainName = trainName;
}

public String getStartStation() {
	return startStation;
}

public void setStartStation(String startStation) {
	this.startStation = startStation;
}

public String getEndStation() {
	return endStation;
}

public void setEndStation(String endStation) {
	this.endStation = endStation;
}
	
}
