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

import main.Key;
import main.KeyFactory;

public class Main {
	public static void main(String[] args) {
		assignment01();

		assignment02();

		assignment03();

		assignment04();
	}

	private static void assignment04() {
		// ASSIGNMENT 04 (decript predefined cipher.txt (in directory "providedFiles") and save to cipher-d.txt)
		System.out.println("--- Assignment 04");
		Key privateKey = readKeyFromFile("providedFiles/sk.txt");
		List<BigInteger> encryptedInts = getBigIntegersFromFile("providedFiles/cipher.txt");
		List<Integer> decriptedInts = new ArrayList<>();
		for (BigInteger code : encryptedInts) {
			decriptedInts.add(decriptInteger(privateKey, code));
		}

		String decriptedText = getStringFromAscii(decriptedInts);

		System.out.println("Decrypted text: " + decriptedText);
		saveFile("providedFiles/cipher-d.txt", decriptedText);

	}

	private static void assignment03() {
		// ASSIGNMENT 03 (decript cipher.txt and store to cipher-d.txt)
		System.out.println("--- Assignment 03");
		Key privateKey = readKeyFromFile("sk.txt");
		List<BigInteger> encryptedInts = getBigIntegersFromFile("cipher.txt");
		List<Integer> decriptedInts = new ArrayList<>();
		for (BigInteger code : encryptedInts) {
			decriptedInts.add(decriptInteger(privateKey, code));
		}

		String decriptedText = getStringFromAscii(decriptedInts);

		System.out.println("Decrypted text: " + decriptedText);
		saveFile("cipher-d.txt", decriptedText);
	}

	private static void assignment02() {
		// ASSIGNMENT 02 (encrypt text.txt)
		System.out.println("--- Assignment 02");
		Key publicKey = readKeyFromFile("pk.txt");
		List<Integer> asciiCodes = getAscii(loadFileContents("text.txt"));

		List<BigInteger> encryptedInts = new ArrayList();
		for (Integer code : asciiCodes) {
			encryptedInts.add(encriptInteger(publicKey, code));
		}

		System.out.println("Encrypted text: " + loadFileContents("text.txt"));
		saveCodeToFile("cipher.txt", encryptedInts);
	}

	private static void assignment01() {
		// ASSIGNMENT 01 (CREATING AN RSA KEY PAIR)
		System.out.println("--- Assignment 01");
		Random rnd1 = new Random();
		Random rnd2 = new Random();

		BigInteger p = BigInteger.probablePrime(1024, rnd1);
		BigInteger q = BigInteger.probablePrime(1024, rnd2);
		KeyFactory factory = new KeyFactory(p, q);

		System.out.println("Generated private key: " + factory.getPrivate().toString());
		System.out.println("Generated public key: " + factory.getPublic().toString());

		saveFile("sk.txt", factory.getPrivate().toString());
		saveFile("pk.txt", factory.getPublic().toString());
	}

	public static BigInteger encriptInteger(Key publicKey, Integer intToEncrypt) {
		// Provided Integer gets encrypted
		return fastExponentiation(BigInteger.valueOf(intToEncrypt), publicKey.getSecondInt(), publicKey.getFirstInt());
	}

	public static Integer decriptInteger(Key privateKey, BigInteger intToDecrypt) {
		// Provided Integer gets decrypted
		return intToDecrypt.modPow(privateKey.getSecondInt(), privateKey.getFirstInt()).intValue();
	}

	public static BigInteger fastExponentiation(BigInteger base, BigInteger exponent, BigInteger mod) {
		List<BigInteger> bitset = new ArrayList<>();

		//creates bitrepresentation of exponent in reverse order
		while(!(exponent.equals(BigInteger.valueOf(0)))){
			if (exponent.remainder(BigInteger.valueOf(2)).equals(BigInteger.valueOf(1))){
				bitset.add(BigInteger.valueOf(1));
			}
			else {
				bitset.add(BigInteger.valueOf(0));
			}
			exponent = exponent.divide(BigInteger.valueOf(2));
		}

		BigInteger h = BigInteger.valueOf(1);
		BigInteger k = base;

		for (int i = 0; i < bitset.size(); i++) {
			if (bitset.get(i).equals(BigInteger.valueOf(1))) {
				h = (h.multiply(k)).mod(mod);
			}
			k = k.multiply(k).mod(mod);
		}
		return h.mod(mod);
	}

	public static Key readKeyFromFile(String fileName) {
		// Key gets read from file
		String text = loadFileContents(fileName);
		String number1 = text.substring(text.indexOf("(") + 1);
		number1 = number1.substring(0, number1.indexOf(","));
		String number2 = text.substring(text.indexOf(",") + 1);
		number2 = number2.substring(0, number2.indexOf(")"));

		return new Key(new BigInteger(number1), new BigInteger(number2));
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
