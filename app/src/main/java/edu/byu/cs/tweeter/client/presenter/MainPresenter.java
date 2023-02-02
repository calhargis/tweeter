package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter implements UserService.LogoutObserver, FollowService.GetFollowCountObserver,
        FollowService.IsFollowerObserver, FollowService.FollowObserver, FollowService.UnfollowObserver, StatusService.PostStatusObserver {

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
        void updateIsFollower(boolean isFollower);
        void updateFollow(boolean success);
        void updateUnfollow(boolean success);
        void updatePostStatus(boolean success);
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

    public void isFollower(AuthToken authToken, User follower, User followee) {
        new FollowService().isFollower(authToken, follower, followee, this);
    }

    public void follow(AuthToken authToken, User user) {
        new FollowService().follow(authToken, user, this);
    }

    public void unfollow(AuthToken authToken, User user) {
        new FollowService().unfollow(authToken, user, this);
    }

    public void postStatus(AuthToken authToken, Status status) {
        new StatusService().postStatus(authToken, status, this);
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

    @Override
    public void isFollowerSuccess(boolean isFollower) {
        view.updateIsFollower(isFollower);
    }

    @Override
    public void handleFollowSuccess(boolean success) {
        view.updateFollow(success);
    }

    @Override
    public void handleUnfollowSuccess(boolean success) {
        view.updateUnfollow(success);
    }

    @Override
    public void handlePostStatusSuccess(boolean success) {
        view.updatePostStatus(success);
    }

    /* ---------------- HELPER FUNCTIONS FOR MAIN ACTIVITY ---------------- */

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }
}
