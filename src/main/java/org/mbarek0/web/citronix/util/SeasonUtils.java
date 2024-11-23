package org.mbarek0.web.citronix.util;

import org.mbarek0.web.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.time.Month;

public class SeasonUtils {

    public static Season getSeasonFromDate(LocalDate date) {
        Month month = date.getMonth();

        switch (month) {
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                return Season.WINTER;
            case MARCH:
            case APRIL:
            case MAY:
                return Season.SPRING;
            case JUNE:
            case JULY:
            case AUGUST:
                return Season.SUMMER;
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
                return Season.AUTUMN;
            default:
                throw new IllegalArgumentException("Invalid month in date: " + date);
        }
    }
}
