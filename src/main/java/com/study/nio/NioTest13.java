package com.study.nio;

import io.netty.util.CharsetUtil;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.stream.Stream;

/**
 * @author ：fei
 * @date ：Created in 2019/11/26 0026 10:45
 */
public class NioTest13 {
    public static void main(String[] args) throws Exception {
        //文件的存储是用UTF-8存储的
        String inputFile = "NioTest13_In.txt";
        String outputFile = "NioTest13_Out.txt";
        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");
        long length = inputRandomAccessFile.length();
        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);
        Charset charset = CharsetUtil.ISO_8859_1;
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();
        //用ISO8859-1 解码之后是乱码的
        CharBuffer charBuffer = decoder.decode(inputData);
        Stream.of(charBuffer.array()).forEach(System.out::println);
        ByteBuffer outputData = encoder.encode(charBuffer);

        outputFileChannel.write(outputData);
        inputRandomAccessFile.close();
        outputRandomAccessFile.close();

    }
}
