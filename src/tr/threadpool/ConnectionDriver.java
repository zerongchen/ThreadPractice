package tr.threadpool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class ConnectionDriver {

    public static Connection createConnection(){
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),new Class<?>[]{Connection.class},new ConnectionHandler());
    }
    static class ConnectionHandler implements InvocationHandler{

        @Override
        public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
            if (method.getName().equals("commit")){
                TimeUnit.SECONDS.sleep(1000);
            }
            return null;
        }
    }
}
