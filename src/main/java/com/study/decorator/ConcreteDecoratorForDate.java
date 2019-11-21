package com.study.decorator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 11:22
 */
public class ConcreteDecoratorForDate extends Decorator {
    public ConcreteDecoratorForDate(Component component){
        super(component);
    }
    @Override
    public void constructor(String something) {
        weekConstructor(something);
        component.constructor(something);
    }
    private void weekConstructor(String date){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println("constructor week " + dayOfWeek.name());
    }

}
