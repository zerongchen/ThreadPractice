package tr;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道输入输出的应用
 */
public class PrintThread {
    public static void main(String[] args) throws IOException {
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();
        //输入流和输出流的链接,否则抛出IOException
        writer.connect(reader);
        new Thread(new Print(reader),"PrintThread").start();
        int re=0;
        try {
            while ((re=System.in.read())!=-1){
                writer.write(re);
            }
        }catch (Exception e){

        }finally {
            writer.close();
        }

    }

    static class Print implements Runnable{

        private PipedReader in;
        public Print(PipedReader in){
            this.in=in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive=in.read())!=-1){
                    System.out.println((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
