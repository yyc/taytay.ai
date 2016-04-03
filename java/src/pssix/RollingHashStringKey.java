package pssix;

import java.util.Arrays;

public class RollingHashStringKey {

	private int hash;
	String[] StringArray;
	public static int shiftAmount = 5;
	private static int maxShiftAmount;

	public RollingHashStringKey(String[] cArray) {
		StringArray = cArray;
		maxShiftAmount = (StringArray.length - 1) * shiftAmount;
		hash = 0;
		for (int i = 0; i < StringArray.length; i++) {
			hash = hash << shiftAmount;
			hash += StringArray[i].hashCode() % shiftAmount;
		}
	}

	private RollingHashStringKey(String[] cArray, int h) {
		StringArray = cArray;
		hash = h;
	}

	public RollingHashStringKey displace(String c) {
		String[] newArray = Arrays.copyOfRange(StringArray, 1, StringArray.length + 1);
		newArray[StringArray.length - 1] = c;
		int newHash = ((hash - ((StringArray[0].hashCode() % 5) << maxShiftAmount)) << shiftAmount)
				+ (c.hashCode() % shiftAmount);
		return new RollingHashStringKey(newArray, newHash);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof RollingHashStringKey) {
			return Arrays.equals(StringArray, ((RollingHashStringKey) o).StringArray);
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
		return String.join(" ", StringArray);
	}
}
