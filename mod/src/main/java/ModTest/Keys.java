package ModTest;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class Keys
{
    public static int gcd(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }

    public static BigInteger getExponent(BigInteger phi) {
        for (int i = 2; i < phi.intValue() + 1; i++) {
            if (phi.gcd(BigInteger.valueOf(i)).intValue() == 1) return BigInteger.valueOf(i);
        }

        return BigInteger.valueOf(-1);
    }

    public static BigInteger getPrivate(BigInteger exp, BigInteger phi) {
        if (phi.compareTo(exp) == 1) return App.res(phi, exp, phi.subtract(BigInteger.ONE)).multiply(phi).subtract(phi.subtract(BigInteger.ONE)).divide(exp);
        else return App.res(exp, phi, BigInteger.ONE);
    }
}
