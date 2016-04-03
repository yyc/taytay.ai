package pssix;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextGenerator {

	public static MarkovModel model;

	public static void main(String[] args) {
		generateText(args);
		// generateWordsDenko();
		// generateWordsTest();
		// generateWordsTayTay();
	}

	public static void generateText(String[] args) {
		// 7 1000 src/pssix/denko.txt seems to work well
		int order = new Integer(args[0]);
		int n = new Integer(args[1]);
		String text = readFile(args[2], StandardCharsets.US_ASCII);
		model = new MarkovModel(text, order);
		char[] startChars = new char[order];
		text.getChars(0, order, startChars, 0);
		model.printn(new RollingHashKey(startChars), n);

	}

	public static void generateWordsDenko() {
		int order = new Integer(3);
		int n = new Integer(1000);
		String text = readFile("src/pssix/denko.txt", StandardCharsets.US_ASCII);
		MarkovModelWords model = new MarkovModelWords(text, order);
		model.printn(model.getStartKey(), n);
	}

	public static void generateWordsTest() {
		int order = 2;
		int n = 1000;
		String text = readFile("src/pssix/test.txt", StandardCharsets.US_ASCII);
		MarkovModelWords model = new MarkovModelWords(text, order);
		model.printn(model.getStartKey(), n);
	}

	public static void generateWordsTayTay() {
		int order = 2;
		int n = 1000;
		String text = readFile("src/pssix/taytay.txt", StandardCharsets.US_ASCII);
		MarkovModelWords model = new MarkovModelWords(text, order);
		model.printn(model.getStartKey(), n);
	}

	// From
	// https://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
	public static String readFile(String filename, Charset encoding) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(filename));
			return new String(encoded, encoding);
		} catch (IOException e) {
			return null;
		}
	}
}
