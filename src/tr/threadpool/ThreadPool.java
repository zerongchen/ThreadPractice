package tr.threadpool;

public interface ThreadPool<Job extends  Runnable> {
    void excute(Job job);

    void shutDown();

    void addWorks(int num);

    void removeWorks(int num);

    void getJobSize();
}
