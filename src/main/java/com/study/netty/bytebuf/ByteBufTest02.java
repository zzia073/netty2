package com.study.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author ：fei
 * @date ：Created in 2019/11/29 0029 17:19
 * UTF-8 编码规则 就是所有都是0101编码后的二进制存储方式会是怎样的，而又是怎样去读这些二进制解码识别几位算一个字符的？
 * 汉字在 U+0800 - U+FFFF 因此是三位表示一个汉字。
 * last code point的位数
 * 比如U+007F二进制 1111111 7个1表示最大值
 * Bits of code point    First code point    Last code point    Bytes in sequence    Byte 1      Byte 2      Byte 3      Byte 4      Byte 5      Byte 6
 *     7(最高7个1）           U+0000               U+007F                1           0xxxxxxx
 *     11                    U+0080               U+07FF                2           110xxxxx    10xxxxxx
 *     16                    U+0800               U+FFFF                3           1110xxxx    10xxxxxx    10xxxxxx
 *     21                    U+10000              U+1FFFFF              4           11110xxx    10xxxxxx    10xxxxxx    10xxxxxx
 *     26                    U+200000             U+3FFFFFF             5           111110xx    10xxxxxx    10xxxxxx    10xxxxxx    10xxxxxx
 *     31                    U+4000000            U+7FFFFFFF            6           1111110x    10xxxxxx    10xxxxxx    10xxxxxx    10xxxxxx    10xxxxxx
 *
 *     所有的x表示可用字符编码对应于Unicode码对应值
 */
public class ByteBufTest02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("龙hello world",CharsetUtil.UTF_8);
        //如果返回真则是堆上的缓存，用字节数组存储的，就可以调用它的array方法
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            System.out.println(new String(content, CharsetUtil.UTF_8));
            //真实的类型
            System.out.println(byteBuf);
            int readableBytes = byteBuf.readableBytes();
            System.out.println(readableBytes);
            for (int i = 0; i < readableBytes; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }
            System.out.println(byteBuf.getCharSequence(0, 4, CharsetUtil.UTF_8));
            System.out.println(byteBuf.getCharSequence(4, 6, CharsetUtil.UTF_8));
        }
    }
}
