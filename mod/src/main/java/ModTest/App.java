package ModTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static HashMap<Integer, ArrayList<Integer[]>> pers = new HashMap<>();

    public static void checkRSACreation() {
        BigInteger p = new BigInteger("3"), q = new BigInteger("5");
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger exp = Keys.getExponent(phi);
        BigInteger d = Keys.getPrivate(exp, phi);

        System.out.println("p: "+p+", q: "+q+", exp: "+exp+", d: "+d);

        BigInteger t = BigInteger.TWO;
        BigInteger n = p.multiply(q);
        t = t.modPow(d.multiply(exp), n);

        System.out.println(t);

    }

    public static int getPrime(int index) {
    
        int i = 0;
        int x = 0;

        while (i < index) {
            x++;

            boolean isPrime = true;
            for (int u = 2; u <= Math.sqrt(x); u++) {
                if (x % u == 0){
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                i++;
            }
        }

        return x;
    } 

    public static void main(String[] args) {
        for (int i = 3; i < 10; i++) {
            for (int u = i; u < 10; u++) {
                if (i != u) checkPrimes(BigInteger.valueOf(getPrime(i)), BigInteger.valueOf(getPrime(u)));
            }
        }
    }

    public static void checkPrimes(BigInteger p, BigInteger q)
    {
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger exp = Keys.getExponent(phi);
        BigInteger d = Keys.getPrivate(exp, phi);

        System.out.println("p: "+p+", q: "+q+", exp: "+exp+", d: "+d);

        BigInteger nStart = BigInteger.TWO.pow(exp.intValue());
        BigInteger o = q.multiply(p);
        BigInteger start = BigInteger.TWO;

        int point = 0;
        int period = 0, per0 = 0;

        for(int i = 0; i < d.intValue(); i++)
        {
            if (nStart.compareTo(o) == 1) {
                start = res(nStart, o, start);
            } else {
                start = res(o, nStart, o.subtract(start)).multiply(o).subtract(o.subtract(start)).divide(nStart);
            }

            BigInteger count = o.subtract(nStart.mod(o));
            BigInteger rem = o.mod(count);
            o = o.divide(rem.gcd(count));

            point++;

            if (start.intValue() == 1){
                if (period == 0) {
                    period = point;
                    per0 = point;
                } else {
                    period = point;
                }
                point = 0;
            }

            //System.out.println(start+" + "+o+" * x,  "+(d.intValue() - 1 - i));

            if (start.intValue() == -1 || i == d.intValue()) 
            {
                System.out.println(-1);
                break;
            }
        }

        Integer[] vals = {per0, period, d.intValue()};
        if (pers.containsKey(per0)) pers.get(per0).add(vals);
        else {
            pers.put(per0, new ArrayList<Integer[]>());
            pers.get(per0).add(vals);
        }
        System.out.println(per0+",  "+period);
    }

    public static BigInteger res(BigInteger n, BigInteger p, BigInteger start)
    {
        BigInteger count, rem;
        ArrayList<BigInteger[]> steps = new ArrayList<BigInteger[]>();
        BigInteger[] iArr = {n, p, start};
        steps.add(iArr.clone());

        do{
            if(p.intValue() == 0) return BigInteger.valueOf(-1);
            
            count = p.subtract(n.mod(p));
            rem = p.mod(count);
            start = n.subtract(start.mod(n)).subtract(n.subtract(start.mod(n)).divide(p).multiply(p));
            n = count;
            p = rem;

            iArr[0] = n;
            iArr[1] = p;
            iArr[2] = start;

            steps.add(iArr.clone());
        } while(n.intValue() != 1 && start.mod(count).intValue() != 0);

        BigInteger l = start.divide(count).add(BigInteger.ONE);

        for(int i = steps.size()-2; i > 0; i--)
        {
            BigInteger[] valsOld = steps.get(i);
            //System.out.println(valsOld[0]+",  "+valsOld[1]+",  "+valsOld[2]);
            BigInteger[] vals = steps.get(i-1);
            BigInteger k = valsOld[0].multiply(l).subtract(valsOld[2].mod(valsOld[0])).divide(valsOld[1]);
            l = vals[1].multiply(k).add(valsOld[2]).divide(valsOld[0]).add(BigInteger.ONE);
        }
        
        //int i = (l*nStart-2)%pStart;
        return(l);
    }
}
