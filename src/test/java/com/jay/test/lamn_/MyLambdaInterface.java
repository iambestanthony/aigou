package com.jay.test.lamn_;

/**
 * Created by JayJ on 2018/8/30.
 **/
@FunctionalInterface
public interface MyLambdaInterface {
    void doSomething(String s);
    default void check(){
        System.out.println("java8真的可以在接口中写实现");;
    }
}
