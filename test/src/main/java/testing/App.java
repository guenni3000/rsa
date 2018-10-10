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

    public static BigDecimal[] getPQ(BigDecimal dg, BigDecimal k, MathContext mc) throws Exception
    {
        BigDecimal[] res = new BigDecimal[2];

        BigDecimal phi = e.multiply(dg).divideToIntegralValue(k);
        if(phi.intValue() == 0) throw new Exception();
        BigDecimal g = e.multiply(dg).remainder(k);

        BigDecimal test = n.subtract(phi).add(BigDecimal.ONE).divide(BigDecimal.valueOf(2.0));
        System.out.println(test);
        if(test.doubleValue() != test.intValue())
        {
            throw new Exception();
        }

        res[0] = test.multiply(test).subtract(n).sqrt(mc).add(test);
        res[1] = n.divide(res[0], mc);
        return res;
    }

    public static void main( String[] args )
    {
        MathContext mc = new MathContext(500);
     
        String n_hex = "c2cbb24fdbf923b61268e3f11a3896de4574b3ba58730cbd652938864e2223eeeb704a17cfd08d16b46891a61474759939c6e49aafe7f2595548c74c1d7fb8d24cd15cb23b4cd0a3";//bigRand(100);
        String e_hex = "1001";
        BigInteger eI = new BigInteger(e_hex, 16);
        BigInteger nI = new BigInteger(n_hex, 16);

        n = new BigDecimal(nI, 0);
        e = new BigDecimal(eI, 0);

        BigDecimal rem = n.remainder(e, mc);
        BigDecimal div = e;
        BigDecimal remOld;

        BigDecimal[] vals = new BigDecimal[100];
        vals[0] = n.divideToIntegralValue(e);
        System.out.println(vals[0]);
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
                BigDecimal div1 = ((i+1) % 2 == 0)?vals[i].add(BigDecimal.ONE):vals[i];
                BigDecimal div2 = BigDecimal.ONE;
            
                for(int u = i-1; u >= 0; u--)
                {
                    BigDecimal divOld = div1;
                    div1 = vals[u].multiply(div1).add(div2);
                    div2 = divOld;
                }
                
                System.out.println(div2+"/"+div1);
                try {
                    //BigDecimal[] pq = getPQ(div1, div2, mc);
                    if(BigInteger.TWO.modPow(div1.multiply(e).toBigInteger(), n.toBigInteger()).intValue() == 2)
                    {
                        System.out.println("------"+div1);
                    }
                    //System.out.println(pq[0]+", "+pq[1]);
                } catch(Exception e) {
                    System.out.println("No factors!");
                }
            }
        }

    }
}
