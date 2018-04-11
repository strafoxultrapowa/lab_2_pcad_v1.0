package lab2;
import java.lang.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;

public class FilterClass implements Runnable {

    //private ExecutorService filterCrew;
    //private Runnable nextFilter;
    private Thread nextFilter;

    private int primeNumber;
    private ArrayBlockingQueue<Integer> myQueue;
    private ArrayBlockingQueue<Integer> previousQueue;
    private int queueSize;


    FilterClass(int assignedPrimeNumber, int bufferSize, ArrayBlockingQueue<Integer>queueToBeConsumed/*, ExecutorService filterCrew*/)
    {
        this.primeNumber=assignedPrimeNumber;
        this.previousQueue=queueToBeConsumed;
        this.queueSize=bufferSize;
        //this.filterCrew=filterCrew;
        //System.out.println("Creo per "+primeNumber);
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
                                //filterCrew.execute(new FilterClass(numberUnderExamination,this.queueSize,myQueue,filterCrew));
                                nextFilter = new Thread(new FilterClass(numberUnderExamination, this.queueSize, this.myQueue));nextFilter.start();
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
