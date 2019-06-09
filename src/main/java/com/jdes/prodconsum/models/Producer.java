package com.jdes.prodconsum.models;


import java.util.concurrent.BlockingQueue;


public class Producer {
	
	
	public synchronized void cookBurger (AtomicBurger burger) {
		
		if (burger.getCooked() == false) {
		System.out.println("Burger["+burger.burgerId+"] is cooking...");
		// I'm going to run this in a thread and either do a join
		// or sleep to give it a time...
		burger.setCooked();
		}
	}
	
	public synchronized void placeBurgerInBuffer(AtomicBurger burger, BlockingQueue<AtomicBurger> buffer) {
		
		boolean isBurgerCooked = burger.getCooked();
		// ahh I see why buffer.contains(burger) != true is flawed!
		// if the consumer takes it out of the buffer, then this becomes false again!
		
		if ((isBurgerCooked == true) && (buffer.contains(burger) != true) && (burger.getInBuffer() == false)) {
			try { buffer.put(burger);
				burger.setInBuffer();
			} catch (InterruptedException e) {
				System.out.println("Error putting burger into queue: "+e);
			}
			System.out.println("burger["+burger.burgerId+"] has been placed in the buffer");
		} else if (isBurgerCooked == false) {
			System.out.println("Waiting for burger["+burger.burgerId+"] to cook");
		}

		
			
		}
	}


