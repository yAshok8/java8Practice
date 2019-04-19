package com.practice.citiustech.chap6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.practice.citiustech.practice.Dish;

public class ReducingAndSummarizing {
	public static void main(String[] args) {
		Dish dish_1 = new Dish("Mutton Curry", false, 234.34);
		Dish dish_2 = new Dish("Aloo Paratha", true, 129.46);
		Dish dish_3 = new Dish("Egg Biryani", false, 564.78);
		Dish dish_4 = new Dish("Chicken Tikka Masala", false, 152.87);
		Dish dish_5 = new Dish("Sev Puri", true, 234.34);
		Dish dish_6 = new Dish("Schegwan Rice", true, 91.67);
		
		List<Dish> dishes = Arrays.asList(dish_1, dish_2, dish_3, dish_4, dish_5, dish_6);
		
		Optional<Dish> biggestCaloriDish = dishes.stream().max(Comparator.comparing(Dish::getCalories));
		System.out.println(biggestCaloriDish.isPresent() ? biggestCaloriDish.get().getCalories() : "");
	
	}
	
	//The collectors can be categorized in 3 separate sections 
		/* 	1. Reducing and Summarizing
			2. Grouping elements
			3. Partitioning elements */
	
	//finding maximum value in stream
	public void findMaxInStream(List<Dish> dishList){
		//syntax collect(max_or_min_collector(comparator_for_any_specific_property))
		Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
		Optional<Dish> maxCaloriDish = dishList.stream()
								.collect(Collectors.maxBy(dishCaloriesComparator));
		System.out.println(maxCaloriDish.isPresent() ? maxCaloriDish.get().getName() : "");
		
		//Summarization
		double totalCaloriesValue = dishList.stream().collect(Collectors.summingDouble(Dish::getCalories));
		System.out.println(totalCaloriesValue);
	}
}
 
