package com.tal.queue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
		   private final BlockingQueue<String> queue;
		   Consumer(BlockingQueue<String> q) { queue = q; }
		   public void run() {
		     try {
		       while (true) { consume(queue.take()); }
		     } catch (InterruptedException ex) {
		    	 // Do nothing
		     }
		   }
		   void consume(String s) throws InterruptedException { 
			   System.out.println( " consume :" + s );
			   Thread.sleep(500L);
		   }
		   

}
