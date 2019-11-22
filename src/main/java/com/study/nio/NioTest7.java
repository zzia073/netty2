package com.study.nio;

import java.nio.IntBuffer;

/**
 * @author ：fei
 * @date ：Created in 2019/11/22 0022 14:46
 * 我们可以把一个普通的buffer转换成readonlyBuffer不可以从只读的变成一个读写的，
 * 就是为了给别人一个只读的不能修改，他不可以把只读的改成能读写的。
 */
public class NioTest7 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i ++){
            buffer.put(i);
        }
        IntBuffer readonlyBuffer = buffer.asReadOnlyBuffer();
        //会抛出异常，不可以修改
        readonlyBuffer.put(3);
    }
}
