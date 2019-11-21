package com.study.decorator;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 14:07
 */
public class ConcreteDecoratorForNumber extends Decorator {
    public ConcreteDecoratorForNumber(Component component) {
        super(component);
    }

    @Override
    public void constructor(String something) {
        lengthConstructor(something);
        super.constructor(something);
    }
    private void lengthConstructor(String something){
        System.out.println("constructor length " + something.length());
    }
}
