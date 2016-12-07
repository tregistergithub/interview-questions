package com.tal;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MultiThreadedFutures {
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime() );

		int initialDelay = 0;
		int period = 1;
		executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
	}

	private static void callablFutureExecutor() {
		Callable<Integer> callable = () -> {
		    try {
		        TimeUnit.SECONDS.sleep(3);
		        return 123;
		    }
		    catch (InterruptedException e) {
		        throw new IllegalStateException("task interrupted", e);
		    }
		};

		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Integer> future = executor.submit(callable);

		System.out.println("future done? " + future.isDone());

		String threadName = Thread.currentThread().getName();
		Integer result = null;
		try {
			result = future.get();
		} catch (InterruptedException e) {
		    System.err.println(threadName +  ": tasks interrupted");
		} catch (ExecutionException e) {
		    System.err.println(threadName +  ": Exception");
		}

		System.out.println("future done? " + future.isDone());
		System.out.print("result: " + result);
	}

	private static void exec1() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
		    String threadName = Thread.currentThread().getName();
	        try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			    System.err.println(threadName + ": tasks interrupted");
			}
		    System.out.println("Hello " + threadName);
		});

		String threadName = Thread.currentThread().getName();
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(1, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println(threadName +  ": tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println(threadName +  ": cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println(threadName +  ": shutdown finished");
		}
	}

	private static void t2() {
		Runnable runnable = () -> {
		    try {
		        String name = Thread.currentThread().getName();
		        System.out.println("Foo " + name);
		        TimeUnit.SECONDS.sleep(1);
		        System.out.println("Bar " + name);
		    }
		    catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		};

		for (int i = 0; i< 100; i++) {
			Thread thread = new Thread(runnable);
			thread.start();
		}
		
		System.out.println("Done!");
	}

	private static void t1() {
		Runnable task = () -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("Hello from " + threadName);
		};

		task.run();

		for (int i = 0; i< 100; i++) {
			Thread thread = new Thread(task);
			thread.start();	
		}

		System.out.println("Done!");
	}
}
