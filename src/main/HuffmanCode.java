package main;

public class HuffmanCode {
	private int asciiCode;

	private String huffmanBits;

	public HuffmanCode(int asciiCode, String huffmanBits) {
		this.asciiCode = asciiCode;
		this.huffmanBits = huffmanBits;
	}

	public int getAsciiCode() {
		return asciiCode;
	}

	public void setAsciiCode(int asciiCode) {
		this.asciiCode = asciiCode;
	}

	public String getHuffmanBits() {
		return huffmanBits;
	}

	public void setHuffmanBits(String huffmanBits) {
		this.huffmanBits = huffmanBits;
	}
}
