package tr;

import java.util.concurrent.TimeUnit;

public class ShutDown {

    public static void main(String[] args) throws InterruptedException {
        ShutDownThread one =new ShutDownThread();
        Thread countThread = new Thread(one,"countThread-1");
        countThread.start();
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();

        ShutDownThread two = new ShutDownThread();
        countThread = new Thread(two,"countThread-2");
        countThread.start();
        TimeUnit.SECONDS.sleep(1);
        two.stop();
    }

    static class ShutDownThread implements Runnable{
        private long i;
        private volatile boolean on =true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("count " + i);
        }
        public void stop(){
            on=false;
        }
    }
}
