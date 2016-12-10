package com.tal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MultiThreadedCalibration {
	private static final int NUM_OF_THREADS = 2;

	public static void main(String[] args) {
		usingCountDownLatch();
	}

	private static void test1to20() {
		Thread[] tl = new Thread[100];
		Runnable runnable = () -> {
			doIt();
		};

		for (int numOfThreads = 0; numOfThreads < 20; numOfThreads++) {

			long t1 = System.currentTimeMillis();

			for (int i = 0; i < numOfThreads; i++) {
				tl[i] = new Thread(runnable);
				tl[i].start();
			}

			for (int i = 0; i < numOfThreads; i++) {
				try {
					tl[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			long t2 = System.currentTimeMillis();
			System.out.println(numOfThreads + ":" + (t2 - t1));

		}
	}

	private static void doIt() {
		new PrimeCalculator(200000).run();
	}

	private static void scheduler2() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

		int initialDelay = 0;
		int period = 1;
		executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
	}

	private static void callablFutureExecutor() {
		int numOfThreads = 10;
		long t1 = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);

		List<Future<Void>> futures = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			futures.add(executor.submit(new Callable<Void>() {
				public Void call() {
					doIt();
					return null;
				}
			}));
		}

		int doneCount = 0;
		for (Future<Void> f : futures) {
			try {
				f.get();
				System.out.println(++doneCount);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println(numOfThreads + ":" + (t2 - t1));
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
		} catch (InterruptedException e) {
			System.err.println(threadName + ": tasks interrupted");
		} finally {
			if (!executor.isTerminated()) {
				System.err.println(threadName + ": cancel non-finished tasks");
			}
			executor.shutdownNow();
			System.out.println(threadName + ": shutdown finished");
		}
	}

	private static void t2() {
		Runnable runnable = () -> {
			try {
				String name = Thread.currentThread().getName();
				System.out.println("Foo " + name);
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Bar " + name);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		for (int i = 0; i < 100; i++) {
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

		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(task);
			thread.start();
		}

		System.out.println("Done!");
	}

	public static class PrimeCalculator {
		public long longMax; // The maximum integer range to seach
		public long primeMax; // The maximum prime found
		public long primeCount; // The number of primes found
		public long jobCount; // The number of jobs done so far

		public PrimeCalculator(long max) {
			longMax = max;
			primeCount = 0;
			primeMax = 0;
		}

		public void run() {
			jobCount = 0;
			long count = 0;
			long max = 0;
			for (long i = 3; i <= longMax; i++) {
				boolean isPrime = true;
				for (long j = 2; j <= i / 2 && isPrime; j++) {
					isPrime = i % j > 0;
				}
				if (isPrime) {
					count++;
					max = i;
				}
			}
			primeCount = count;
			primeMax = max;
			jobCount++;
		}
	}
	
	public static void usingCountDownLatch() {
		long t1 = System.currentTimeMillis();
		int NUMBER_OF_TASKS = 20;
	    CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_TASKS);

        int NUMBER_OF_THREADS = 4;
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            Worker worker = new Worker(countDownLatch);
            executorService.execute(worker);
        }

        
        try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();

        executorService.shutdown();
		System.out.println(NUMBER_OF_THREADS + ":" + (t2 - t1));
	}
	
	public static class Worker implements Runnable {
		 
	    private final CountDownLatch countDownLatch;
	 
	    public Worker(CountDownLatch countDownLatch) {
	        this.countDownLatch = countDownLatch;
	    }
	 
	    @Override
	    public void run() {
	        try {
	        	doIt();
	        	System.out.println("done " + Thread.currentThread().getName());
	            
	        } finally {
	        	countDownLatch.countDown();
	        	System.out.println("left " + countDownLatch.getCount());
	        	
			}
	    }
	}	
}
