package com.example.spotted.utils;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generate random colors from pre-defined list
 */
public class RandomColorGenerator {

    public List<String> colors;
    private Random r;

    public RandomColorGenerator() {
        colors = new ArrayList<>();
        Collections.addAll(colors, "#ce93d8", "#90caf9", "#b64fc8", "#4d82cb", "#ff80ab", "#9d46ff",
                "#b39ddb", "#81d4fa", "#805acb", "#49a7cc", "#ea80fc", "#bc477b",
                "#9fa8da", "#80cbc4", "#5870cb", "#75ccb9", "#e254ff", "#ff4081");

        r = new Random();
    }

    public Color getColor(int i) {
        return Color.valueOf(Color.parseColor(colors.get(i)));

    }

    public int getColor() {
        return Color.parseColor(colors.get(r.nextInt(colors.size())));

    }

    public String getColorString() {
        return colors.get(r.nextInt(colors.size()));

    }
}