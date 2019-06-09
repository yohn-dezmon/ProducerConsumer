package com.jdes.prodconsum.models;


import java.util.concurrent.BlockingQueue;


public class Producer {
	
	
	public void cookBurger (AtomicBurger burger) {
		
		if (burger.getCooked() == false) {
		System.out.println("Burger["+burger.burgerId+"] is cooking...");
		
		burger.setCooked();
		}
	}
	
	public void placeBurgerInBuffer(AtomicBurger burger, BlockingQueue<AtomicBurger> buffer) {
		
		boolean isBurgerCooked = burger.getCooked();
		
		
		if ((isBurgerCooked == true) && (buffer.contains(burger) != true) && (burger.getInBuffer() == false)) {
			try { buffer.put(burger);
				burger.setInBuffer();
			} catch (InterruptedException e) {
				System.out.println("Error putting burger into queue: "+e);
			}
			System.out.println("Burger["+burger.burgerId+"] has been placed in the buffer");
		} else if (isBurgerCooked == false) {
			System.out.println("Waiting for burger["+burger.burgerId+"] to cook");
		}

		
			
		}
	}


