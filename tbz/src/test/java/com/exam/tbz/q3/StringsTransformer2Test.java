package com.exam.tbz.q3;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringsTransformer2Test {

	private static final int STRING_COUNT_MULT_BY_4 = 50;
	private static final int FUNCTION_COUNT = 50;

	@Test
	public void test() throws InterruptedException {
		List<String> startingData = new ArrayList<>();
		for (int i = 0; i < STRING_COUNT_MULT_BY_4; i++) {
			startingData.add("aaa");
			startingData.add("bbb");
			startingData.add("ccc");
			startingData.add("ddd");
		}

		List<StringsTransformer2.StringFunction> funcs = new ArrayList<>();

		for (int j = 0; j < FUNCTION_COUNT; j++) {
			final int k = j;
			funcs.add(new StringsTransformer2.StringFunction() {
				@Override
				public String transform(String str) {
					return str + String.valueOf(k % 10);
				}
			});
		}

		StringsTransformer2 stringsTransformer2 = new StringsTransformer2(startingData);
		String[] arrRes = stringsTransformer2.transform(funcs);
		Long totalLength = Arrays.stream(arrRes).mapToLong(String::length).sum(); 
		assertEquals("totalLength", Long.valueOf(STRING_COUNT_MULT_BY_4 * 4 * (FUNCTION_COUNT + startingData.get(0).length())), totalLength);
	}
}
