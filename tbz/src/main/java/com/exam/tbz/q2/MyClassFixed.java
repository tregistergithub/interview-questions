package com.exam.tbz.q2;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <b>Question 2</b>
 * <p/> 
 * The following class has several memory and runtime inefficiencies and bugs.
 * <p/> 
 * 
 * Locate and fix as many as you can.
 *
 */
public class MyClassFixed {
	private Date m_time; 
	private String m_name;
	private List<Long> m_numbers;
	private List<String> m_strings;
	private Set<Long> numbersDefesiveLazyCopy;
	private Set<String> stringsDefesiveLazyCopy;

	public MyClassFixed(Date time, String name, List<Long> numbers, List<String> strings) {
		if (time==null||name==null||numbers==null||strings==null) throw new IllegalArgumentException("All arguments must be non-null");
		m_time = new Date(time.getTime());
		m_name = name; 
		m_numbers = numbers;
		m_strings = strings;
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
		MyClassFixed other = (MyClassFixed) obj;
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

	@Override
	public String toString() {
		return "MyClassFixed [m_time=" + m_time + ", m_name=" + m_name + ", m_numbers=" + m_numbers + ", m_strings="
				+ m_strings + "]";
	}

	public void removeString(String str) {
		if (stringsDefesiveLazyCopy == null) {
			Set<String> tempStringsDefesiveLazyCopy = new HashSet<>(m_strings);
			synchronized (this) {
				if (stringsDefesiveLazyCopy == null) {
					stringsDefesiveLazyCopy = tempStringsDefesiveLazyCopy;
				}
			}
		}
		stringsDefesiveLazyCopy.remove(str);
	}

	public boolean containsNumber(long number) {
		if (numbersDefesiveLazyCopy == null) {
			Set<Long> tempNumbersDefesiveLazyCopy = new HashSet<>(m_numbers);
			synchronized (this) {
				if (numbersDefesiveLazyCopy == null) {
					numbersDefesiveLazyCopy = tempNumbersDefesiveLazyCopy;
				}
			}			
		}
		return numbersDefesiveLazyCopy.contains(number);
	}

	public boolean isHistoric() {
		return m_time.before(new DateService(){}.getDate());  // This can be wired by spring for testing 
	}
}