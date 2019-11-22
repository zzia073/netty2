package com.study.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 16:01
 *
 */
public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i ++) {
            int randomNumber = new SecureRandom().nextInt(50);
            buffer.put(randomNumber);
        }
        System.out.println(buffer.get(3));
        buffer.flip();
        for (int i = buffer.position(); i < buffer.limit(); i ++){
            System.out.println(buffer.get());
        }
    }
}
