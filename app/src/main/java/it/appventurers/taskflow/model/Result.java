package it.appventurers.taskflow.model;

import it.appventurers.taskflow.data.repository.UserRepository;

public abstract class Result {

    private Result(){

    }

    public boolean isSuccess() {
        return this instanceof UserSuccess;
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
