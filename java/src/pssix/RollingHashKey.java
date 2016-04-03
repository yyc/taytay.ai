package pssix;

import java.util.Arrays;

public class RollingHashKey {

	private int hash;
	char[] charArray;
	public static int shiftAmount = 5;
	private static int maxShiftAmount;

	public RollingHashKey(char[] cArray) {
		charArray = cArray;
		maxShiftAmount = (charArray.length - 1) * shiftAmount;
		hash = 0;
		for (int i = 0; i < charArray.length; i++) {
			hash = hash << shiftAmount;
			hash += charArray[i];
		}
	}

	private RollingHashKey(char[] cArray, int h) {
		charArray = cArray;
		hash = h;
	}

	public RollingHashKey displace(char c) {
		char[] newArray = Arrays.copyOfRange(charArray, 1, charArray.length + 1);
		newArray[charArray.length - 1] = c;
		int newHash = ((hash - (charArray[0] << maxShiftAmount)) << shiftAmount) + c;
		return new RollingHashKey(newArray, newHash);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof RollingHashKey) {
			return Arrays.equals(charArray, ((RollingHashKey) o).charArray);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public String toString() {
		return new String(charArray);
	}
}
