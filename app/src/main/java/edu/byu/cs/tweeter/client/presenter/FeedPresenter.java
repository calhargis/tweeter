package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
//import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter implements StatusService.GetFeedObserver, UserService.GetUserObserver{

    private static final String LOG_TAG = "FeedPresenter";
    public static final int PAGE_SIZE = 10;

    private final FeedPresenter.View view;
    private final User user;
    private final AuthToken authToken;

    private Status lastStatus;
    private boolean hasMorePages = true;
    private boolean isLoading = false;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void setLoading(boolean value);
        void addItems(List<Status> newStatuses);
        void displayErrorMessage(String message);
        void navigateToUser(User user);
    }

    /**
     * Creates an instance.
     *
     * @param view      the view for which this class is the presenter.
     * @param user      the user that is currently logged in.
     * @param authToken the auth token for the current session.
     */
    public FeedPresenter(FeedPresenter.View view, User user, AuthToken authToken) {
        this.view = view;
        this.user = user;
        this.authToken = authToken;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    private void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public boolean isHasMorePages() {
        return hasMorePages;
    }

    private void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    /**
     * Called by the view to request that another page of "followers" users be loaded.
     */
    public void loadMoreItems() {
        if (!isLoading && hasMorePages) {
            setLoading(true);
            view.setLoading(true);

            getFeed(authToken, user, PAGE_SIZE, lastStatus);
        }
    }

    /**
     * Requests the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned for a previous request. This is an asynchronous
     * operation.
     *
     * @param authToken    the session auth token.
     * @param targetUser   the user for whom followees are being retrieved.
     * @param limit        the maximum number of followees to return.
     * @param lastStatus the last status returned in the previous request (can be null).
     */
    public void getFeed(AuthToken authToken, User targetUser, int limit, Status lastStatus) {
        getStatusService().getFeed(authToken, targetUser, limit, lastStatus, this);
    }

    /**
     * Returns an instance of {@link StatusService}. Allows mocking of the FollowService class
     * for testing purposes. All usages of FollowService should get their FollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public StatusService getStatusService() {
        return new StatusService();
    }

    public void getUser(String username, AuthToken authToken) {
        getUserService().getUser(username, authToken, this);
    }

    public UserService getUserService() {
        return new UserService();
    }

    /**
     * Adds new followers retrieved asynchronously from the service to the view.
     *
     * @param statuses    the retrieved followers.
     * @param hasMorePages whether or not there are more followers to be retrieved.
     */
    @Override
    public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
        setLastStatus((statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null);
        setHasMorePages(hasMorePages);

        view.setLoading(false);
        view.addItems(statuses);
        setLoading(false);
    }

    /**
     * Notifies the presenter when asynchronous retrieval of followers failed.
     *
     * @param message error message.
     */
    @Override
    public void handleFailure(String message) {
        String errorMessage = "Failed to retrieve feed: " + message;
        Log.e(LOG_TAG, errorMessage);

        view.setLoading(false);
        view.displayErrorMessage(errorMessage);
        setLoading(false);
    }

    /**
     * Notifies the presenter that an exception occurred in an asynchronous method this class is
     * observing.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        String errorMessage = "Failed to retrieve followers because of exception: " + exception.getMessage();
        Log.e(LOG_TAG, errorMessage, exception);

        view.setLoading(false);
        view.displayErrorMessage(errorMessage);
        setLoading(false);
    }

    @Override
    public void handleSuccess(User user) {
        view.navigateToUser(user);
    }

}
