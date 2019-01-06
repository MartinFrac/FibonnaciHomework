package com.infoshareacademy.beans;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FibonacciCalculator {

    public List<Integer> getFibonacci(int n)  {

        List<Integer> fibonacciList = new ArrayList<>();

        fibonacciList.add(0);
        fibonacciList.add(1);

        for(int i = 2; i<=n; i++) {
            fibonacciList.add(fibonacciList.get(i-1)+fibonacciList.get(i-2));
        }
        return fibonacciList;
    }
}
