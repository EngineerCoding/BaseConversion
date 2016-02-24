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

/**
 * This class represents a value from the given {@link Base}. This class does not convert the number itself, it simply stores the {@link #base}, {@link #value} and the
 * {@link #representation} of the number. It can also divide, multiply, add and subtract the number by calculating the values and then converting it with the given base 
 * or the base it is in.
 */
public class BaseNumber {

	/**
	 * The base this number represents
	 */
	private final Base base;
	
	/**
	 * The value of this number
	 */
	private final int value;
	
	/**
	 * The representation of the value without the prefix of the base.
	 */
	private String representation;

	/**
	 * Creates a new number of the given variables.
	 * @param base The {@link Base} this number represents
	 * @param value The value of this number object
	 * @param representation The representation of the value, converted by the {@link Base}
	 */
	protected BaseNumber (final Base base, final int value, final String representation) {
		this.base = base;
		this.value = value;
		this.representation = representation;
	}

	/**
	 * Retrieves the value of this number
	 */
	public int value() {
		return value;
	}

	/**
	 * Retrieves the {@link Base} of this number
	 */
	public Base getBase() {
		return base;
	}

	public BaseNumber subtract(final BaseNumber baseNumber) {
		return subtract(baseNumber, getBase());
	}

	public BaseNumber subtract(final BaseNumber baseNumber, final Base base) {
		return base.encode(value() - baseNumber.value());
	}

	public BaseNumber add(final BaseNumber baseNumber) {
		return add(baseNumber,  getBase());
	}

	public BaseNumber add(final BaseNumber baseNumber, final Base base) {
		return base.encode(value() + baseNumber.value());
	}

	public BaseNumber divide(final BaseNumber baseNumber) {
		return divide(baseNumber,  getBase());
	}

	public BaseNumber divide(final BaseNumber baseNumber, final Base base) {
		return base.encode(value() / baseNumber.value());
	}

	public BaseNumber multiply(final BaseNumber baseNumber) {
		return multiply(baseNumber,  getBase());
	}

	public BaseNumber multiply(final BaseNumber baseNumber, final Base base) {
		return base.encode(value() * baseNumber.value());
	}


	@Override
	public String toString () {
		return base.getPrefix() + representation;
	}

	@Override
	public int hashCode () {
		int result = 17;
		result = 53 * result + value;
		result += base.hashCode();
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (obj.getClass() == getClass()) {
			final BaseNumber baseNumber = (BaseNumber) obj;
			return baseNumber.base.base == base.base && baseNumber.value == value;
		}
		return false;
	}
}
