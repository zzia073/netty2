package com.study.nio;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ：fei
 * @date ：Created in 2019/11/27 0027 13:55
 */
public class ZeroCopyOldServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            byte[] byteArray = new byte[4096];
            int readCount;
            while ((readCount = dataInputStream.read(byteArray, 0, byteArray.length)) > 0) {
            }
            System.out.println("完成");
        }
    }
}
