package com.study.netty.bytebuf;


import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author ：fei
 * @date ：Created in 2019/12/2 0002 11:30
 */
public class AtomicIntegerTest {
    public static void main(String[] args) {
        Person person = new Person();
        AtomicIntegerFieldUpdater<Person> atomicIntegerFieldUpdater =
                AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
               try {
                   Thread.sleep(20);
               } catch (Exception e) {

               }
                System.out.println(atomicIntegerFieldUpdater.getAndIncrement(person));
            }).start();
        }
    }
}
class Person {
    volatile int age = 1;
}
