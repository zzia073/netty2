package com.study.decorator;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 11:20
 */
public class Decorator implements Component {
    protected Component component;
    public Decorator(Component component){
        this.component = component;
    }
    @Override
    public void constructor(String something){
        component.constructor(something);
    }
}
