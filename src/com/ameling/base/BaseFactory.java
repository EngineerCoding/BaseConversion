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

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the class which an user would interact with, because in general you only want 1 instance of a base. However since one can create a base of his own, the create method
 * can be used for that. Note that via the create method the base will not be stored. If one wants to get a base which is not loaded by default, one should use the {@link #getBase(int, char[])}
 * method, because then one will be created, stored en returned to the end user. From then one can get it via {@link #getBase(int)}.
 */
public class BaseFactory {

	/**
	 * The storage of the base number along with the {@link Base} object.
	 */
	private static Map<Integer, Base> base_storage = new HashMap<>();

	static {
		base_storage.put(2, createBase(2, new char[] { '0', '1' }, "0b")); // Binary
		base_storage.put(8, createBase(8, "012345678".toCharArray(), "0")); // Base 8 or octal base
		base_storage.put(16, createBase(16, "0123456789ABCDEF".toCharArray(), "0x")); // Hexadecimal
		base_storage.put(32, createBase(32, "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray())); // Base32, note that padding is not accounted for
		base_storage.put(64, createBase(64, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray())); // Base 64, note that padding is not accounted for
	}

	/**
	 * Checks if the given base is in the internal storage.
	 * @param base The base to check for
	 * @return True when it exists or false when it doesn't
	 */
	public static boolean gotBase(final int base) {
		return base_storage.containsKey(base);
	}

	/**
	 * Simply retrieves the base if it exists in the storage
	 * @param base The base to retrieve
	 * @return The {@link Base} object or null when it does not exist
	 */
	public static Base getBase(final int base) {
		if (gotBase(base)) {
			return base_storage.get(base);
		}
		return null;
	}

	/**
	 * Same as {@code getBase(int, char[], null)}
	 * @param base The base to retrieve or create
	 * @param used_chars The according char array
	 * @return The {@link Base} object it represents
	 */
	public static Base getBase(final int base, final char[] used_chars) {
		return getBase(base, used_chars, null);
	}

	/**
	 * Tries to retrieve a {@link Base} object from the storage, otherwise it will create an object, store it and return it
	 * @param base The base to retrieve or create
	 * @param used_chars The according char array
	 * @param prefix The prefix that is used to identify this base, optional
	 * @return A {@link Base} object
	 */
	public static Base getBase(final int base, final char[] used_chars, final String prefix) {
		Base base_object = getBase(base);
		if (base_object == null) {
			base_object = createBase(base, used_chars, prefix);
			base_storage.put(base, base_object);
		}
		return base_object;
	}

	/**
	 * This method just creates a {@link Base} object with calling {@code createBase(base, used_chars, null}
	 * @param base The base number it represents
	 * @param used_chars The according char array
	 * @return A {@link Base} object
	 */
	public static Base createBase(final int base, final char[] used_chars) {
		return createBase(base, used_chars, null);
	}

	/**
	 * This method just creates a {@link Base} object with calling {@code createBase(base, used_chars, null}
	 * @param base The base number it represents
	 * @param used_chars The according char array
	 * @param prefix The prefix that is used to identify this base, optional
	 * @return A {@link Base} object
	 */
	public static Base createBase(final int base, final char[] used_chars, final String prefix) {
		return new Base(base, used_chars, prefix);
	}

}
