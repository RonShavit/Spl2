package bgu.spl.mics;


import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args)
    {
        Future<Integer> f = new Future<>();
        Thread t= new Thread(()->longInsert(f));
        t.start();
        System.out.println(f.get(1600, TimeUnit.MILLISECONDS));
    }

    public static void longInsert(Future<Integer> f)
    {
        try
        {Thread.sleep(1500);} catch (Exception e) {
            throw new RuntimeException(e);
        }
        f.resolve(0);
    }
}
