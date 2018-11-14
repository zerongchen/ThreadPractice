package tr;

import util.SleepUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Deprecated {

    public static void main(String[] args) throws InterruptedException {
        DateFormat  format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Thread printRunner  = new Thread(new Runner(),"printThread");
        printRunner.setDaemon(true);
        printRunner.start();
        TimeUnit.SECONDS.sleep(3);
        printRunner.suspend();
        System.out.println("main suspend printThread at "+format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
        printRunner.resume();
        System.out.println("main resume printThread at "+format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
        printRunner.stop();
        System.out.println("main stop printThread at "+format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

    }


    static class Runner implements Runnable{

        @Override
        public void run() {
            DateFormat  format = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

            while(true){
                System.out.println(Thread.currentThread().getName()+" run at "+format.format(new Date()));
                SleepUtil.sleep(1);
            }
        }
    }
}
