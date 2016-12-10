package com.tbla.tbz.q3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.tbla.tbz.q3.StringsTransformer2.StringFunction;

public class StringsTransformer2Test {

	private static final int STRING_COUNT_MULT_BY_4 = 50000;
	private static final int FUNCTION_COUNT = 500;

	@Test
	public void test() throws InterruptedException {
		List<String> startingData = new ArrayList<>();
		for (int i = 0; i < STRING_COUNT_MULT_BY_4; i++) {
			startingData.add("aaa");
			startingData.add("bbb");
			startingData.add("ccc");
			startingData.add("ddd");
		}

		List<StringFunction> funcs = new ArrayList<>();

		for (int j = 0; j < FUNCTION_COUNT; j++) {
			final int k = j;
			funcs.add(new StringFunction() {
				@Override
				public String transform(String str) {
					return str + k;
				}
			});
		}

		StringsTransformer2 stringsTransformer2 = new StringsTransformer2(startingData);
		String[] arrRes = stringsTransformer2.transform(funcs);
//		String strRes = Arrays.toString(arrRes);
//		assertEquals("[aaa01234, bbb01234, ccc01234, ddd01234, aaa01234, bbb01234, ccc01234, ddd01234]", strRes);
	}

}
