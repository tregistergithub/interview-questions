package com.tal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BalancedArraySumTest {

	@Test
	public void testBalancedElementFound() {
		BalancedArraySum b = new BalancedArraySum();
		assertThat(b.getBalancedIndex(new int[] { 1, 1, 1, 3, 6, 1, 2, 3 }), equalTo(4));
		assertThat(b.getBalancedIndex(new int[] { 1, 1, 1}), equalTo(1));
		assertThat(b.getBalancedIndex(new int[] { 1, 1 }), equalTo(BalancedArraySum.DID_NOT_FIND_BALANCED_INDEX));
		assertThat(b.getBalancedIndex(new int[] { 2, 1, 1, 1 }), equalTo(1));
		assertThat(b.getBalancedIndex(new int[] { }), equalTo(BalancedArraySum.DID_NOT_FIND_BALANCED_INDEX));
	}
	
	@Test
	public void testBalancedElementNotFound() {
		BalancedArraySum b = new BalancedArraySum();
		assertThat(b.getBalancedIndex(new int[] { 1, 1, 3, 6, 1, 2, 3 }), equalTo(BalancedArraySum.DID_NOT_FIND_BALANCED_INDEX));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException() {
		new BalancedArraySum().getBalancedIndex(null);
	}
}
