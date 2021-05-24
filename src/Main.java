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
import java.util.Scanner;
import java.util.stream.Collectors;

import main.FrequencyPair;
import main.TreeNode;

public class Main {

	public static void main(String[] args) {
		// **ENCODING**
		// Read Text File to ASCII
		List<Integer> asciiCodes = getAscii(loadFileContents("text.txt"));

		// Create Frequency Table of Text File
		List<FrequencyPair> freqTable = getFrequencyTable(asciiCodes);

		System.out.println("Frequency Table:");
		for (FrequencyPair pair : freqTable) {
			System.out.println(pair.getCode() + ": " + pair.getFrequency());
		}

		TreeNode rootNode;
		// Create Huffman Code from frequency Table
		for (FrequencyPair pair : freqTable) {

		}

		// Store Huffman Table in dectab.txt

		// Convert read in file to Bitstring using huffman code

		// Append a 1 and afterwards 0s until the length of the string is divisible by 8

		// Convert this (extended) bit string into a bytearray by combining each 8 subsequent bits to a byte.

		// Store this bytearray in an external file output.dat

		// **DECODING**
		// Read in Huffman Code table and byte array

		// Convert byte array to bitstring where trailing 1s and 0s get removed

		//  remaining bit string is then to be decoded using the code table and to be stored in an external filedecompress.txt
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

	public static List<FrequencyPair> getFrequencyTable(List<Integer> asciiCodes) {
		// AbstractMap.SimpleEntry<Integer, Integer> =
		List<FrequencyPair> freqTable = new ArrayList<FrequencyPair>();

		for (Integer code : asciiCodes) {
			List<FrequencyPair> matching = freqTable.stream().filter(entry -> entry.getCode() == code)
					.collect(Collectors.toList());
			if (matching.size() == 0) {
				freqTable.add(new FrequencyPair(code, 1));
			} else if (matching.size() == 1) {
				matching.get(0).incrementFrequency();
			} else {
				System.out.println("ERROR: Multiple entries for same char in frequency table!");
			}
		}

		return freqTable.stream()
				.sorted((o1, o2) -> o1.getFrequency() < o2.getFrequency() ? -1 : o1.getFrequency() == o2.getFrequency() ? 0 : 1)
				.collect(Collectors.toList());
	}

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

	public static String append10(String bit) {
		int length = bit.length();
		int missingBits = length % 8;
		if (missingBits > 0) {
			bit = bit + "1";
			missingBits--;
		}
		for (int i = 0; i < missingBits; i++) {
			bit = bit + "0";
		}
		return bit;
	}

	public static byte[] stringToArray(String bit) {
		byte[] bval = new BigInteger(bit, 2).toByteArray();
		return bval;


		/*int anzByte = bit.length() / 8;
		Byte[] byteArray = new Byte[anzByte];
		for (int i = 0; i < anzByte; i++) {
			int intermediateI = Integer.valueOf(bit.substring(8*i, 8*i+1),2);
			Byte intermediateB = new ByteBuffer().putInt(intermediateI).compact();
			byteArray[i] = null;
		}

		return null;*/

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
