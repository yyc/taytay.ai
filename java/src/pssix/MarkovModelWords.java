package pssix;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MarkovModelWords {

	private HashMap<RollingHashStringKey, TreeMultiset<String>> hashTable;
	private int m_order;
	private RollingHashStringKey startKey;
	private Pattern separator = Pattern.compile("[\\(. ) ?<=>!(. ) -]+?");

	// Temporary variables
	private RollingHashStringKey tempKey;
	private String tempString;
	private TreeMultiset<String> tempTree;
	private int i;

	/**
	 * creates a Markov Model from the specified text of the specified order.
	 * You can assume that the order will be at least 1.
	 * 
	 * @param text
	 * @param order
	 */
	public MarkovModelWords(String text, int order) {
		// TODO Implement it as a HashTable of trees, which should be
		// a suitable implementation for a very sparse 2D array
		hashTable = new HashMap<RollingHashStringKey, TreeMultiset<String>>();
		TreeMultiset.rand = new Random();
		m_order = order;
		Scanner stringScanner = new Scanner(text);
		stringScanner.useDelimiter(separator);
		String[] ary = new String[order];
		for (i = 0; i < order; i++) {
			assert stringScanner.hasNext();
			ary[i] = stringScanner.next();
		}
		assert stringScanner.hasNext(); // Assume that the text length is of at
										// least length order
		tempKey = new RollingHashStringKey(ary);
		startKey = tempKey;
		while (stringScanner.hasNext()) {
			tempString = stringScanner.next();
			tempTree = hashTable.get(tempKey);
			if (tempTree != null) {
				tempTree.insertOrIncrement(tempString);
			} else {
				hashTable.put(tempKey, new TreeMultiset<String>(tempString));
			}
			tempKey = tempKey.displace(tempString);
		}
		stringScanner.close();
	}

	public int order() {
		// TODO Auto-generated method stub
		return m_order;
	}

	public int getFrequency(String kgram) {
		tempKey = new RollingHashStringKey(kgram.split(separator.pattern()));
		tempTree = hashTable.get(tempKey);
		if (tempTree != null) {
			return tempTree.getCount();
		} else {
			return 0;
		}
	}

	public int getFrequency(String kgram, String c) {
		assert kgram.length() == m_order;
		tempKey = new RollingHashStringKey(kgram.split(separator.pattern()));
		tempTree = hashTable.get(tempKey);
		if (tempTree != null) {
			return tempTree.search(c);
		} else {
			return 0;
		}
	}

	public boolean augment(String text) {
		Scanner stringScanner = new Scanner(text);
		stringScanner.useDelimiter(separator);
		String[] ary = new String[m_order];
		for (i = 0; i < m_order; i++) {
			if (!stringScanner.hasNext()) {
				return false;
			}
			ary[i] = stringScanner.next();
		}
		if (!stringScanner.hasNext()) {
			return false;
		}

		tempKey = new RollingHashStringKey(ary);
		startKey = tempKey;
		while (stringScanner.hasNext()) {
			tempString = stringScanner.next();
			tempTree = hashTable.get(tempKey);
			if (tempTree != null) {
				tempTree.insertOrIncrement(tempString);
			} else {
				hashTable.put(tempKey, new TreeMultiset<String>(tempString));
			}
			tempKey = tempKey.displace(tempString);
		}
		stringScanner.close();
		return true;
	}

	public String nextString(String kgram) {
		assert kgram.length() == m_order;
		tempKey = new RollingHashStringKey(kgram.split(separator.pattern()));
		tempTree = hashTable.get(tempKey);
		if (tempTree != null) {
			return tempTree.getRandom();
		} else {
			return "";
		}
	}

	public String printn(RollingHashStringKey rk, int n) {
		n -= m_order;
		StringBuilder sb = new StringBuilder();
		sb.append(rk.toString() + " ");
		tempTree = hashTable.get(rk);
		tempKey = rk;
		n = n * 2;
		while (tempTree != null && n-- > 0) { // https://stackoverflow.com/questions/1642028/what-is-the-name-of-the-operator
			tempString = tempTree.getRandom();
			sb.append(tempString);
			sb.append(" ");
			tempKey = tempKey.displace(tempString);
			tempTree = hashTable.get(tempKey);
		}
		return sb.toString();
	}

	public RollingHashStringKey getStartKey() {
		return startKey;
	}

	public void setRandomSeed(long seed) {
		TreeMultiset.rand.setSeed(seed);

	}

}
