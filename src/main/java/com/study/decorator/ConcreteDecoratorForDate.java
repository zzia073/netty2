package com.study.decorator;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 11:22
 */
public class ConcreteDecorator extends Decorator {
    public ConcreteDecorator(Component component){
        super(component);
    }
    @Override
    public void constructor(String something) {
        System.out.println("decorator " + something);
        component.constructor(something);
    }

}
