package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{9, 9, 8, 8, 1, 1, 2, 3, 4, 5, 6}));
        System.out.println(oddOrEven(new ArrayList<>(List.of(1, 2, 3))));
        System.out.println(oddOrEven(new ArrayList<>(List.of(1, 1, 3))));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (x, y) -> 10 * x + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(Integer::sum).orElseThrow() ;
        return integers.stream()
                .filter(i -> i % 2 != sum % 2)
                .collect(Collectors.toList());
    }
}
