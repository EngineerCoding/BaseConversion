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

import java.lang.reflect.Field;

public class BitContainerMock extends BitContainer {

	private int number;
	private String binary;

	public BitContainerMock(final int number) {
		super(0);
		this.number = number;
		binary = reverseString(Integer.toBinaryString(number));
		reflectBitsField(this, getBits());
	}

	private BitContainerMock(final String submock) {
		super(0);
		this.number = Integer.valueOf(submock, 2);
		binary = submock;
		reflectBitsField(this, getBits());
	}

	private boolean[] getBits() {
		final boolean[] bits = new boolean[bits()];
		for (int i = 0; i < bits.length; i++)
			bits[i] = binary.charAt(i) == '1';
		return bits;
	}

	@Override
	public BitContainer fillContainer (final int number) {
		String binary = reverseString(Integer.toBinaryString(number));
		if (binary.length() > bits()) {
			binary = binary.substring(0, bits());
		} else if (binary.length() < bits()) {
			while (binary.length() != bits())
				binary += '0';
		}
		System.out.println(this.binary);
		this.binary = binary;

		reflectBitsField(this, getBits());
		return this;
	}

	@Override
	public BitContainer getSubContainer (int start, int amount) {
		return new BitContainerMock(reverseString(binary.substring(start, start + amount)));
	}

	@Override
	public BitContainer join (final BitContainer container) {
		if (container instanceof BitContainerMock) {
			final BitContainerMock mock = (BitContainerMock) container;
			return new BitContainerMock(reverseString(binary + mock.binary));
		}
		throw new RuntimeException();
	}

	@Override
	public int bits () {
		return binary.length();
	}

	@Override
	public int value () {
		return number;
	}

	public static String reverseString(final String r) {
		return new StringBuilder(r.length()).append(r).reverse().toString();
	}

	// Reflection methods

	protected static void reflectBitsField(final BitContainer container, final boolean[] new_bits) {
		try {
			final Field field = BitContainer.class.getDeclaredField("bits");
			field.setAccessible(true);
			field.set(container, new_bits);
			field.setAccessible(false);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	protected static boolean[] getBitsField(final BitContainer container) {
		try {
			final Field bits = BitContainer.class.getDeclaredField("bits");
			bits.setAccessible(true);
			return (boolean[]) bits.get(container);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
