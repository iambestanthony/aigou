package com.jay.test.lamn_;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.util.*;

/**
 * Created by JayJ on 2018/8/30.
 **/
public class La1 implements Comparator<User> {


    public static void main(String[] args) {
        /*ArrayList<User> ulist = Lists.newArrayList();
        ulist.add(new User("zl",17));
        ulist.add(new User("zs",14));
        ulist.add(new User("li",15));
        ulist.add(new User("ww",16));
        //Collections.sort(ulist,new La1());
        Collections.sort(ulist,(u1,u2)->u2.getAge()-u1.getAge());
        System.out.println(ulist);*/
        //lambda表达式实现接口 很简洁
        //MyLambdaInterface lambdaInterface = (s)-> System.out.println(s);
        //lambdaInterface.doSomething("helloworld");


        /*say((s -> System.out.println(s)),"hello world");
        Thread thread = new Thread(()->System.out.println("hello "));
        thread.start();*/

        //排序
        List<String> str1 = Arrays.asList(new String[]{"b", "g", "c", "a"});
        //通过compareTo方法
        Collections.sort(str1,(s1,s2)->s1.compareTo(s2));
        //遍历
        System.out.println(str1);
        //lambda表达式无法访问接口的默认方法default；

        MyLambdaInterface lambdaInterface = La1::say;
        lambdaInterface.doSomething("luosha");

    }

    private static void say(String s) {
        System.out.println(s+"nishizhu");
    }

    public static void say(MyLambdaInterface lambdaInterface,String something){
        lambdaInterface.doSomething(something);
    }


    @Override
    public int compare(User o1, User o2) {
        return o1.getAge() - o2.getAge();
    }
}
