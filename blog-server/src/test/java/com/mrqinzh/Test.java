package com.mrqinzh;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {

        List<Class> list = new ArrayList<>();
        list.add(MergeSort.class);
        list.add(Test.class);

        MergeSort mergeSort = new MergeSort();

        List<Integer> list1 = mergeSort.list;
        Class<Test> testClass = Test.class;

        testClass.getConstructor().newInstance();

        for (Class aClass : list) {

            List<? extends Class> list2 = new ArrayList<>();

        }

    }

}
