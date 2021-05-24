import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) {
		
	}

	/*
	public static Key readKeyFromFile(String fileName) {
		// Key gets read from file
		String text = loadFileContents(fileName);
		String number1 = text.substring(text.indexOf("(") + 1);
		number1 = number1.substring(0, number1.indexOf(","));
		String number2 = text.substring(text.indexOf(",") + 1);
		number2 = number2.substring(0, number2.indexOf(")"));

		return new Key(new BigInteger(number1), new BigInteger(number2));
	}
	*/

	public static String loadFileContents(String filePath) {
		// File contents get loaded
		try {
			Path path = Paths.get(filePath);

			return Files.readAllLines(path).stream().collect(Collectors.joining());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void saveCodeToFile(String fileName, List<BigInteger> bigInts) {
		// Code gets saved to file
		String newString = "";

		for (int i = 0; i < bigInts.size(); i++) {
			newString += String.valueOf(bigInts.get(i));

			if (i < bigInts.size() - 1) {
				newString += ",";

			}
		}

		saveFile(fileName, newString);
	}

	public static List<BigInteger> getBigIntegersFromFile(String filename) {
		List<BigInteger> newBigInts = new ArrayList();

		try {
			Scanner scanner = new Scanner(new File(filename));
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				newBigInts.add(scanner.nextBigInteger());
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newBigInts;
	}

	public static String saveFile(String fileName, String text) {
		// File gets saved
		try {
			File newFile = new File(fileName);
			newFile.createNewFile();

			FileWriter writer = new FileWriter(fileName);
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<Integer> getAscii(String input) {
		// Convert the String input to a ASCII Code Array in bytes
		byte[] byteArray = input.getBytes(StandardCharsets.US_ASCII);

		List<Integer> asciiList = new ArrayList<>();

		// Cast the Bytes to easier readable ints
		for (byte asciiByte : byteArray) {
			int asciiCode = (int) asciiByte;
			asciiList.add(asciiCode);
		}

		return asciiList;
	}

	public static String getStringFromAscii(List<Integer> AsciiCodes) {
		// Convert the ASCII Code into a String
		String text = "";

		for (int code : AsciiCodes) {
			text += ((char) code);
		}

		return text;
	}
}
