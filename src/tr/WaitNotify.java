package tr;

import util.SleepUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(),"waitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(),"notifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                while (flag){
                    System.out.println(Thread.currentThread()+" flag is true . wait@ "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread()+" flag is false . running@ "+new SimpleDateFormat("HH:mm:ss").format(new Date()));

            }
        }
    }
    static class Notify implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                System.out.println(Thread.currentThread()+" hold lock . notify@ "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
//                flag=false;
                SleepUtil.sleep(1);
            }
            synchronized (lock){
                System.out.println(Thread.currentThread()+" hold lock again. notify@ "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtil.sleep(2);
            }
            SleepUtil.sleep(2);
            synchronized (lock){
                System.out.println(Thread.currentThread()+" hold lock . notify@ "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag=false;
                SleepUtil.sleep(1);
            }
        }
    }
}
