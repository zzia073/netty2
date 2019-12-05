package com.study.netty.handler3;

/**
 * @author ：fei
 * @date ：Created in 2019/12/5 0005 10:10
 */
public class MyProtocol {
    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
