package lab2;
import java.lang.*;
import java.util.concurrent.ArrayBlockingQueue;

public class CrivelloEratostene {
    public static void main(String [] args)
    {
        if(args.length!=2)
        {
            System.out.println("args: lastNumberToBeAnalyzed bufferSize");
        }
        int n_max=Integer.parseInt(args[0]);
        int filterClassBufferSize=Integer.parseInt(args[1]);
        ArrayBlockingQueue<Integer>myQueue=new ArrayBlockingQueue<>(filterClassBufferSize);
        Thread headFilters=new Thread(new FilterClass(2,filterClassBufferSize,myQueue));
        headFilters.start();

        try {
            for(int currentNumber=3;currentNumber<=n_max;currentNumber++)
            {
                myQueue.put(new Integer(currentNumber));
                //System.out.println("passing number "+currentNumber);
            }
            //System.out.println("Stopping Threads...");

            myQueue.put(new Integer(-1));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
