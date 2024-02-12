package it.appventurers.taskflow.model;

public abstract class Result {

    private Result(){

    }

    public boolean isSuccess() {
        return this instanceof UserSuccess ||
                this instanceof HabitSuccess ||
                this instanceof DailySuccess ||
                this instanceof ToDoSuccess;
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

        public HabitSuccess(Habit habit) {
            this.habit = habit;
        }

        public Habit getHabit() {
            return habit;
        }
    }

    public static final class DailySuccess extends Result {

        private final Daily daily;

        public DailySuccess(Daily daily) {
            this.daily = daily;
        }

        public Daily getDaily() {
            return daily;
        }
    }

    public static final class ToDoSuccess extends Result {

        private final ToDo toDo;

        public ToDoSuccess(ToDo toDo) {
            this.toDo = toDo;
        }

        public ToDo getToDo() {
            return toDo;
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
