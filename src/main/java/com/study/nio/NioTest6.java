package com.study.nio;

import java.nio.IntBuffer;

/**
 * @author ：fei
 * @date ：Created in 2019/11/22 0022 14:29
 * Slice Buffer 与原有的数组共享底层数组
 */
public class NioTest6 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i);
        }
        buffer.position(2);
        buffer.limit(6);
        //slice方法截取从buffer的position到limit包含position不包含limit位置(因为limit是限制值可能会是capacity越界)
        IntBuffer sliceBuffer = buffer.slice();
        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            int b = sliceBuffer.get(i);
            sliceBuffer.put(i, b * 2);
        }
        buffer.clear();
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.get(i));
        }

    }
}
