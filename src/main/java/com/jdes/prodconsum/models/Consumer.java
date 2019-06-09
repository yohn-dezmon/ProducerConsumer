package com.jdes.prodconsum.models;

import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;

import java.util.List;



public class Consumer {
	
	public void getBurgerFromBuffer(BlockingQueue<AtomicBurger> buffer, List<AtomicBurger> cookedBurgers) {
		
		 
			// .take() will wait until the list is not empty unless otherwise interrupted.
			try { AtomicBurger readyBurger = buffer.take();
			System.out.println("Taking burger["+readyBurger.burgerId+"] from the buffer");
				cookedBurgers.add(readyBurger);
			} catch (InterruptedException e) {
				System.out.println("Error extracting burger from queue: "+e);
			}
			
		}
		
	
	public void deliverBurger(AtomicBurger burger) {
		if (burger.getDelivered() == false) {
		System.out.println("Delivering burger["+burger.burgerId+"]...");
		
		burger.setDelivered();
		}
	}

}
