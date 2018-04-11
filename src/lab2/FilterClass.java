package lab2;
import java.lang.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
public class FilterClass implements Runnable {

    private ExecutorService filterCrew;
    private Runnable nextFilter;
   // private Thread nextFilter;

    private int primeNumber;
    private BlockingQueue<Integer> myQueue;
    private BlockingQueue<Integer> previousQueue;
    private int queueSize;


    FilterClass(int assignedPrimeNumber, int bufferSize, BlockingQueue<Integer>queueToBeConsumed, ExecutorService filterCrew)
    {
        this.primeNumber=assignedPrimeNumber;
        this.previousQueue=queueToBeConsumed;
        this.queueSize=bufferSize;
        this.filterCrew=filterCrew;
        //System.out.println("Creo per "+primeNumber);
    }

    public void run()
    {
        boolean uscita=false;
        int numberUnderExamination=0;
        while (!uscita) {
                try {
                    numberUnderExamination = this.previousQueue.take();
                    //System.out.println(numberUnderExamination);
                    if(numberUnderExamination<0)
                    {
                       uscita=true;
                    }
                    else {
                        if ((numberUnderExamination % this.primeNumber) != 0) {
                            if (this.nextFilter == null) {
                                this.myQueue = new LinkedBlockingQueue<>();
                                this.nextFilter=new FilterClass(numberUnderExamination,this.queueSize,myQueue,filterCrew);
                                filterCrew.execute(nextFilter);
                                //nextFilter = new Thread(new FilterClass(numberUnderExamination, this.queueSize, this.myQueue));nextFilter.start();
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
