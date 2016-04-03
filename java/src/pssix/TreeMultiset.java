package pssix;

import java.util.Random;

public class TreeMultiset<DType extends Comparable<DType>> {
	private TreeMultiset<DType> right_child;
	private TreeMultiset<DType> left_child;

	public static Random rand;

	private DType value;
	private int count = 1;
	private int right_count = 0;
	private int left_count = 0;

	public TreeMultiset(DType a) {
		value = a;
	}

	/**
	 * Inserts a value, or increments it if the value is already present returns
	 * true if the value is inserted, or false if the value is incremented.
	 * 
	 * @param a
	 */
	public boolean insertOrIncrement(DType a) {
		if (value.equals(a)) {
			count++;
			return false;
		} else if (a.compareTo(value) > 0) {
			right_count++;
			if (right_child == null) {
				right_child = new TreeMultiset<DType>(a);
				return true;
			} else {
				return right_child.insertOrIncrement(a);
			}
		} else { // value is more
			left_count++;
			if (left_child == null) {
				left_child = new TreeMultiset<DType>(a);
				return true;
			} else {
				return left_child.insertOrIncrement(a);
			}
		}
	}

	public int search(DType a) {
		if (value.equals(a)) {
			return count;
		} else if (a.compareTo(value) > 0) { // value is less, traverse right
			if (right_child == null) {
				return 0;
			} else {
				return right_child.search(a);
			}
		} else { // value is more
			if (left_child == null) {
				return 0;
			} else {
				return left_child.search(a);
			}
		}
	}

	public DType getRandom() {
		int total = count + right_count + left_count;
		return this.getIndex(rand.nextInt(total));
	}

	public DType getIndex(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (index < left_count) {
			return left_child.getIndex(index);
		} else if (index - left_count < count) {
			return value;
		} else {
			if (right_child == null) {
				throw new IndexOutOfBoundsException();
			} else {
				return right_child.getIndex(index - (count + left_count));
			}
		}
	}

	public int getCount() {
		return this.count + this.left_count + this.right_count;
	}

}
