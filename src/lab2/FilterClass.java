package lab2;
import java.lang.*;
import java.util.concurrent.ArrayBlockingQueue;
public class FilterClass implements Runnable {

    private int primeNumber;
    private Thread nextFilter;
    private ArrayBlockingQueue<Integer> myQueue;
    private ArrayBlockingQueue<Integer> previousQueue;
    private int queueSize;


    FilterClass(int assignedPrimeNumber,int bufferSize,ArrayBlockingQueue<Integer>queueToBeConsumed)
    {
        this.primeNumber=assignedPrimeNumber;
        this.previousQueue=queueToBeConsumed;
        this.queueSize=bufferSize;
    }

    public void run()
    {
        boolean uscita=false;
        int numberUnderExamination=0;
        while (!uscita) {
                try {
                    numberUnderExamination = this.previousQueue.take();
                    if(numberUnderExamination<0)
                    {
                       uscita=true;
                    }
                    else {
                        if ((numberUnderExamination % this.primeNumber) != 0) {
                            if (this.nextFilter == null) {
                                this.myQueue = new ArrayBlockingQueue<Integer>(this.queueSize);
                                nextFilter = new Thread(new FilterClass(numberUnderExamination, this.queueSize, this.myQueue));
                                nextFilter.start();
                            } else {
                                this.myQueue.put(new Integer(numberUnderExamination));
                            }
                        }
                    }
                } catch (InterruptedException e) {e.printStackTrace(); }

            }

            System.out.println(primeNumber);
            try {
                if (this.nextFilter != null)
                    this.myQueue.put(new Integer(-1));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
    }


}
