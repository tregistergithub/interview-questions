package com.tbla.tbz.q3;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tbla.tbz.q3.StringsTransformer.StringFunction;

public class StringsTransformerTest {

	@Test
	public void test() throws InterruptedException {
		List<String> startingData = new ArrayList<>();
		startingData.add("aaa");
		startingData.add("bbb");
		startingData.add("ccc");
		startingData.add("ddd");
		StringsTransformer stringsTransformer = new StringsTransformer(startingData);
		
		List<StringFunction> functions = new ArrayList<>();
		
		StringFunction f1 = new StringFunction() {
			@Override
			public String transform(String str) {
				return str + "1";
			}
		};

		StringFunction f2 = new StringFunction() {
			@Override
			public String transform(String str) {
				return str + "2";
			}
		};

		functions.add(f1);
		functions.add(f2);
		
		System.out.println(stringsTransformer.transform(functions));
	}

}
