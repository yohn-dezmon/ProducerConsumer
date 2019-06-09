package com.jdes.prodconsum.models;

import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;

import java.util.List;



public class Consumer {
	// since the Buffer is a shared resource, I'm going to make this a synchronized method
	public void getBurgerFromBuffer(BlockingQueue<AtomicBurger> buffer, List<AtomicBurger> cookedBurgers) {
		
		 
			// .take() will wait until the list is not empty unless otherwise interrupted.
			try { AtomicBurger readyBurger = buffer.take();
			System.out.println("Taking burger["+readyBurger.burgerId+"] from the buffer");
				cookedBurgers.add(readyBurger);
			} catch (InterruptedException e) {
				System.out.println("Error extracting burger from queue: "+e);
			}
			
		}
		
	// the burger is also a shared resource, therefore I will make this synchronized as well
	public void deliverBurger(AtomicBurger burger) {
		if (burger.getDelivered() == false) {
		System.out.println("Delivering burger["+burger.burgerId+"]...");
		// I'm going to run this in a thread and either do a join
		// or sleep to give it a time...
		burger.setDelivered();
		}
	}

}
