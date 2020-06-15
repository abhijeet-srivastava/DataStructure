package com.oracle.casb.junits;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenerateGdcData {

    public static void main(String[] args) {
        GenerateGdcData ggd = new GenerateGdcData();
        ggd.generateDate(201950);
        ggd.generateDate(201951);
    }

    private void generateDate(int dcId) {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 5);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Random r = new Random();
        for (int i = 0; i < 7; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%c%s%c%c", '\'', df.format(date.plusDays(i)),'\'', ','));
            sb.append(String.format("%d%c", dcId, ','));
            for(int j = 0; j < 11; j++) {
                sb.append(String.format("%f%c", generateRandomVolume(r), ','));
            }
            sb.append(String.format("%f", generateRandomVolume(r)));
            System.out.printf("(%s),\n", sb.toString());
        }
    }

    private Double generateRandomVolume(Random r) {
        Double rangeMin = Double.valueOf(400000.33899d);
        Double rangeMax = Double.valueOf(900000.33899d);
        return (rangeMin + (rangeMax - rangeMin) * r.nextDouble());
    }
}
