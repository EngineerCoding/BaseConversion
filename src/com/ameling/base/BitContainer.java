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

import java.util.Arrays;

/**
 * This class is internally used to create a binary series, and to append that with a binary series. It does it by creating an array of booleans because one boolean represents a bit,
 * on (true) or off (false). In the JVM a boolean is stored just like that and thus it is the most ideal value to use3 for this
 */
public class BitContainer {

	/**
	 * The array with all the bits
	 */
	private final boolean[] bits;

	/**
	 * Creates a new BitContainer with this amount of bits
	 * @param amount_bits The amount of bits this container should have
	 */
	public BitContainer(final int amount_bits) {
		bits = new boolean[amount_bits];
	}

	/**
	 * Creates a new BitContainer with this amount of bits and sets the bits accordingly to the number
	 * @param amount_bits The amount of bits this container should have
	 * @param filler The number this container should represent (at least the bits that this container represents)
	 */
	public BitContainer(final int amount_bits, final int filler) {
		this(amount_bits);
		fillContainer(filler);
	}

	/**
	 * Checks which bit is on the given number, it does this for the all the bits this container represents
	 * @param number The number this container should represent (at least the bits that this container represents)
	 * @return The filled {@link BitContainer}, which is the same instance. This allows chaining of methods.
	 */
	public BitContainer fillContainer(final int number) {
		for (int i = 0; i < bits.length; i++)
			bits[i] = (number & (1 << i)) != 0;
		return this;
	}

	/**
	 * Creates a new BitContainer with this BitContainer as source. It will start at the given position and the amount of elements
	 * @param start The start position (inclusive)
	 * @param amount The amount of bits to be contained
	 * @return A sub container containing the correct bits
	 * @throws IndexOutOfBoundsException if start + length >= bits()
	 */
	public BitContainer getSubContainer(final int start, final int amount) {
		final BitContainer container = new BitContainer(amount);
		System.arraycopy(bits, start, container.bits, 0, amount);
		return container;
	}

	/**
	 * This method creates a new {@link BitContainer} which has the same amount of bits as this object and the given object, those objects are joined.
	 * @param container The container to append to this container (in a new container)
	 * @return A new {@link BitContainer}
	 */
	public BitContainer join(final BitContainer container) {
		final BitContainer new_container = new BitContainer(bits.length + container.bits.length);
		System.arraycopy(bits, 0, new_container.bits, 0, bits.length);
		System.arraycopy(container.bits, 0, new_container.bits, bits.length, container.bits.length);
		return new_container;
	}

	/**
	 * Calculates the value of this {@link BitContainer}
	 * @return The value of this {@link BitContainer}
	 */
	public int value() {
		int result = 0;
		for (int i = 0; i < bits.length; i++)
			if(bits[i])
				result += Math.pow(2, i);
		return result;
	}

	/**
	 * Retrieves the amount of bits this container has
	 * @return The amount of bits
	 */
	public int bits() {
		return bits.length;
	}

	@Override
	public String toString () {
		return String.format("%d%s", value(), Arrays.toString(bits));
	}

	@Override
	public int hashCode () {
		return 17 + 53 * value();
	}

	@Override
	public boolean equals (final Object obj) {
		if(obj instanceof BitContainer) {
			final BitContainer other = (BitContainer) obj;
			return value() == other.value();
		}
		return false;
	}
}
