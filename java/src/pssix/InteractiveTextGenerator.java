package pssix;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class InteractiveTextGenerator {

	private MarkovModelWords model;
	private static int order;

	public InteractiveTextGenerator(int a_order, String filename) {
		String text = readFile(filename, StandardCharsets.US_ASCII);
		order = a_order;
		model = new MarkovModelWords(text, order);
	}

	public boolean add(String s) {
		try {
			return model.augment(s);
		} catch (Exception e) {
			return false;
		}
	}

	public String print(String tempString, int n) {
		try {
			String[] startString = Arrays.copyOf(tempString.split(" ", order + 1), order);
			if (startString[order - 1] == null) {
				return "Prompt too short!";
			} else {
				try {
					String res = model.printn(new RollingHashStringKey(startString), n);
					if (res.trim() == tempString) {
						return null;
					} else {
						return res;
					}
				} catch (Exception e) {
					return "Print problem!" + e.getMessage() + e.getClass().getName();
				}
			}
		} catch (Exception e) {
			return "Problem with input!" + e.getMessage() + e.getClass().getName();
		}
	}

	public String print(int n) {
		try {
			return model.printn(model.getStartKey(), 40);
		} catch (Exception e) {
			return "Problems!" + e.getMessage() + e.getClass().getName();
		}

	}

	public static void main(String[] args) {
		order = new Integer(args[0]);
		InteractiveTextGenerator itg = new InteractiveTextGenerator(order, args[1]);
		int n = 100;
		Scanner inScanner = new Scanner(System.in);
		String tempString;
		StringBuilder sb;
		int i = 100;
		while (true) {
			if (inScanner.hasNextInt()) {
				n = inScanner.nextInt();
				tempString = inScanner.nextLine().trim();
				System.out.println(itg.print(tempString, n));
			} else if (inScanner.hasNextLine()) {
				i = 40;
				sb = new StringBuilder();
				while (i-- > 0 && inScanner.hasNextLine()) {
					tempString = inScanner.nextLine();
					sb.append(tempString);
					sb.append("\n");
					if (tempString.length() < 3) {
						i = 0;
						break;
					}
				}
				System.out.println(itg.add(sb.toString()));
			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

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
