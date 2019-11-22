package com.study.nio;

import java.nio.IntBuffer;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 17:35
 *
 * 初始化一个IntBuffer
 *
 * index    0      1      2      3      4     length(5)
 *      ||=====||=====||=====||=====||=====||
 *      ||     ||     ||     ||     ||     ||
 *      ||=====||=====||=====||=====||=====||
 *          ↑                                    ↑
 *      position                          (capacity|limit)
 *
 *  执行put(3);put(7);put(6);put(2);每次put 放入position位置 然后position ++ 其他位置不变
 *
 *  index    0      1      2      3      4     length(5)
 *      ||=====||=====||=====||=====||=====||
 *      ||  3  ||  7  ||  6  ||  2  ||     ||
 *      ||=====||=====||=====||=====||=====||
 *                                      ↑        ↑
 *                                  position(capacity|limit)
 *
 *  执行flip();准备读 limit = position; position = 0; mark = -1;
 *
 *  index   0      1      2      3      4     length(5)
 *      ||=====||=====||=====||=====||=====||
 *      ||  3  ||  7  ||  6  ||  2  ||     ||
 *      ||=====||=====||=====||=====||=====||
 *          ↑                           ↑        ↑
 *       position                     limit  (capacity)
 *
 *  执行get();get();get();get(); 每次get 从position位置取 然后position ++ 其他位置不变
 *
 *  index    0      1      2      3      4     length(5)
 *      ||=====||=====||=====||=====||=====||
 *      ||  3  ||  7  ||  6  ||  2  ||     ||
 *      ||=====||=====||=====||=====||=====||
 *                                      ↑         ↑
 *                            (position|limit)(capacity)
 *
 * 综上步骤，如果一个初始化之后的buffer调用了flip那么该buffer将不能进行读写操作，
 * clear方法可以重置buffer为最初初始化完之后的状态 position = 0; limit = capacity; mark = -1;
 */
public class NioTestBufferField {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);
        System.out.println("初始后：");
        System.out.println("capacity : " + buffer.capacity());
        System.out.println("limit : " + buffer.limit());
        System.out.println("position : " + buffer.position());
        buffer.put(3);
        buffer.put(7);
        buffer.put(6);
        buffer.put(2);
        System.out.println("=================================");
        System.out.println("写入后：");
        System.out.println("capacity : " + buffer.capacity());
        System.out.println("limit : " + buffer.limit());
        System.out.println("position : " + buffer.position());
        System.out.println("=================================");
        buffer.flip();
        System.out.println("翻转后：");
        System.out.println("capacity : " + buffer.capacity());
        System.out.println("limit : " + buffer.limit());
        System.out.println("position : " + buffer.position());
        System.out.println("=================================");
        while (buffer.hasRemaining()){
            buffer.get();
        }
        System.out.println("读取后：");
        System.out.println("capacity : " + buffer.capacity());
        System.out.println("limit : " + buffer.limit());
        System.out.println("position : " + buffer.position());
    }
}
