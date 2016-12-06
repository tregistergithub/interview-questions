package com.tal.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MultithreadedSharpen {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<String> q = new ArrayBlockingQueue<>(10);
		Producer p = new Producer(q);

		Thread tp = new Thread(p);
		tp.start();

		Consumer[] c = new Consumer[10];
		Thread[] tc = new Thread[c.length];

		for (int i = 0; i < c.length; i++) {
			c[i] = new Consumer(q);
			tc[i] = new Thread(c[i]);
			tc[i].start();
		}

		Thread.sleep(100L);
		while (!q.isEmpty()) {
			Thread.sleep(100L);
		}

		for (int i = 0; i < c.length; i++) {
			tc[i].interrupt();
		}
	}
}
