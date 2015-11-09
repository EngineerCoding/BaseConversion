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

public class BaseNumber {

	private final Base base;
	private final int value;
	private String representation;

	protected BaseNumber (final Base base, final int value, final String representation) {
		this.base = base;
		this.value = value;
		this.representation = representation;
	}

	public int value() {
		return value;
	}

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
