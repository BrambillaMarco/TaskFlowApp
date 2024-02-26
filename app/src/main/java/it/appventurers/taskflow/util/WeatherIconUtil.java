package it.appventurers.taskflow.util;

import it.appventurers.taskflow.R;

public class WeatherIconUtil {

    public static int changeImageNight(String code) {
        switch (code) {
            case "1000":
                return R.drawable.clear;
            case "1003":
                return R.drawable.partly_cloudy_night;
            case "1006":
                return R.drawable.cloudy_night;
            case "1009":
                return R.drawable.overcast;
            case "1030":
                return R.drawable.mist_night;
            case "1063":
                return R.drawable.patchy_rain_possible_night;
            case "1066":
                return R.drawable.patchy_snow_possible_night;
            case "1069":
                return R.drawable.patchy_sleet_possible_night;
            case "1072":
                return R.drawable.patchy_freezing_drizzle_possible;
            case "1087":
                return R.drawable.thundery_outbreaks_possible;
            case "1114":
                return R.drawable.blowing_snow;
            case "1117":
                return R.drawable.blizzard;
            case "1135":
                return R.drawable.fog_night;
            case "1147":
                return R.drawable.freezing_fog_night;
            case "1150":
                return R.drawable.patchy_light_drizzle_night;
            case "1153":
                return R.drawable.light_drizzle_night;
            case "1168":
                return R.drawable.freezing_drizzle_night;
            case "1171":
                return R.drawable.heavy_freezing_drizzle_night;
            case "1180":
                return R.drawable.patchy_light_rain;
            case "1183":
                return R.drawable.light_rain;
            case "1186":
                return R.drawable.moderate_rain_at_times;
            case "1189":
                return R.drawable.moderate_rain;
            case "1192":
                return R.drawable.heavy_rain_at_times;
            case "1195":
                return R.drawable.heavy_rain;
            case "1198":
                return R.drawable.light_freezing_rain;
            case "1201":
                return R.drawable.moderate_or_heavy_freezing_rain;
            case "1204":
                return R.drawable.light_sleet;
            case "1207":
                return R.drawable.moderate_or_heavy_sleet;
            case "1210":
                return R.drawable.patchy_light_snow_night;
            case "1213":
                return R.drawable.light_snow_night;
            case "1216":
                return R.drawable.patchy_moderate_snow_night;
            case "1219":
                return R.drawable.moderate_snow_night;
            case "1222":
                return R.drawable.patchy_heavy_snow_night;
            case "1225":
                return R.drawable.heavy_snow_night;
            case "1237":
                return R.drawable.ice_pellets;
            case "1240":
                return R.drawable.light_rain_shower;
            case "1243":
                return R.drawable.moderate_or_heavy_rain_shower;
            case "1246":
                return R.drawable.torrential_rain_shower;
            case "1249":
                return R.drawable.light_sleet_showers;
            case "1252":
                return R.drawable.moderate_or_heavy_sleet_showers;
            case "1255":
                return R.drawable.light_snow_showers;
            case "1258":
                return R.drawable.moderate_or_heavy_snow_showers;
            case "1261":
                return R.drawable.light_showers_of_ice_pellets;
            case "1264":
                return R.drawable.moderate_or_heavy_showers_of_ice_pellets;
            case "1273":
                return R.drawable.patchy_light_rain_with_thunder_night;
            case "1276":
                return R.drawable.moderate_or_heavy_rain_with_thunder;
            case "1279":
                return R.drawable.patchy_light_snow_with_thunder;
            case "1282":
                return R.drawable.moderate_or_heavy_snow_with_thunder;
            case "3000":
                return R.drawable.cloud_connection;
            default:
                return R.drawable.ic_launcher_foreground;
        }
    }

    public static int changeImageDay(String code) {
        switch (code) {
            case "1000":
                return R.drawable.sunny;
            case "1003":
                return R.drawable.partly_cloudy;
            case "1006":
                return R.drawable.cloudy;
            case "1009":
                return R.drawable.overcast;
            case "1030":
                return R.drawable.mist;
            case "1063":
                return R.drawable.patchy_rain_possible;
            case "1066":
                return R.drawable.patchy_snow_possible;
            case "1069":
                return R.drawable.patchy_sleet_possible;
            case "1072":
                return R.drawable.patchy_freezing_drizzle_possible;
            case "1087":
                return R.drawable.thundery_outbreaks_possible;
            case "1114":
                return R.drawable.blowing_snow;
            case "1117":
                return R.drawable.blizzard;
            case "1135":
                return R.drawable.fog;
            case "1147":
                return R.drawable.freezing_fog;
            case "1150":
                return R.drawable.patchy_light_drizzle;
            case "1153":
                return R.drawable.light_drizzle;
            case "1168":
                return R.drawable.freezing_drizzle;
            case "1171":
                return R.drawable.heavy_freezing_drizzle;
            case "1180":
                return R.drawable.patchy_light_rain;
            case "1183":
                return R.drawable.light_rain;
            case "1186":
                return R.drawable.moderate_rain_at_times;
            case "1189":
                return R.drawable.moderate_rain;
            case "1192":
                return R.drawable.heavy_rain_at_times;
            case "1195":
                return R.drawable.heavy_rain;
            case "1198":
                return R.drawable.light_freezing_rain;
            case "1201":
                return R.drawable.moderate_or_heavy_freezing_rain;
            case "1204":
                return R.drawable.light_sleet;
            case "1207":
                return R.drawable.moderate_or_heavy_sleet;
            case "1210":
                return R.drawable.patchy_light_snow;
            case "1213":
                return R.drawable.light_snow;
            case "1216":
                return R.drawable.patchy_moderate_snow;
            case "1219":
                return R.drawable.moderate_snow;
            case "1222":
                return R.drawable.patchy_heavy_snow;
            case "1225":
                return R.drawable.heavy_snow;
            case "1237":
                return R.drawable.ice_pellets;
            case "1240":
                return R.drawable.light_rain_shower;
            case "1243":
                return R.drawable.moderate_or_heavy_rain_shower;
            case "1246":
                return R.drawable.torrential_rain_shower;
            case "1249":
                return R.drawable.light_sleet_showers;
            case "1252":
                return R.drawable.moderate_or_heavy_sleet_showers;
            case "1255":
                return R.drawable.light_snow_showers;
            case "1258":
                return R.drawable.moderate_or_heavy_snow_showers;
            case "1261":
                return R.drawable.light_showers_of_ice_pellets;
            case "1264":
                return R.drawable.moderate_or_heavy_showers_of_ice_pellets;
            case "1273":
                return R.drawable.patchy_light_rain_with_thunder;
            case "1276":
                return R.drawable.moderate_or_heavy_rain_with_thunder;
            case "1279":
                return R.drawable.patchy_light_snow_with_thunder;
            case "1282":
                return R.drawable.moderate_or_heavy_snow_with_thunder;
            case "3000":
                return R.drawable.cloud_connection;
            default:
                return R.drawable.ic_launcher_foreground;
        }
    }
}
