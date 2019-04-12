package com.practice.citiustech.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class FetchProductPrices {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Shop s1 = new Shop("shop 1");
		Shop s2 = new Shop("shop 2");
		Shop s3 = new Shop("shop 3");
		Shop s4 = new Shop("shop 4");
		Shop s5 = new Shop("shop 5");
		
		List<Shop> shops = Arrays.asList(s1, s2, s3, s4, s5);
		String productName = "Patanjali Innerwares";
		
		streamInternalWorking(shops);
		
/*		List<String> descs = calcPrices(productName, shops);
		
		for (String desc : descs) {
			System.out.println(desc);
		}*/
		
		
		List<String> greets = Arrays.asList("Hello", "World", "Hello", "Ashok");
		List<String> distinctWords = greets.stream().distinct().collect(Collectors.toList());
		for (String string : distinctWords) {
			System.out.println(string);
		}
	}
	
	/*
	 * CompletableFuture's join() is same as get(). The get() throws compile time exception which needs to be handled explicitly.
	 * The join() throws runtime exception.
	 * */
	private static List<String> calcPrices(String productName, List<Shop> shopList) throws InterruptedException, ExecutionException{
		List<CompletableFuture<String>> pricesFutures = 
				shopList.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getName()+" "+shop.getPrice(productName)))
				.collect(Collectors.toList());
//		System.out.println("blah");
		return pricesFutures.stream()
				.map((pf)->pf.join())
				.collect(Collectors.toList());
	}
	
	private static void streamInternalWorking(List<Shop> shopList){
		List<Integer> shopNames = shopList.stream()
				.filter(shop -> {
					System.out.println("Calc shop "+shop.getName());
					return shop.getName().contains("shop");
				})
				.map(shop -> {
					System.out.println(shop.getName());
					return shop.getName();
				}).map(String::length)
				.collect(Collectors.toList());
	}

}
 
