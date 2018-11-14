package tr;

import util.SleepUtil;

public class ThreadState {



    public static void main(String[] args){
        new Thread(new TimeWaiting(),"TimeWaiting").start();
        new Thread(new Waiting(),"Waiting").start();
        new Thread(new Block(),"block-1").start();
        new Thread(new Block(),"block-2").start();
    }

    //always sleep
    static class TimeWaiting implements Runnable{

        @Override
        public void run() {
            while (true){
                SleepUtil.sleep(100);
            }
        }
    }

    //always sleep in class
    static class Waiting implements Runnable{

        @Override
        public void run() {
            while (true){

                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Block implements Runnable{


        @Override
        public void run() {
            synchronized (Block.class){
                while (true){
                    SleepUtil.sleep(100);
                }
            }
        }
    }
}
