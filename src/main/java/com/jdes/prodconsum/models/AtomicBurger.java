package com.jdes.prodconsum.models;



import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBurger {
	
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
	
	public synchronized void setCooked() {
		cooked.compareAndSet(false, true);
	}
	
	public boolean getDelivered() {
		return delivered.get();
	}
	
	public synchronized void setDelivered() {
		delivered.compareAndSet(false, true);
	}
	
	public boolean getInBuffer() {
		return inBuffer.get();
	}
	
	public synchronized void setInBuffer() {
		inBuffer.compareAndSet(false, true);
	}
	

}

