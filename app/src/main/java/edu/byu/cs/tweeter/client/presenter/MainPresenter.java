package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter implements UserService.LogoutObserver, FollowService.GetFollowCountObserver {

    private static final String LOG_TAG = "MainPresenter";

    private final MainPresenter.View view;

    private final User user;

    private final AuthToken authToken;

    public interface View {
        void displayErrorMessage(String message);
        void displayInfoMessage(String message);
        void navigateToUser(User user);
        void logoutUser();
        void updateFollowerCount(int count);
        void updateFollowingCount(int count);
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public MainPresenter(MainPresenter.View view, User user, AuthToken authToken) {
        // An assertion would be better, but Android doesn't support Java assertions
        if(view == null) {
            throw new NullPointerException();
        }
        this.view = view;
        this.user = user;
        this.authToken = authToken;
    }

    public void logout(AuthToken authToken) {
        view.displayInfoMessage("Logging Out...");
        new UserService().logout(authToken, this);
    }

    public void getFollowersCount(AuthToken authToken, User targetUser) {
        new FollowService().getFollowersCount(authToken, targetUser, this);
    }

    public void getFollowingCount(AuthToken authToken, User user) {
        new FollowService().getFollowingCount(authToken, user, this);
    }

    @Override
    public void handleLogoutSuccess() {
        view.logoutUser();
    }

    @Override
    public void handleFailure(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void handleException(Exception exception) {
        view.displayErrorMessage(exception.getMessage());
    }

    @Override
    public void handleFollowerCountSuccess(int count) {
        view.updateFollowerCount(count);
    }

    @Override
    public void handleFollowingCountSuccess(int count) {
        view.updateFollowingCount(count);
    }
}
