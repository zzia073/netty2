package com.study.decorator;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 11:15
 * 具体构建角色（相当于 FileInputStream (节点流，具体跟实际读取的内容相关，比如文件或缓存中直接读数据的流)）
 */
public class ConcreteComponent implements Component {
    @Override
    public void constructor(String something) {
        System.out.println("constructor " + something);
    }
}
