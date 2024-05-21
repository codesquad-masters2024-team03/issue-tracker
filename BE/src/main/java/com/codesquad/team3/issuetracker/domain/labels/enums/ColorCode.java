package com.codesquad.team3.issuetracker.domain.labels.enums;

import lombok.Getter;
import java.util.Arrays;
import java.util.Random;

@Getter
public enum ColorCode {


    WHITE("#FFFFFF"),
    BLACK("#000000"),
    RED("#FF0000"),
    GREEN("#00FF00"),
    BLUE("#0000FF"),
    YELLOW("#FFFF00"),
    CYAN("#00FFFF"),
    MAGENTA("#FF00FF"),
    SILVER("#C0C0C0"),
    GRAY("#808080"),
    MAROON("#800000"),
    OLIVE("#808000"),
    PURPLE("#800080"),
    TEAL("#008080"),
    NAVY("#000080");



    String code;
    private static final Random RANDOM = new Random();

    ColorCode(String code) {
        this.code = code;
    }

    public static boolean validate(String colorCode){


        ColorCode[] colors = ColorCode.
                values();
        return Arrays.stream(colors).anyMatch(color->color.code.equals(colorCode));
    }

    public static String getRandomColor(){
        ColorCode[] colors = ColorCode.
                values();

        int randomIndex = RANDOM.nextInt(colors.length);
        return colors[randomIndex].getCode();
    }
}
