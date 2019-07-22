package com.jay.test.lamn_;

import java.util.function.Function;

/**
 * Created by JayJ on 2018/8/31.
 **/
public class LambdaTest2 {

    /**
     * 静态内部类
     */
    private static class Student{
        String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    public static void main(String[] args){
        String name = "tony";
        Function<String,Student> f1 = (s)->new Student(s);
        //添加前缀
        Function<String,String> f2 = (s)->"CHN_"+s;
        //compose
        Student stu1 = f1.compose(f2).apply(name);
        System.out.println(stu1.getName());
    }
}
