package tr.threadpool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 连接池
 */
public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initiaSize){
        if (initiaSize>0){
            for (int i=0;i<initiaSize;i++){
                pool.add(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection){
        if (connection!=null){
            synchronized (connection){
                pool.addLast(connection);
                pool.notify();
            }
        }
    }

    /**
     * 规定时间没有获取连接，返回null
     * @param mills
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConnection(int mills) throws InterruptedException {
        synchronized (pool){
            if (mills<0){
                if (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else {
                long future = System.currentTimeMillis()+mills;
                long remain = mills;
                while (pool.isEmpty() && remain>0){
                    pool.wait(remain);
                    remain = future-System.currentTimeMillis();
                }
                if (!pool.isEmpty()){
                    return pool.removeFirst();
                }
                return null;
            }
        }
    }
}
