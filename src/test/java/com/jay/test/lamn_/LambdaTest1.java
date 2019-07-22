package com.jay.test.lamn_;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by JayJ on 2018/8/31.
 **/
public class LambdaTest1 {
    /**
     * Predicate接口
     * @param args
     */
    public static void main(String[] args){

        List<String> languages = Arrays.asList("Java", "html5","JavaScript", "C++", "hibernate", "PHP");
        //Predicate接口  从...开始
        //filter(languages,(name)->name.startsWith("J"));
        //判断字母的个数
        //filter(languages,(name)->name.length()==3);
        //filter(languages,(name)->"JavaScript".equals(name.concat("Script")));
        //filter(languages,(name)->name.endsWith("t"));
        //所有语言
        //filter(languages,(name)->true);
        /*Predicate<String> c1 = (name)->name.startsWith("J");
        Predicate<String> c2 = (name)->name.length()>4;
        filter(languages,c1.and(c2));*/
        //不是以... 开头的
        //Predicate<String> c3 = (name)->name.startsWith("J");
        //filter(languages,c3.negate());
        //名称为...
        filter(languages,Predicate.isEqual("Java"));

    }

    private static void filter(List<String> languages,Predicate<String> condition) {
        for(String name: languages) {
            if(condition.test(name)) {
                System.out.println(name + " is selected!");
            }
        }
    }
}
