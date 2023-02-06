package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.User;

public class Presenter {

    public interface View {
        void displayErrorMessage(String message);

        void clearErrorMessages();

        void displayInfoMessage(String message);

        void clearInfoMessages();

        void navigateToUser(User user);
    }

}
