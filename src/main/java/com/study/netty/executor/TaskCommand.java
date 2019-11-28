package com.study.netty.executor;

/**
 * @author ：fei
 * @date ：Created in 2019/11/27 0027 17:06
 */
public class TaskCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("command");
    }
}
