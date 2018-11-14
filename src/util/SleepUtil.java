package util;

import java.util.concurrent.TimeUnit;

public class SleepUtil
{

    public static void sleep(long secodes){
        try {
            TimeUnit.SECONDS.sleep(secodes);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
