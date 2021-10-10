package com.app.snacksstore.utils;

import com.app.snacksstore.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Common {
    public static int getImageID(int id) {
        Class<?> drawable = R.drawable.class;
        Field field;
        try {
            field = drawable.getField("snacks_image_" + id);
            return field.getInt(field.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Integer> calculateBudget(double budget, List<Double> list) {
        list.sort(Double::compareTo);
        double sum = list.stream().mapToDouble(Double::valueOf).sum();
        if (sum <= 0) return null;
        int baseCount = (int) Math.floor(budget / sum);
        List<Integer> countList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); ++i) {
            countList.add(baseCount);
        }
        sum = budget - baseCount * sum;
        double val;
        for (int i = 0; i < list.size(); ++i) {
            val = list.get(i);
            if (sum - val < 0) break;
            sum -= val;
            countList.set(i, countList.get(i) + 1);
        }
        return countList;
    }
}
