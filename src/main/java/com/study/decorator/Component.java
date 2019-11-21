package com.study.decorator;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 11:13
 * 装饰者模式的抽象构建角色
 * 相当于InputStream（他是个抽象类，java没有多继承设计成抽象类不让一个流既是输出流又是输入流）
 */
public interface Component {
    /**
     * 构建一个东西
     */
    void constructor(String something);
}
