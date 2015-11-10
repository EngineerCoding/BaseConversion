/*
 * Copyright 2015 Wesley Ameling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ameling.base;

import org.junit.Assert;
import org.junit.Test;

public class BitContainerTest {

	private static final int TEST_NUMBER = 0b1010101101101;
	private static final int AMOUNT_OF_BITS = 8;

	private static final String EXPECTED_BITS_AFTER_FILL = "10110110";

	private static final int SUB_START = 4;
	private static final int SUB_AMOUNT = 3;
	private static final String EXPECTED_SUB = "011";

	@Test
	public void testFillContainer() {
		final BitContainer container = new BitContainer(AMOUNT_OF_BITS);
		testToString("Testing filling container: ", container.fillContainer(TEST_NUMBER), EXPECTED_BITS_AFTER_FILL);
	}

	@Test
	public void testSubContainer() {
		final BitContainer container = new BitContainer(AMOUNT_OF_BITS);
		BitContainerMock.reflectBitsField(container, getDefaultBitsArray());
		testToString("Testing retrieving subcontainer: ", container.getSubContainer(SUB_START, SUB_AMOUNT), EXPECTED_SUB);
	}

	@Test
	public void testJoin() {
		final BitContainer container = new BitContainer(AMOUNT_OF_BITS);
		final BitContainer container2 = new BitContainer(AMOUNT_OF_BITS);
		BitContainerMock.reflectBitsField(container, getDefaultBitsArray());
		BitContainerMock.reflectBitsField(container2, getDefaultBitsArray());
		testToString("Testing join: ", container.join(container2), EXPECTED_BITS_AFTER_FILL + EXPECTED_BITS_AFTER_FILL);
	}

	@Test
	public void testValue() {
		final BitContainer container = new BitContainer(AMOUNT_OF_BITS);
		BitContainerMock.reflectBitsField(container, getDefaultBitsArray());
		final int control = Integer.valueOf(BitContainerMock.reverseString(toBinaryString(container)), 2);
		Assert.assertEquals("Comparing values.", control, container.value());
	}

	private static String toBinaryString(final BitContainer container) {
		final boolean[] bits = BitContainerMock.getBitsField(container);
		final StringBuilder builder = new StringBuilder();
		for (final boolean bit : bits)
			builder.append(bit ? "1" : "0");
		return builder.toString();
	}

	private static void testToString(String message, final BitContainer container, final String expected) {
		final String binary = toBinaryString(container);
		message += String.format("%s -> %s", binary, expected);
		Assert.assertTrue(message, expected.equals(binary));
	}

	private boolean[] getDefaultBitsArray() {
		final boolean[] bits = new boolean[EXPECTED_BITS_AFTER_FILL.length()];
		for (int i = 0; i < bits.length; i++)
			bits[i] = EXPECTED_BITS_AFTER_FILL.charAt(i) == '1';
		return bits;
	}

}
