package pssix;

public interface IMarkovModel {

	/**
	 * Returns the order of the Markov model.
	 * 
	 * @return
	 */
	public int order();

	/**
	 * Returns the number of times the specified string kgram appears in the
	 * input text. The kgram must be the length specified by the order of the
	 * Markov model.
	 * 
	 * @param kgram
	 * @return
	 */
	public int getFrequency(String kgram);

	/**
	 * Returns the number of times the specified character ‘c’ appears
	 * immediately after the string kgram in the input text. The kgram must be
	 * the length specified by the order of the Markov model.
	 * 
	 * @param kgram
	 * @param c
	 * @return
	 */
	public int getFrequency(String kgram, char c);

	/**
	 * Returns a random character. The probability of a character ‘c’ should be
	 * equal to getFrequency(kgram,c)/getFrequency(kgram). That is, the
	 * probability of character ‘c’ should be equal to the frequency that ‘c’
	 * follows the string kgram in the text. If there is no possible next
	 * character, then return an indicator of such, e.g.: final char NOCHARACTER
	 * = (char)(255). The kgram must be the length specified by the order of the
	 * Markov model.
	 * 
	 * @param kgram
	 * @return
	 */

	public char nextCharacter(String kgram);

	/**
	 * Sets the random seed to be used by the random number generator. If you
	 * set the same seed, it will produce the same sequence of random numbers,
	 * and hence the same sequence of characters. We will use this only in
	 * testing your program.
	 * 
	 * @param seed
	 */
	public void setRandomSeed(long seed);
}
