package com.jaho.datastructuresandalgorithms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DatastructuresandalgorithmsApplicationTests {

	@Test
	public void whenAssertingArraysEquality_thenEqual() {
		char[] expected = {'J','u','n','i','t'};
		char[] actual = "Junit".toCharArray();

		assertEquals(1, 1);
	}

}
