package com.study.decorator;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 11:46
 */
public class Test {
    public static void main(String[] args) {
        ConcreteComponent concreteComponent = new ConcreteComponent();

        concreteComponent.constructor("1234567");
        System.out.println("====================");

        ConcreteDecoratorForDate concreteDecoratorForDate = new ConcreteDecoratorForDate(new ConcreteComponent());

        concreteDecoratorForDate.constructor("2019-11-21");
        System.out.println("====================");

        ConcreteDecoratorForNumber concreteDecoratorForNumber = new ConcreteDecoratorForNumber(new ConcreteComponent());

        concreteDecoratorForNumber.constructor("2019-11-21");
        System.out.println("====================");

        ConcreteDecoratorForDate concreteDecoratorForDateAndNumber = new ConcreteDecoratorForDate(
                new ConcreteDecoratorForNumber(new ConcreteComponent()));

        concreteDecoratorForDateAndNumber.constructor("2019-11-21");
        System.out.println("====================");

        ConcreteDecoratorForNumber concreteDecoratorForNumberAndDate = new ConcreteDecoratorForNumber(
                new ConcreteDecoratorForDate(new ConcreteComponent()));

        concreteDecoratorForNumberAndDate.constructor("2019-11-21");

    }
}
