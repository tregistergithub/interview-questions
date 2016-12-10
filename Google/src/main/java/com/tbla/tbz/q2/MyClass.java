package com.tbla.tbz.q2;

import java.util.Date;
import java.util.List;


/**
 * <b>Question 2</b>
 * <p/> 
 * The following class has several memory and runtime inefficiencies and bugs.
 * <p/> 
 * 
 * Locate and fix as many as you can.
 *
 */
public class MyClass {
	private Date m_time; 
	private String m_name;
	private List<Long> m_numbers;
	private List<String> m_strings;

	// Potential issue: not handling null values, it is a simple check 
	// which can avoid a NPE in the future, as the code expect the members to be not null
	// It is not a must, but since it is very cheap, I would do it here
	// The class is suppose to immutable (no setters) - so it can be done
	// Three of the fields are mutable m_time, m_numbers, m_strings and it might be changed from 
	// outside of the class. In order to be on the safe side, we can create a defensive copy
	// The down-side - By copying we double the memory, but we also control the data structure (collection)
	// to fir to the internal usage
	public MyClass(Date time, String name, List<Long> numbers, List<String> strings) {
		m_time = time;
		m_name = name;
		m_numbers = numbers;
		m_strings = strings;
	}

	// * Bug: equals is poorly implemented, not handling null correctly
	// I don't know why equals referring only to m_name, but in case
	// It will be used in a HashSet / HashMap it will be overridden
	// So I recommend to implement equals correctly with all of the state fields
	// In addition if equals is implemented, hashCode must be also implemented
	// They always go together (as recommended by the java guru John Bloch)
	// * Missing @Override annotation
	public boolean equals(Object obj) {
		if (obj instanceof MyClass) {
			MyClass other = (MyClass) obj;
			return m_name.equals(other.m_name); 
		} 
		return false;
	}

	// To string is usually implemented using a StringBuilder to avoid 
	// the creation of the strings in memory, plus, it does not contain 
	// all the state fields for some reason
	// * Missing @Override annotation
	public String toString() {
		String out = m_name;
		for (long item : m_numbers) {
			out += " " + item; // inefficient - should use StringBuilder
		}
		return out;
	}

	// This function perform remove without an iterator - this won't work on every implementation
	// In my test, it thrown an UnsupportedOperationException from AbstractString.remove()
	// In in some List implementation it will be in-efficient
	// This operation runs at O(N) - this can be improved by sorting O(LG(N)) or hashing - O(1)
	// The downside of this approach is more memory and sorting
	// The sorting can be lazy to avoid the action if this method is 
	// never called
	// Since the removal of the string is done on the original input list - this may break some 
	// other flows that use this list  	
	public void removeString(String str) {
		for (int i = 0; i < m_strings.size(); i++) {
			if (m_strings.get(i).equals(str)) {
				m_strings.remove(i);
			}
		}
	}

	public boolean containsNumber(long number) {
		for (long num : m_numbers) {
			if (num == number) {
				return true;
			}
		}
		return false;
	}

	// This code is OK, but it is not very testable
	// To make it testable, we can add a time service 
	// To tell this class what is "Now"
	public boolean isHistoric() {
		return m_time.before(new Date());
	}
}