package it.appventurers.taskflow.model;

import java.util.ArrayList;

public abstract class Result {

    private Result(){

    }

    public boolean isSuccess() {
        return this instanceof UserSuccess ||
                this instanceof HabitSuccess ||
                this instanceof WeatherSuccess;
    }

    public static final class UserSuccess extends Result {

        private final User user;

        public UserSuccess(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static final class HabitSuccess extends Result {

        private final Habit habit;
        private final ArrayList<Habit> habitList;

        public HabitSuccess(ArrayList<Habit> habitList) {
            this.habit = null;
            this.habitList = habitList;
        }

        public HabitSuccess(Habit habit) {
            this.habit = habit;
            this.habitList = null;
        }

        public Habit getHabit() {
            return habit;
        }

        public ArrayList<Habit> getHabitList() {
            return habitList;
        }
    }

    public static final class WeatherSuccess extends Result {

        private final Weather weather;

        public WeatherSuccess(Weather weather) {
            this.weather = weather;
        }

        public Weather getWeather() {
            return weather;
        }
    }

    public static final class Fail extends Result {

        private final String error;

        public Fail(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
