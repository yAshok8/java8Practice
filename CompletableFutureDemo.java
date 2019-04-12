package com.practice.citiustech.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;


public class CompletableFutureDemo {

	public static void main(String[] args) throws Exception{
//		simpleCompletableFutureDemo();
//		runAsyncCompletableFuture();
//		supplyAsyncCompletableFuture();
//		callbackFutureDemo();
//		callbackChain();
//		thenAcceptDemo();
//		thenCombine();
		thenCombineNested();
	}
	
	private static void simpleCompletableFutureDemo() throws Exception{
		CompletableFuture<String> cf = new CompletableFuture<String>();
		new Thread(()->{
			try {Thread.sleep(2000);} catch (Exception e) { e.printStackTrace(); }
			cf.complete("I am done");
		}).start();
		System.out.println("main is doing something in parallel");
		System.out.println(cf.get());		
	}
	
	//runs a task asynchronously without sending the result
	private static void runAsyncCompletableFuture()throws Exception{
		CompletableFuture<Void> cf = CompletableFuture.runAsync(()->{
			try {Thread.sleep(2000);} catch (Exception e) { e.printStackTrace(); }
			System.out.println("I'm runnin in separate thread. "+Thread.currentThread().getName());
		});
		System.out.println("running in current thread : "+Thread.currentThread().getName());
		cf.get();
	}
	
	//runs a task asynchronously with a value returned
	private static void supplyAsyncCompletableFuture()throws Exception{
		CompletableFuture<String> cf = CompletableFuture.supplyAsync(()->{
			try {Thread.sleep(2000);} catch (Exception e) { e.printStackTrace(); }
			return "supplied from another thread";
		});
		System.out.println(cf.get());
	}
	
	//attaches the callback function to current CompletableFuture
	//thenApply returns CompletableFuture<T>. If you dowanna return anything and simply run a task then use thenRun()
	private static void callbackFutureDemo()throws Exception{
		CompletableFuture<String> futureNameObj = CompletableFuture.supplyAsync(()->{
			try {Thread.sleep(2000);} catch (Exception e) { e.printStackTrace(); }
			return "Ashok";
		});
		
		CompletableFuture<String> nameCallback = futureNameObj.thenApply(name -> "Hello " + name);
		
		System.out.println(futureNameObj.get());
		System.out.println(nameCallback.get());
	}
	
	//callback chain
	private static void callbackChain()throws Exception{
		CompletableFuture<String> chain = CompletableFuture.supplyAsync(()->{
			try {Thread.sleep(2000);} catch (Exception e) { e.printStackTrace(); }
			return "Hello";
		}).thenApply(greet -> greet + " Ashok")
		  .thenApply(fullGreet -> fullGreet + "! Welcome to CompletableFuture Chain");
		
		System.out.println(chain.get());
	}
	
	/*
	 * If you don’t want to return anything from your callback function and just want 
	 * to run some piece of code after the completion of the Future, then you can use 
	 * thenAccept() and thenRun() methods. These methods are consumers and are often 
	 * used as the last callback in the callback chain.
	 * */
	private static void thenAcceptDemo(){
		//thenAccept takes Consumer<T>
		CompletableFuture.supplyAsync(()->{
			return "returning from thenAcceptDemo() supplyAsync";
		}).thenAccept(System.out::println);
		
	}
	
	
	private static void thenCombine() throws InterruptedException, ExecutionException{
		CompletableFuture<String> myName = CompletableFuture.supplyAsync(()->{
			System.out.println("calculating name");
			try {Thread.sleep(3000);} catch (Exception e) { e.printStackTrace(); }
			System.out.println("name ready");
			return "Ashok";
		});
		
		CompletableFuture<String> mySurname = CompletableFuture.supplyAsync(()->{
			System.out.println("calculating surname");
			try {Thread.sleep(2000);} catch (Exception e) { e.printStackTrace(); }
			System.out.println("surname ready");
			return "Yadav";
		});
		
		System.out.println("parallel execution");
		
		BiFunction<String, String, String> bf = (name,surname)-> {
			System.out.println("Combine BiFunction started");
			return name +" "+ surname;
		};
		
		CompletableFuture<String> myFullname = myName.thenCombine(mySurname, bf);
		System.out.println(myFullname.get());
	}
	
	public static void thenCombineNested()throws Exception{
		CompletableFuture<String> name = CompletableFuture.supplyAsync(()->{
			System.out.println("calculating name");
//			System.out.println(1/0);
			try {Thread.sleep(3000);} catch (Exception e) { e.printStackTrace(); }
			System.out.println("name ready");
			return "Ashok";
		}).thenCombine(CompletableFuture.supplyAsync(()->{
			System.out.println("calculating surname");
			try {Thread.sleep(1000);} catch (Exception e) { e.printStackTrace(); }
			System.out.println("surname ready");
			return "Yadav";
		}), (n, s) -> n+" "+s)
		.thenCombine(CompletableFuture.supplyAsync(()->{
			System.out.println("Greeting text ready");
			return "Hello";
		}) , (fullname, greet) -> greet + " " + fullname);
		System.out.println(name.join());
	}
}
   
