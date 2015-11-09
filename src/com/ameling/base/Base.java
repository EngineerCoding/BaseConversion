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
 * This class represents a base as in base2, base8 etc. This only works for number which are a power of 2, because this algorithm uses binary operations. For example, base10 won't work
 * because it does not have a binary logic behind it, just a logic by counting 1 for one higher. Of course other bases can be created by overriding the encode and decode method, but
 * technically there is no specific use for base10, since an integer represents that already.
 * Another example, let's take hexadecimal, does have a binary logic behind it.
 * The number {@code 0x9F} can be converted to binary to {@code 10011111} which is 159 in decimal. Each character in hexadecimal is 4 bit, so it can have 16 different values (including 0).
 * Then the array of characters should look like: {@code 0 1 2 3 4 5 6 7 8 9 A B C D E F}. Then the 4 bit value can be converted to the correct index and is appended that way.
 */
public class Base {

	/**
	 * The base of this object
	 */
	public final int base;

	/**
	 * The amount of bits where one character is stored
	 */
	private final int amount_bits;

	/**
	 * The characters in order which correlates with the base.
	 */
	final char[] used_characters;

	/**
	 * The prefix of this base, can be null
	 */
	private final String prefix;

	/**
	 * Creates a new base without a prefix
	 * @param base The base representing this object
	 * @param representing_characters The correct characters in the correct order from 0 to {base}
	 * @see {@link #Base(int, char[], String)}
	 */
	protected Base(final int base, final char[] representing_characters) {
		this(base, representing_characters, null);
	}

	/**
	 * Creates a new base with a prefix. The amount of bits is calculated using the following formula: log(base) / log(2)
	 * @param base The base representing this object
	 * @param representing_characters The correct characters in the correct order from 0 to {base}
	 * @param prefix The prefix of this base, can be null
	 */
	protected Base(final int base, final char[] representing_characters, final String prefix) {
		this.base = base;
		this.used_characters = representing_characters;
		this.prefix = prefix;
		amount_bits = (int) (Math.log(base) / Math.log(2));
		//TODO check for amount characters
	}

	/**
	 * Encodes the base10 number to the base which is represented by this object. That is done using an and on the number and then bit shifting the number to the right by the amount of bits.
	 * @param value The value to encode
	 * @return The representing {@link BaseNumber}
	 */
	public BaseNumber encode(final int value) {
		int value_copy = value;
		final StringBuilder builder = new StringBuilder();
		while (value_copy != 0) {
			builder.append(used_characters[value_copy & base - 1]);
			value_copy = value_copy >> amount_bits;
		}
		return new BaseNumber(this, value, builder.reverse().toString());
	}

	/**
	 * Encodes the container to a {@link BaseNumber} which is in the format of this base
	 * @param container The container to encode from
	 * @return A representing {@link BaseNumber}
	 */
	public BaseNumber encodeFromBitContainer(final BitContainer container) {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < container.bits(); i += amount_bits) {
			final BitContainer subContainer = container.getSubContainer(i, Math.min(container.bits() - i, amount_bits));
			builder.append(used_characters[subContainer.value()]);
		}
		return new BaseNumber(this, container.value(), builder.reverse().toString());
	}

	/**
	 * Decodes the presentation using {@link #decodeToBitContainer(String)} and returns the direct value as an integer.
	 * @param presentation The string representing the number
	 * @return The integer value fo the number
	 */
	public int decode(final String presentation) {
		return decodeToBitContainer(presentation).value();
	}

	/**
	 * Decodes a String which this base represents. The only characters allowed are the in the used_characters and of course the prefix.
	 * At first the prefix is stripped off when it is available, and the String is reversed, because that is how it works with those numbers. Then the bits this base represent are checked
	 * and set in a {@link BitContainer} to be appended to a main {@link BitContainer}.
	 * @param presentation The string representing the number
	 * @return The {@link BitContainer} which represents the same value.
	 */
	public BitContainer decodeToBitContainer(String presentation) {
		presentation = presentation.trim();
		final int prefix_length = getPrefix().length();
		if (prefix_length > 0 && presentation.startsWith(getPrefix()))
			presentation = presentation.substring(prefix.length());
		presentation = new StringBuilder().append(presentation).reverse().toString();

		BitContainer main_container = new BitContainer(0);
		BitContainer filler_container = new BitContainer(amount_bits);
		for (final char character : presentation.toCharArray()) {
			int position = -1;
			for (int i = 0; i < used_characters.length; i++) {
				if (character == used_characters[i]) {
					position = i;
					break;
				}
			}
			if (position > -1) {
				main_container = main_container.join(filler_container.fillContainer(position));
			}
		}
		return main_container;
	}

	/**
	 * Retrieves the prefix of this base, if none is given an empty string is returned
	 * @return The correct prefix
	 */
	public String getPrefix() {
		if (prefix == null)
			return "";
		return prefix;
	}

	@Override
	public int hashCode () {
		int result = 17;
		result += 53 * base;
		result += 53 * amount_bits;
		result += Arrays.hashCode(used_characters);
		result += getPrefix().hashCode();
		return result;
	}

	@Override
	public String toString () {
		return "base" + Integer.toString(base);
	}

	@Override
	public boolean equals (Object obj) {
		if(obj.getClass() == getClass())
			return obj.hashCode() == hashCode();
		return false;
	}
}
