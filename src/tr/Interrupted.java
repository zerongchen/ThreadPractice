package tr;

import util.SleepUtil;

import java.util.concurrent.TimeUnit;

public class Interrupted {

    public static void main(String[] args)  {
        Thread sleepThread   = new Thread(new SleepRunnable(),"sleepThread ");
        sleepThread.setDaemon(true);

        Thread busyThread  = new Thread(new BusyRunnable(),"BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println("--"+e);
        }

        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread interrupt is "+sleepThread.isInterrupted());
        System.out.println("busyThread interrupt is "+busyThread.isInterrupted());

        SleepUtil.sleep(2);
    }
    //sleep
    static class SleepRunnable implements Runnable{
        @Override
        public void run() {
            while (true){
                SleepUtil.sleep(10);
            }
        }
    }

    //aways run
    static class BusyRunnable implements  Runnable{
        @Override
        public void run() {
            while (true){

            }
        }
    }
}
