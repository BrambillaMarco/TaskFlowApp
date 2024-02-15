package it.appventurers.taskflow.util;

import it.appventurers.taskflow.R;

public class WeatherIconUtil {

    public static int changeImageNight(String code) {
        switch (code) {
            case "1000":
                return 1;
            default:
                return R.drawable.ic_launcher_foreground;
        }
    }

    public static int changeImageDay(String code) {
        switch (code) {
            case "1000":
                return R.drawable.clear_day;
            default:
                return R.drawable.ic_launcher_foreground;
        }
    }
}
