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
	// 5 indicates the size of the BlockingQueue I'm instantiating
	private static volatile BlockingQueue<AtomicBurger> buffer = new ArrayBlockingQueue<AtomicBurger>(5);	
	
    public static void main( String[] args ) throws InterruptedException
    {
    	// the lines similar to the one below are useful to see which thread is 
    	// being used by each task in real time.
//    	System.out.println("Inside : " + Thread.currentThread().getName());
    	// Sets up a blocking queue of max capacity 5! :D 
    	List<AtomicBurger> orders = new ArrayList<AtomicBurger>();
    	List<AtomicBurger> cookedBurgers = new ArrayList<AtomicBurger>();
    	
    	Producer producer = new Producer();
    	Consumer consumer = new Consumer();
    	
    	
    	AtomicBurger burger1 = new AtomicBurger(1);
    	AtomicBurger burger2 = new AtomicBurger(2);
    	AtomicBurger burger3 = new AtomicBurger(3);
    	AtomicBurger burger4 = new AtomicBurger(4);
    	AtomicBurger burger5 = new AtomicBurger(5);
    	AtomicBurger burger6 = new AtomicBurger(6);
    	
    	orders.add(burger1);
    	orders.add(burger2);
    	orders.add(burger3);
    	orders.add(burger4);
    	orders.add(burger5);
    	orders.add(burger6);
    	
    	
    	
    	List<AtomicBurger> synlist = Collections.synchronizedList(orders);
    	List<AtomicBurger> cookedSynlist = Collections.synchronizedList(cookedBurgers);
     
        
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        
        // Anonymous Runnable defined using a lambda 
        Runnable task1 = () -> {
        	
//        	System.out.println("Task 1 Inside : " + Thread.currentThread().getName());
        	for (AtomicBurger b:synlist) {
        		producer.cookBurger(b);
        		try { Thread.sleep(3000); 
        		} catch (InterruptedException ex) {
        			throw new IllegalStateException(ex);
        		}
        		
        	}
        	
        };
        
        Runnable task2 = () -> {
//        	System.out.println("Task 2 Inside : " + Thread.currentThread().getName());
        	for (AtomicBurger b:synlist) {
        		producer.placeBurgerInBuffer(b, buffer);
        		try { Thread.sleep(3000); 
        		} catch (InterruptedException ex) {
        			throw new IllegalStateException(ex);
        		}
        	}
        };
        
        Runnable task3 = () -> {
//        	System.out.println("Task 3 Inside : " + Thread.currentThread().getName());
        	for (AtomicBurger b: buffer) {
        	consumer.getBurgerFromBuffer(buffer, cookedSynlist); 
        	}
        };
        
        Runnable task4 = () -> {
//        	System.out.println("Task 4 Inside : " + Thread.currentThread().getName());
        	for (AtomicBurger b: cookedSynlist) {
        	consumer.deliverBurger(b);
        	try { Thread.sleep(3000); 
    		} catch (InterruptedException ex) {
    			throw new IllegalStateException(ex);
    		}
        	}
        	
        };
       
         
         Runnable task5 = () -> {
//        	 System.out.println("Task 5 Inside : " + Thread.currentThread().getName());
         for (AtomicBurger b:synlist) {
        	 if ((b.getCooked() == true) && (b.getDelivered() == true)) {
        		 System.out.println("Burger["+b.burgerId+"] has been delivered!");
        	 }
         }
         };
         
         
         
        
        	 
       // 0 is the initial delay, I only wanted to schedule this task once.
         executorService.schedule(task1, 0, TimeUnit.MINUTES);
      // 0 is initial delay, 4 means every four seconds
         executorService.scheduleAtFixedRate(task2, 0,4, TimeUnit.SECONDS);
        
         executorService.scheduleAtFixedRate(task3, 0,2, TimeUnit.SECONDS);
         
         executorService.scheduleAtFixedRate(task4, 0,8, TimeUnit.SECONDS);
         // I set the period between runs for this task as longer since it is just a 
         // check to see if the burgers were delivered
         executorService.scheduleAtFixedRate(task5, 0, 10, TimeUnit.SECONDS);
        
         // This waits one minute to shut down the executor service! 
         executorService.awaitTermination(1, TimeUnit.MINUTES);
         executorService.shutdown();
         
         
         
        
    }
}
