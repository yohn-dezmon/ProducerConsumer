package com.jdes.prodconsum.models;

//import java.util.Queue;
// IDK if I really need this to be a class... but ok. 

public class Burger {
	
	private boolean cooked = false;
	private boolean delivered = false;
	private boolean inBuffer = false;
	public final int burgerId;
	
	
	public Burger (int burgerId) {
		this.burgerId = burgerId;
	}
	
	public boolean getCooked() {
		return cooked;
	}
	
	public synchronized void setCooked(boolean cooked) {
		this.cooked = cooked;
	}
	
	public boolean getDelivered() {
		return delivered;
	}
	
	public synchronized void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
	
	public boolean getInBuffer() {
		return inBuffer;
	}
	
	public synchronized void setInBuffer(boolean inBuffer) {
		this.inBuffer = inBuffer;
	}
	

}

