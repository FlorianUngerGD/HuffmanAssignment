import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.FileOutputStream;

import com.sun.source.tree.Tree;

import main.FrequencyPair;
import main.HuffmanCode;
import main.TreeNode;

public class Main {
	private static List<HuffmanCode> huffmanCodes;

	public static void main(String[] args) {
		huffmanCodes = new ArrayList<>();

		// **ENCODING**
		// Read Text File to ASCII
		List<Integer> asciiCodes = getAscii(loadFileContents("text.txt"));

		// Create Frequency Table of Text File
		List<FrequencyPair> freqTable = getFrequencyTable(asciiCodes);

		System.out.println("Frequency Table:");
		for (FrequencyPair pair : freqTable) {
			System.out.println(pair.getCode() + ": " + pair.getFrequency());
		}

		// Create Huffman Code from frequency Table
		// Create Tree
		List<TreeNode> nodes = new ArrayList<>();
		for (FrequencyPair pair : freqTable) {
			nodes.add(new TreeNode(pair.getFrequency(), pair.getCode()));
		}

		while(nodes.size() > 1) {
			List<TreeNode> sortedList = nodes.stream().sorted((o1, o2) -> o1.getFrequencyValue() < o2.getFrequencyValue() ? -1 : o1.getFrequencyValue() == o2.getFrequencyValue() ? 0 : 1)
					.collect(Collectors.toList());
			TreeNode left = sortedList.get(0);
			TreeNode right = sortedList.get(1);

			nodes.add(new TreeNode(left.getFrequencyValue() + right.getFrequencyValue(), left, right));

			nodes.remove(left);
			nodes.remove(right);
		}

		// Read Tree
		TreeNode root = nodes.get(0);
		String bitString = "";

		System.out.println("Huffman Codes:");
		getCode(root, bitString);

		System.out.println("Huffman Code generated");

		// Store Huffman Table in dec_tab.txt
		saveCodesToFile("dec_tab.txt", huffmanCodes);

		// Convert read in file to Bitstring using huffman code
		String newBitString = "";
		for (Integer code : asciiCodes) {
			Optional<HuffmanCode> corresponding = huffmanCodes.stream().filter(huffmanCode -> huffmanCode.getAsciiCode() == code).findFirst();
			if(corresponding.isPresent()) {
				newBitString += corresponding.get().getHuffmanBits();
			} else {
				System.out.println("ERROR: No corresponding Huffman Code found for ascii: " + code);
			}
		}

		// Append a 1 and afterwards 0s until the length of the string is divisible by 8
		// Convert this (extended) bit string into a bytearray by combining each 8 subsequent bits to a byte.
		// Store this bytearray in an external file output.dat
		saveByteArrayToFile("output.dat", stringToByteArray(append10(newBitString)));
		System.out.println("HuffmanCode saved in output.dat");


		// **DECODING**
		// Read in Huffman Code table and byte array

		// Convert byte array to bitstring where trailing 1s and 0s get removed

		//  remaining bit string is then to be decoded using the code table and to be stored in an external filedecompress.txt
	}

	public static void getCode(TreeNode root, String bitString)
	{
		if (root.isLeaf()) {
			System.out.println(root.getCharValue() + ":" + bitString);
			huffmanCodes.add(new HuffmanCode(root.getCharValue(), bitString));
			return;
		}

		getCode(root.getLeft(), bitString + "0");
		getCode(root.getRight(), bitString + "1");
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

	public static byte[] stringToByteArray(String bit) {
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

	public static void saveByteArrayToFile(String fileName, byte[] byteArray) {
		// Code gets saved to file
		Path path = Paths.get(fileName);
		try {
			if (Files.exists(path)) {
				Files.delete(path);
			}
			Files.createFile(path);
			Files.write(path, byteArray);
		}
		catch(IOException e){
			e.getStackTrace();
		}



	}

	public static void saveCodesToFile(String fileName, List<HuffmanCode> huffmanCodes) {
		// Code gets saved to file
		String newString = "";

		for (int i = 0; i < huffmanCodes.size(); i++) {
			newString += String.valueOf(huffmanCodes.get(i).getAsciiCode() + ":" + huffmanCodes.get(i).getHuffmanBits());

			if (i < huffmanCodes.size() - 1) {
				newString += "-";
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
