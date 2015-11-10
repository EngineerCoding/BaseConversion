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


public class BaseTest {

	private static final int TESTING_NUMBER = 85;

	private static final String EXPECTED_BINARY = "0b" + Integer.toBinaryString(TESTING_NUMBER);
	private static final String EXPECTED_OCTAL = "0" + Integer.toOctalString(TESTING_NUMBER);
	private static final String EXPECTED_NONA = "104";
	private static final String EXPECTED_DEC = Integer.toString(85);
	private static final String EXPECTED_HEXA = "0x" + Integer.toHexString(TESTING_NUMBER);

	private final BitContainer TESTING_CONTAINER = new BitContainerMock(TESTING_NUMBER);

	// Correct testing

	@Test
	public void base2Test() {
		testBaseCorrectUse(new Base(2, "01".toCharArray(), "0b"), EXPECTED_BINARY);
	}

	@Test
	public void base8Test() {
		testBaseCorrectUse(new Base(8, "01234567".toCharArray(), "0"), EXPECTED_OCTAL);
	}

	@Test
	public void base16Test() {
		testBaseCorrectUse(new Base(16, "0123456789ABCDEF".toCharArray(), "0x"), EXPECTED_HEXA);
	}

	public void testBaseCorrectUse(final Base base, final String expected) {
		// Test the bitcontainer encode/decode
		final BaseNumber container = base.encodeFromBitContainer(TESTING_CONTAINER);
		Assert.assertTrue("BitContainer encoding (" + container.toString() + " -> " + expected + ")", container.toString().equals(expected));
		final BitContainer decoded = base.decodeToBitContainer(expected);
		Assert.assertTrue("BitContainer decoding (" + TESTING_CONTAINER.toString() + " -> " + decoded.toString() + ")",
				decoded.equals(TESTING_CONTAINER));
		// Test the regular encode/decode
		final BaseNumber number = base.encode(TESTING_NUMBER);
		Assert.assertTrue("Integer encoding", number.toString().equals(expected));
		Assert.assertEquals("Integer decoding", TESTING_NUMBER, base.decode(number.toString()));
	}

	// Incorrect testing

	@Test
	public void base9Test() {
		testBaseIncorrectUse(new Base(9, "012345678".toCharArray()), EXPECTED_NONA);
	}

	@Test
	public void base10Test() {
		testBaseIncorrectUse(new Base(10, "0123456789".toCharArray()), EXPECTED_DEC);
	}

	public void testBaseIncorrectUse(final Base base, final String expected) {
		final BaseNumber number = base.encode(TESTING_NUMBER);
		Assert.assertFalse(number.toString().equals(expected));
		Assert.assertNotEquals(TESTING_NUMBER, base.decode(number.toString()));
	}

}
