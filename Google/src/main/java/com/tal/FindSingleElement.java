package com.tal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FindSingleElement {
	public static void main(String[] args) {
		Integer[] a = Stream.of(2, 1, -2, 2, 7, 6, -2, 7, 1).toArray(Integer[]::new);

		sol1(Stream.of(a).toArray(Integer[]::new));
		sol2(Stream.of(a).toArray(Integer[]::new));
		sol3xor(Stream.of(a).toArray(Integer[]::new));
	}

	
	
	private static void sol3xor(Integer[] arr) {
		int XOR = arr[0];
		for(int i=1;i<arr.length;i++) { 
		     XOR = XOR ^ arr[i];
		}
		 
		System.out.println(XOR);		
	}



	private static void sol2(Integer[] a) {
		if (a.length == 1) {
			System.out.println(a[0]);
			return;
		}
		Arrays.sort(a);
		int candidate = a[0];
		int count = 1;
		for (int i = 1; i < a.length; i++) {
			int curr = a[i];
			if (count == 0) {
				candidate = curr;
				count++;
			} else if ( count == 1) {
				if (candidate != curr) {
					break;
				} else {
					count = 0;
				}
			} 
		}
		
		if (count == 1) {
			System.out.println(candidate);
		}
	}

	private static void sol1(Integer[] a) {
		Map<Integer, Integer> m = new HashMap<>();

		for (int i = 0; i < a.length; i++) {
			Integer val = a[i];
			Integer count = m.get(val);
			if (count == null) {
				m.put(val, 1);
			} else {
				if (count == 1) {
					m.remove(val);
				}
			}
		}

		System.out.println(m.keySet().toArray()[0]);
	}

}
