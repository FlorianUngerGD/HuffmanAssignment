package main;

import java.math.BigInteger;

public class Key {
	private BigInteger firstInt;
	private BigInteger secondInt;

	public Key(BigInteger firstInt, BigInteger secondInt) {
		this.firstInt = firstInt;
		this.secondInt = secondInt;
	}

	public String toString() {
		return "(" + firstInt + "," + secondInt + ")";
	}

	public BigInteger getFirstInt() {
		return firstInt;
	}

	public void setFirstInt(BigInteger firstInt) {
		this.firstInt = firstInt;
	}

	public BigInteger getSecondInt() {
		return secondInt;
	}

	public void setSecondInt(BigInteger secondInt) {
		this.secondInt = secondInt;
	}
}
