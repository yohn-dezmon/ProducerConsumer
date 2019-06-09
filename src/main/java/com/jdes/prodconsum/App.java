package com.jdes.prodconsum;

import java.util.Queue;
import java.util.LinkedList;
import com.jdes.prodconsum.models.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.TimeUnit;

import java.util.Collections;



public class App 
{
	// volatile prevents memory consistency errors
	// the queue in this case will always be read from the main memory
	private static volatile BlockingQueue<Burger> buffer = new ArrayBlockingQueue<Burger>(5);
	private static volatile Burger burger1 = new Burger(1);
	private static volatile Burger burger2 = new Burger(2);
	private static volatile Burger burger3 = new Burger(3);
	private static volatile Burger burger4 = new Burger(4);
	private static volatile List<Burger> orders = new ArrayList<Burger>();
	private static volatile List<Burger> cookedBurgers = new ArrayList<Burger>();
	private static volatile Producer producer = new Producer();
	private static volatile Consumer consumer = new Consumer();
	
	
    public static void main( String[] args ) throws InterruptedException
    {
    	System.out.println("Inside : " + Thread.currentThread().getName());
    	// Sets up a blocking queue of max capacity 5! :D 
    	
    	
    	orders.add(burger1);
    	orders.add(burger2);
    	orders.add(burger3);
    	orders.add(burger4);
    	
    	
    	List<Burger> synlist = Collections.synchronizedList(orders);
    	
    	List<Burger> cookedSynlist = Collections.synchronizedList(cookedBurgers);
    
    	
//        System.out.println("BurgerID: "+ burger1.burgerId);
    	
        
     
        
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        
        Runnable task1 = () -> {
        	System.out.println("Task 1 Inside : " + Thread.currentThread().getName());
        	for (Burger b:synlist) {
        		producer.cookBurger(b);
        		try { Thread.sleep(3000); 
        		} catch (InterruptedException ex) {
        			throw new IllegalStateException(ex);
        		}
        		
        	}
        	
        };
        
        Runnable task2 = () -> {
        	System.out.println("Task 2 Inside : " + Thread.currentThread().getName());
        	for (Burger b:synlist) {
        		producer.placeBurgerInBuffer(b, buffer);
        		try { Thread.sleep(3000); 
        		} catch (InterruptedException ex) {
        			throw new IllegalStateException(ex);
        		}
        	}
        };
        
        Runnable task3 = () -> {
        	System.out.println("Task 3 Inside : " + Thread.currentThread().getName());
        	for (Burger b: buffer) {
        	consumer.getBurgerFromBuffer(buffer, cookedSynlist); 
        	}
        };
        
        Runnable task4 = () -> {
        	System.out.println("Task 4 Inside : " + Thread.currentThread().getName());
        	for (Burger b: cookedSynlist) {
        	consumer.deliverBurger(b);
        	try { Thread.sleep(3000); 
    		} catch (InterruptedException ex) {
    			throw new IllegalStateException(ex);
    		}
        	}
        	
        };
       
         
         Runnable task5 = () -> {
        	 System.out.println("Task 5 Inside : " + Thread.currentThread().getName());
         for (Burger b:synlist) {
        	 if ((b.getCooked() == true) && (b.getDelivered() == true)) {
        		 System.out.println("Burger["+b.burgerId+"] has been delivered!");
        	 }
         }
         };
         
         
         
        
        	 
       // I really only want this to run once but this will do for now...
         executorService.schedule(task1, 0, TimeUnit.MINUTES);
      // 0 is initial delay, 2 means every two seconds.
         // I believe the initial delay here is set to 4 seconds...
         executorService.scheduleAtFixedRate(task2, 0,4, TimeUnit.SECONDS);
        
         
         executorService.scheduleAtFixedRate(task3, 0,2, TimeUnit.SECONDS);
         
         executorService.scheduleAtFixedRate(task4, 0,8, TimeUnit.SECONDS);
         
         
         executorService.scheduleAtFixedRate(task5, 0, 10, TimeUnit.SECONDS);
        
         // This waits one minute to shut down the executor service! 
         executorService.awaitTermination(1, TimeUnit.MINUTES);
         executorService.shutdown();
         
         
         
        
    }
}
