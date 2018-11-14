package tr;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 线程管理JMX
 */
public class MutiThread {

    public static void main(String[] args){
        //线程管理JMX
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos= threadMXBean.dumpAllThreads(false,false);

        for (ThreadInfo info:threadInfos){
            System.out.println(info.getThreadId()+":"+info.getThreadName());
        }
    }
}
