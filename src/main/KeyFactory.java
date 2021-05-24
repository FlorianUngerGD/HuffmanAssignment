package main;

import java.math.BigInteger;
import java.util.Random;

public class KeyFactory {
	public BigInteger p;
	public BigInteger q;
	public BigInteger e;
	public BigInteger d;
	public BigInteger phiN;
	public BigInteger n;

	public KeyFactory(BigInteger p, BigInteger q) {
		this.p = p;
		this.q = q;
		this.n = p.multiply(q);
		this.phiN = phiOfN(this.n);
		this.e = generateE();
		this.d = multiplicativeInverse(this.phiN, e);

	}

	//generates the e for the public key
	private BigInteger generateE() {
		BigInteger minValue = BigInteger.valueOf(2);
		BigInteger maxValue = this.phiN;

		BigInteger range = maxValue.subtract(minValue);
		Random randNum = new Random();
		int len = maxValue.bitLength();
		BigInteger rand;
		do {
			rand = new BigInteger(len, randNum);
			if (rand.compareTo(minValue) < 0)
				rand = rand.add(minValue);
			if (rand.compareTo(range) >= 0)
				rand = rand.mod(range).add(minValue);
		} while (!(isCoPrime(rand, this.phiN)));

		return rand;
	}

	public Key getPublic() {
		return new Key(n, e);
	}

	public Key getPrivate() {
		return new Key(n, d);
	}

	//calculates phi of N
	public BigInteger phiOfN(BigInteger n) {
		return (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
	}

	//checks wether or not two numbers are coprime
	public static boolean isCoPrime(BigInteger inita, BigInteger initb) {
		if (inita.compareTo(initb) == -1) {
			BigInteger temp = inita;
			inita = initb;
			initb = temp;
		}
		BigInteger a = inita;
		BigInteger b = initb;
		BigInteger x0 = BigInteger.valueOf(1);
		BigInteger y0 = BigInteger.valueOf(0);
		BigInteger tempx0 = BigInteger.valueOf(1);
		BigInteger tempy0 = BigInteger.valueOf(0);
		BigInteger x1 = BigInteger.valueOf(0);
		BigInteger y1 = BigInteger.valueOf(1);
		BigInteger q = a.divide(b);
		BigInteger r = a.remainder(b);
		;
		while (!(r.equals(BigInteger.valueOf(0)))) {
			a = b;
			b = r;
			tempx0 = x0;
			tempy0 = y0;
			x0 = x1;
			y0 = y1;
			x1 = tempx0.subtract(q.multiply(x1));
			y1 = tempy0.subtract(q.multiply(y1));
			q = a.divide(b);
			r = a.remainder(b);
		}
		if (b.equals(BigInteger.valueOf(1))) {
			return true;
		}

		return false;
	}

	//calculates the multiplicative inverse of the integer inita in modulo initb
	public BigInteger multiplicativeInverse(BigInteger inita, BigInteger initb) {
		if (inita.compareTo(initb) == -1) {
			BigInteger temp = inita;
			inita = initb;
			initb = temp;
		}
		BigInteger a = inita;
		BigInteger b = initb;
		BigInteger x0 = BigInteger.valueOf(1);
		BigInteger y0 = BigInteger.valueOf(0);
		BigInteger tempx0 = BigInteger.valueOf(1);
		BigInteger tempy0 = BigInteger.valueOf(0);
		BigInteger x1 = BigInteger.valueOf(0);
		BigInteger y1 = BigInteger.valueOf(1);
		BigInteger q = a.divide(b);
		BigInteger r = a.remainder(b);
		;
		while (!(r.equals(BigInteger.valueOf(0)))) {
			a = b;
			b = r;
			tempx0 = x0;
			tempy0 = y0;
			x0 = x1;
			y0 = y1;
			x1 = tempx0.subtract(q.multiply(x1));
			y1 = tempy0.subtract(q.multiply(y1));
			q = a.divide(b);
			r = a.remainder(b);
		}

		return y1.mod(phiN);
	}

}
