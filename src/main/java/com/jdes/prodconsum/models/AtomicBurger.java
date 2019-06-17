package com.jdes.prodconsum.models;



import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBurger {
	
	// making the class variables atomic means that once one of the threads
	// has access to the Atomic variable, the method involving that variable
	// must be complete before another thread can access it.
	// Atomic variables differ from synchronized variables because the synchronization
	// is achieved through compare-and-swap as opposed to locking
	private AtomicBoolean cooked = new AtomicBoolean(false);
	private AtomicBoolean delivered = new AtomicBoolean(false);
	private AtomicBoolean inBuffer = new AtomicBoolean(false);
	public final int burgerId;
	
	
	public AtomicBurger (int burgerId) {
		this.burgerId = burgerId;
	}
	
	public boolean getCooked() {
		return cooked.get();
	}
	
	public void setCooked() {
		cooked.compareAndSet(false, true);
	}
	
	public boolean getDelivered() {
		return delivered.get();
	}
	
	public void setDelivered() {
		delivered.compareAndSet(false, true);
	}
	
	public boolean getInBuffer() {
		return inBuffer.get();
	}
	
	public void setInBuffer() {
		inBuffer.compareAndSet(false, true);
	}
	

}

