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

	public MyClass(Date time, String name, List<Long> numbers, List<String> strings) {
		m_time = time;
		m_name = name; 
		m_numbers = numbers;
		m_strings = strings;
	}

	// Missing @Override
	public boolean equals1(Object obj) { // Missing hashCode implementation
		// does not check nulls
		if (obj instanceof MyClass) {
			MyClass other = (MyClass) obj;
			return m_name.equals(other.m_name); 
		} 
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_name == null) ? 0 : m_name.hashCode());
		result = prime * result + ((m_numbers == null) ? 0 : m_numbers.hashCode());
		result = prime * result + ((m_strings == null) ? 0 : m_strings.hashCode());
		result = prime * result + ((m_time == null) ? 0 : m_time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyClass other = (MyClass) obj;
		if (m_name == null) {
			if (other.m_name != null)
				return false;
		} else if (!m_name.equals(other.m_name))
			return false;
		if (m_numbers == null) {
			if (other.m_numbers != null)
				return false;
		} else if (!m_numbers.equals(other.m_numbers))
			return false;
		if (m_strings == null) {
			if (other.m_strings != null)
				return false;
		} else if (!m_strings.equals(other.m_strings))
			return false;
		if (m_time == null) {
			if (other.m_time != null)
				return false;
		} else if (!m_time.equals(other.m_time))
			return false;
		return true;
	}

	// Missing @Override
	public String toString2() { // not includes all fields
		String out = m_name;
		for (long item : m_numbers) {
			out += " " + item; // inefficient - should use StringBuilder
		}
		return out;
	}

	@Override
	public String toString() {
		return "MyClass [m_name=" + m_name + ", m_numbers=" + m_numbers + "]";
	}

	public void removeString(String str) {
		for (int i = 0; i < m_strings.size(); i++) {
			if (m_strings.get(i).equals(str)) {
				m_strings.remove(i); // FAIL - operation not supported
			}
		}
	}

	public boolean containsNumber(long number) {
		for (long num : m_numbers) { // SORTED O(LOGN) / HASHMAP O(1)WILL BE BETTER THEN (O(N))
			if (num == number) {
				return true;
			}
		}
		return false;
	}

	public boolean isHistoric() {
		return m_time.before(new Date());
	}
}