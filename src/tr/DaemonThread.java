package tr;

import util.SleepUtil;

/**
 * 设置Daemon 线程,如果jvm中的Daemon 线程没有，则自动退出JVm
 */
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
