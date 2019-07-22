package com.jay.test;


import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by JayJ on 2018/8/30.
 **/
public class java8strem {
    public static void main(String[] args){
       /* IntStream intStream = IntStream.of(1,2,3,4,5,6,7,8,9,10);
        intStream.filter(i->i%2==0).limit(10).forEach(System.out::println);*/
        List<String> list = Arrays.asList("a","2","3","4","5");
        //List l1 = Lists.newArrayList();
        //list.stream().map(a->"map"+a).forEach(System.out::println);
        Stream lines = list.stream();
        lines.flatMap(line->Arrays.stream(line.toString().split(""))).distinct().count();


    }
}
