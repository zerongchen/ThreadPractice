package tr.threadpool;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static final int MAX_WORK_NUMS=10;
    private static final int MIN_WORK_NUMS=1;
    private static final int DEFAULT_WORK_NUMS=5;

    LinkedList<Job> jobList = new LinkedList<>();

    List<Worker> workList = Collections.synchronizedList(new LinkedList<Worker>());

    private static int work_nums = DEFAULT_WORK_NUMS;

    private static AtomicInteger threadNum = new AtomicInteger();

    public DefaultThreadPool(){
        initWorks(DEFAULT_WORK_NUMS);
    }

    public DefaultThreadPool(int num)
    {
        work_nums=num>MAX_WORK_NUMS?MAX_WORK_NUMS:num<MIN_WORK_NUMS?MIN_WORK_NUMS:num;
        initWorks(work_nums);
    }
    private void initWorks(int defaultWorkNums) {
        for (int i=0;i<defaultWorkNums;i++){
            Worker worker =new Worker();
            workList.add(worker);
            Thread thread = new Thread(worker,"ThreadPool-Work-"+threadNum.incrementAndGet());
            thread.start();

        }
    }


    @Override
    public void excute(Job job) {
        if (job!=null){
            jobList.addLast(job);
            jobList.notify();
        }
    }

    @Override
    public void shutDown() {
       for (Worker worker:workList){
           worker.shutDown();
       }
    }

    @Override
    public void addWorks(int num) {

    }

    @Override
    public void removeWorks(int num) {

    }

    @Override
    public void getJobSize() {

    }

    class Worker implements Runnable {

        private volatile boolean stop = false;

        @Override
        public void run() {
            while (!stop){
                Job job=null;
                synchronized (jobList){
                    while (jobList.isEmpty()){
                        try {
                            jobList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    job=jobList.removeFirst();
                    if (job!=null){
                        try {
                            job.run();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }


            }
        }
        public void shutDown(){
            stop=true;
        }
    }

}
