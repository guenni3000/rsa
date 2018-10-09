package testing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static BigDecimal one = new BigDecimal(1.0);
    public static BigDecimal n, e;

    public static String bigRand(int size) 
    {
        String res = "";
        for(int i = 0; i < size; i++)
        {
            res += hex[(int)(Math.random() * hex.length)];
        }

        return res;
    }

    public static BigDecimal[] getPQ(BigDecimal phi, MathContext mc)
    {
        BigDecimal[] res = new BigDecimal[2];
        BigDecimal b = n.subtract(phi).add(BigDecimal.ONE).negate();
        BigDecimal rt = b.pow(2).subtract(n.multiply(BigDecimal.valueOf(4.0))).sqrt(mc);

        res[0] = b.negate().add(rt).divide(BigDecimal.valueOf(2.0));
        res[1] = b.negate().subtract(rt).divide(BigDecimal.valueOf(2.0));

        return res;
    }

    public static BigDecimal getPhi(BigDecimal d, BigDecimal k)
    {
        return e.multiply(d).subtract(BigDecimal.ONE).divide(k);
    }

    public static void main( String[] args )
    {
        MathContext mc = new MathContext(120);
     
        String n_hex = bigRand(100);
        BigInteger eI = new BigInteger("10001", 16);
        BigInteger nI = new BigInteger(n_hex, 16);

        n = new BigDecimal(nI, 0);
        e = new BigDecimal(eI, 0);
        
        System.out.println(n);
        System.out.println(e);

        BigDecimal rem = n.remainder(e, mc);
        BigDecimal div = e;
        BigDecimal remOld;

        BigDecimal[] vals = new BigDecimal[100];
        vals[0] = n.divideToIntegralValue(e);
        int ind = 1;

        while(rem.intValue() != 0)
        {
            remOld = rem;
            vals[ind] = div.divideToIntegralValue(rem, mc);
            System.out.println(vals[ind]);
            ind++;
            rem = div.remainder(rem);
            div = remOld;
        }

        System.out.println();

        for(int i = 0; i < 100; i++)
        {   
            if(vals[i] != null)
            {
                BigDecimal div1 = vals[i];
                BigDecimal div2 = BigDecimal.ONE;
            
                for(int u = i-1; u >= 0; u--)
                {
                    BigDecimal divOld = div1;
                    div1 = vals[u].multiply(div1).add(div2);
                    div2 = divOld;
                }
                
                System.out.println(div2+"/"+div1);
                try {
                    BigDecimal[] pq = getPQ(getPhi(div1, div2), mc);
                    System.out.println(pq[0]+",  "+pq[1]);
                } catch(Exception e) {
                    System.out.println("No factors!");
                }
            }
        }

    }
}
