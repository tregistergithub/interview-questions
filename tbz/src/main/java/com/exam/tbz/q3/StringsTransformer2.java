package com.exam.tbz.q3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StringsTransformer2 {
	private List<String> inputList = new ArrayList<>();

	public StringsTransformer2(List<String> startingData) {
		this.inputList = startingData;
	}

	private void transformString(String s, int i, String[] result, List<StringFunction> funcs) {
		for (final StringFunction f : funcs) {
			s = f.transform(s);
		}
		result[i] = s;
	}

	public String[] transform(List<StringFunction> funcs) throws InterruptedException {

		ExecutorService executor = Executors.newWorkStealingPool();
		String[] res = new String[inputList.size()];

		for (int i = 0; i < inputList.size(); i++) {
			String s = inputList.get(i);
			final int j = i;
			executor.submit(() -> {
				transformString(s, j, res, funcs);
			});
		}

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);
		return res;
	}

	public static interface StringFunction {
		public String transform(String str);
	}
}
