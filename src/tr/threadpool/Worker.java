package tr.threadpool;

public class Worker implements Runnable {

    private volatile boolean stop = false;

    @Override
    public void run() {
        while (!stop){


        }
    }
    public void shutDown(){
        stop=true;
    }
}
