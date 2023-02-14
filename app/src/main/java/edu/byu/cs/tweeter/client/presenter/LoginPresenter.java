package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter extends PagedPresenter {

    private static final String LOG_TAG = "LoginPresenter";

    private final View view;

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public LoginPresenter(View view) {
        // An assertion would be better, but Android doesn't support Java assertions
        if(view == null) {
            throw new NullPointerException();
        }
        this.view = view;
    }

    public void initiateLogin(String username, String password) {
        String validateError = validateLogin(username, password);
        if (validateError == null) {
            view.clearErrorMessages();
            view.displayInfoMessage("Logging In...");
            new UserService().login(username, password, new LoginObserver());
        } else {
            view.displayInfoMessage(validateError);
        }
    }

    /**
     * A function to make sure all needed parameters are passed in when login occurs
     * @param username
     * @param password
     * @return
     */
    public String validateLogin(String username, String password) {
        if (username.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (username.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        return null;
    }


    private class LoginObserver implements UserService.AuthenticationObserver {
        /**
         * Invoked when the login request completes if the login was successful. Notifies the view of
         * the successful login.
         *
         * @param user the logged-in user.
         * @param authToken the session auth token.
         */
        @Override
        public void handleSuccess(User user, AuthToken authToken) {
            view.clearInfoMessages();
            view.clearErrorMessages();
            view.displayInfoMessage("Hello " + Cache.getInstance().getCurrUser().getName());
            view.navigateToUser(user);
        }

        /**
         * Invoked when the login request completes if the login request was unsuccessful. Notifies the
         * view of the unsuccessful login.
         *
         * @param message error message.
         */
        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage("Failed to login: " + message);
        }

        /**
         * A callback indicating that an exception occurred in an asynchronous method this class is
         * observing.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            view.displayErrorMessage("Failed to login because of exception: " + exception.getMessage());
        }
    }
}


