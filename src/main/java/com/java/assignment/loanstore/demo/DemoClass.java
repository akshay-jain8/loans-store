package com.java.assignment.loanstore.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemoClass {

    public void myList() {
        Map<String, String> result = new HashMap<>();
        List<String> listing = new ArrayList<>();
        listing.add("Akshay");
        listing.add("Jain");

        System.out.println(listing.stream().collect(Collectors.toMap(a-> a, a->a )));

    }

    public static void main(String[] args) {
        DemoClass demoClass = new DemoClass();
        demoClass.myList();
    }

}
