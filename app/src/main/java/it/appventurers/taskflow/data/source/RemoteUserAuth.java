package it.appventurers.taskflow.data.source;

import androidx.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import it.appventurers.taskflow.model.User;

public class RemoteUserAuth extends BaseRemoteUserAuth{

    private FirebaseAuth mAuth;

    public RemoteUserAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public User getLoggedUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            return null;
        } else {
            return new User(user.getDisplayName(), user.getEmail(), user.getUid());
        }
    }

    @Override
    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    userCallback.onSuccessAuthUser(new User(user.getDisplayName(), user.getEmail(), user.getUid()));
                } else {
                    userCallback.onFailure("Unable to sign up");
                }
            } else {
                userCallback.onFailure("Unable to sign up");
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    userCallback.onSuccessAuthUser(new User(user.getDisplayName(), user.getEmail(), user.getUid()));
                } else {
                    userCallback.onFailure("Unable to sign in.");
                }
            } else {
                userCallback.onFailure("Unable to sign in.");
            }
        });
    }

    @Override
    public void signInWithGoogle(String token) {
        if (token != null) {
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(token, null);
            mAuth.signInWithCredential(firebaseCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        userCallback.onSuccessAuthUser(new User(user.getDisplayName(), user.getEmail(), user.getUid()));
                    } else {
                        userCallback.onFailure("Unable to sign in with Google");
                    }
                } else {
                    userCallback.onFailure("Unable to sign in with Google");
                }
            });
        }
    }

    @Override
    public void updatePassword(String password) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userCallback.onSuccessUpdatePassword();
                } else {
                    userCallback.onFailure("Unable to update the password");
                }
            });
        }
    }

    @Override
    public void updateEmail(String email) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.verifyBeforeUpdateEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userCallback.onSuccessUpdateEmail();
                } else {
                    userCallback.onFailure("Unable to update the email");
                }
            });
        }
    }

    @Override
    public void retrievePassword(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userCallback.onSuccessRetrievePassword();
            } else {
                userCallback.onFailure("Unable to send the email");
            }
        });
    }

    @Override
    public void logout() {
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    firebaseAuth.removeAuthStateListener(this);
                    userCallback.onSuccessLogout();
                } else {
                    userCallback.onFailure("Unable to logout");
                }
            }
        };
        mAuth.addAuthStateListener(authStateListener);
        mAuth.signOut();
    }

    @Override
    public void deleteUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userCallback.onSuccessDeleteUser();
                } else {
                    userCallback.onFailure("Unable to delete account");
                }
            });
        }
    }
}
