package tr.threadpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {
    static ConnectionPool pool = new ConnectionPool(10);
    static CountDownLatch start = new CountDownLatch(1);
    static CountDownLatch end = null;

    public static void main(String[] args) throws InterruptedException {
        end = new CountDownLatch(10);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i=0;i<10;i++){
            Thread thread = new Thread(new ConnectionRunner(count,got,notGot),"connectionRunnableThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke :"+10*count);
        System.out.println("got connection :"+got.get());
        System.out.println("not got connection :"+notGot.get());

    }


    static class ConnectionRunner implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;
        public ConnectionRunner(int count,AtomicInteger got,AtomicInteger notGot){
            this.count=count;
            this.got=got;
            this.notGot=notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (count>0){
                try {
                    Connection connection = pool.fetchConnection(100);
                    if (connection!=null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        notGot.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
