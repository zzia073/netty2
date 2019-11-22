package com.study.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author ：fei
 * @date ：Created in 2019/11/22 0022 16:23
 */
public class NioTestFileLock {
    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("NioTestFileLock.txt", "rw");
        FileChannel fileChannel = file.getChannel();
        FileLock fileLock = fileChannel.lock(3, 6, true);
        //是否有效
        System.out.println("valid: " + fileLock.isValid());
        //排它锁还是共享锁
        System.out.println("lock type: " + fileLock.isShared());

        fileLock.release();;
        file.close();

    }
}
