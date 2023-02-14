package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends PagedPresenter {

    public void register(String firstName, String lastName, String alias, String password, String imageToUpload) {
        String validateError = validateRegistration(firstName, lastName, alias, password, imageToUpload);
        if (validateError == null) {
            view.clearErrorMessages();
            view.displayInfoMessage("Registering User...");
            new UserService().register(firstName, lastName, alias, password, imageToUpload, new RegisterObserver());
        } else {
            view.displayInfoMessage(validateError);
        }
    }



    private RegisterPresenter.View view;

    public RegisterPresenter(RegisterPresenter.View view) {
        this.view = view;
    }


    public String validateRegistration(String firstName, String lastName, String alias, String password, String imageToUpload) {
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        if (imageToUpload.length() == 0) {
            return "Profile image must be uploaded.";
        }
        return null;
    }

    private class RegisterObserver implements UserService.AuthenticationObserver {
        @Override
        public void handleSuccess(User user, AuthToken authToken) {
            view.clearInfoMessages();
            view.clearErrorMessages();
            view.displayInfoMessage("Hello " + Cache.getInstance().getCurrUser().getName());
            view.navigateToUser(user);
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage("Failed to register: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayErrorMessage("Failed to register because of exception: " + exception.getMessage());
        }
    }

}
