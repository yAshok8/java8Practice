package com.practice.citiustech.completablefuture;

import java.util.Random;

public class Shop {
	
	private String name;
	
	public Shop(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice(String productName){
		Random random = new Random();
		try{ Thread.sleep(1000); }catch(InterruptedException e){}
		return random.nextDouble()*100.0;
	}
}
