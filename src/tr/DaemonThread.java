package tr;

import util.SleepUtil;

public class DaemonThread {
    public static void main(String[] args){
        Thread thread = new Thread(new DaemonRunable(),"DaemonRunable");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunable implements Runnable{


        @Override
        public void run() {
            try {
                SleepUtil.sleep(100);
            }finally {
                System.out.println("DaemonRunnable run");
            }
        }
    }
    class Dhead extends Thread{}
}
