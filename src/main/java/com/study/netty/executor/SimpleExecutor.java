package com.study.netty.executor;

import java.util.concurrent.Executor;

/**
 * @author ：fei
 * @date ：Created in 2019/11/27 0027 17:07
 * Executors 提供了很多方法去实现Executor
 * 如果直接在excute中调用
 */
public class SimpleExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }

    public static void main(String[] args) {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        simpleExecutor.execute(new TaskCommand());
    }
}
