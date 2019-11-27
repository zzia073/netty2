package com.study.nio;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * @author ：fei
 * @date ：Created in 2019/11/27 0027 14:11
 */
public class ZeroCopyOldClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(8899));
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        FileInputStream inputStream = new FileInputStream("E:/Photoshop_CS6.zip");
        byte[] byteArr = new byte[4096];
        long n = System.nanoTime();
        while (true) {
            int read = inputStream.read(byteArr, 0, byteArr.length);
            if (read <= 0) {
                break;
            }
            outputStream.write(byteArr,0,read);
        }
        long m = System.nanoTime();
        System.out.println("耗时：" + (m-n));
        inputStream.close();
        outputStream.close();
    }
}
