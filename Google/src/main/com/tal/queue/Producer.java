package com.tal.queue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
	private final BlockingQueue<String> queue;

	Producer(BlockingQueue<String> q) {
		queue = q;
	}

	public void run() {
		try {
				
			for (int i = 0; i < 10; i++) {
				String produce = produce();
				queue.put(produce + i);
				System.out.println("produce" + i);
			}
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	String produce() {
		return "a";
	}


}
