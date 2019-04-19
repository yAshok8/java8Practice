package com.practice.citiustech.chap6;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreamDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		checkSequentialSummation(995000000);
		checkParallelSummation(9000000);
		parallelSummationUsingNumericStreamMethods(); //lightning speed
	}

	private static void checkSequentialSummation(long n){
		System.out.println("Sequential Execution");
		long startTime = System.currentTimeMillis();
		long summationNo = Stream.iterate(1L, i->i+1).limit(n).reduce(0L, Long::sum);
		long endTime = System.currentTimeMillis();
		System.out.println("Time Gap : "+(endTime-startTime));
		System.out.println("Summation : "+summationNo+" ms");
	}
	
	/*
	 * Iterate operation is not suitable for parallelizing because
	 * 1. it generates boxed objects which have to be unboxed
	 * 2. difficult to divide into independent chunks to execute in parallel
	 * 3. Parallelizing iterate operation is hard to split into chunks that
	 * can be executed independently because input of function always 
	 * depends upon the result of the previous function.
	 * Therefore here parallel approach would take more time.
	 * */
	private static void checkParallelSummation(long n){
		System.out.println("Executing Parallely");
		long startTime = System.currentTimeMillis();
		long summationNo = Stream.iterate(1L, i->i+1).limit(n).parallel().reduce(0L, Long::sum);
		long endTime = System.currentTimeMillis();
		System.out.println("Time Gap : "+(endTime-startTime));
		System.out.println("Summation : "+summationNo+" ms");
	}
	
	/*
	 * 1. LongStream.rangeClosed works on primitive long numbers directly so there’s no boxing and unboxing overhead.
	 * 2. LongStream.rangeClosed produces ranges of numbers, which can be easily split into independent
	 * chunks. For example, the range 1–20 can be split into 1–5, 6–10, 11–15, and 16–20.
	 * */
	private static void parallelSummationUsingNumericStreamMethods(){
		System.out.println("Executing using Numeric Stream");
		long startTime = System.currentTimeMillis();
		long summationNo = LongStream.rangeClosed(0L, 995000000).reduce(0L, Long::sum);
		long endTime = System.currentTimeMillis();
		System.out.println("Time Gap : "+(endTime-startTime));
		System.out.println("Summation : "+summationNo+" ms");
	}
}
