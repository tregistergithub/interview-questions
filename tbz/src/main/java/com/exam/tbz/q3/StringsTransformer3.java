package com.exam.tbz.q3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StringsTransformer3 {
	private List<String> inputList = new ArrayList<String>();

	public StringsTransformer3(List<String> startingData) { 
		this.inputList = startingData;
	}

	public String[] transform(List<StringFunction> funcs) throws InterruptedException {
		
		int shardCount = Runtime.getRuntime().availableProcessors() + 1;
		int size = inputList.size();
		
		int shardSize = size / shardCount; 
		
		ExecutorService executor = Executors.newFixedThreadPool(shardCount);
		String[] res = new String[inputList.size()];

		for (int i = 0; i < shardCount; i++) {
			int startIndex = i * shardSize;
			int endIndex = (i < shardCount - 1) ? startIndex + shardSize - 1 : size - 1;
			executor.submit(() -> {
				transformShard(startIndex, endIndex, res, funcs);
			});
		}

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);
		return res;
	}

	private void transformShard(int startIndex, int endIndex, String[] res, List<StringFunction> funcs) {
		for (int i = startIndex; i < endIndex + 1; i++) {
			transformString(inputList.get(i), i, res, funcs);
		}
	}

	private void transformString(String s, int i, String[] result, List<StringFunction> funcs) {
		for (final StringFunction f : funcs) {
			s = f.transform(s);
		}
		result[i] = s;
	}
	
	public static interface StringFunction {
		public String transform(String str);
	}
}