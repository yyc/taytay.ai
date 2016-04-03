package pssix;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class MarkovModel implements IMarkovModel {

	private HashMap<RollingHashKey, TreeMultiset<Character>> hashTable;
	private int m_order;

	// Temporary variables
	private RollingHashKey tempKey;
	private char tempChar;
	private TreeMultiset<Character> tempTree;
	private int i;

	/**
	 * creates a Markov Model from the specified text of the specified order.
	 * You can assume that the order will be at least 1.
	 * 
	 * @param text
	 * @param order
	 */
	public MarkovModel(String text, int order) {
		// TODO Implement it as a HashTable of trees, which should be
		// a suitable implementation for a very sparse 2D array
		hashTable = new HashMap<RollingHashKey, TreeMultiset<Character>>();
		TreeMultiset.rand = new Random();
		m_order = order;
		Scanner stringScanner = new Scanner(text);
		stringScanner.useDelimiter("");
		char[] ary = new char[order];
		for (i = 0; i < order; i++) {
			assert stringScanner.hasNext();
			ary[i] = stringScanner.next().charAt(0);
		}
		assert stringScanner.hasNext(); // Assume that the text length is of at
										// least length order
		tempKey = new RollingHashKey(ary);
		while (stringScanner.hasNext()) {
			tempChar = stringScanner.next().charAt(0);
			tempTree = hashTable.get(tempKey);
			if (tempTree != null) {
				tempTree.insertOrIncrement(tempChar);
			} else {
				hashTable.put(tempKey, new TreeMultiset<Character>(tempChar));
			}
			tempKey = tempKey.displace(tempChar);
		}
		stringScanner.close();
	}

	@Override
	public int order() {
		// TODO Auto-generated method stub
		return m_order;
	}

	@Override
	public int getFrequency(String kgram) {
		tempKey = new RollingHashKey(kgram.toCharArray());
		tempTree = hashTable.get(tempKey);
		if (tempTree != null) {
			return tempTree.getCount();
		} else {
			return 0;
		}
	}

	@Override
	public int getFrequency(String kgram, char c) {
		assert kgram.length() == m_order;
		tempKey = new RollingHashKey(kgram.toCharArray());
		tempTree = hashTable.get(tempKey);
		if (tempTree != null) {
			return tempTree.search(c);
		} else {
			return 0;
		}
	}

	@Override
	public char nextCharacter(String kgram) {
		assert kgram.length() == m_order;
		tempKey = new RollingHashKey(kgram.toCharArray());
		tempTree = hashTable.get(tempKey);
		if (tempTree != null) {
			return tempTree.getRandom();
		} else {
			return 0;
		}
	}

	public void printn(RollingHashKey rk, int n) {
		n -= m_order;
		System.out.println(rk.toString());
		tempTree = hashTable.get(rk);
		tempKey = rk;
		while (tempTree != null && n-- > 0) { // https://stackoverflow.com/questions/1642028/what-is-the-name-of-the-operator
			tempChar = tempTree.getRandom();
			System.out.print(tempChar);
			tempKey = tempKey.displace(tempChar);
			tempTree = hashTable.get(tempKey);
		}
		if (n >= 0) {
			this.printn(rk, n);
		} else {
			return;
		}

	}

	@Override
	public void setRandomSeed(long seed) {
		TreeMultiset.rand.setSeed(seed);

	}

}
