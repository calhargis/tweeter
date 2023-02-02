package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.Observable;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.FollowHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFollowingTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFollowersTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.UnfollowHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService {

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface GetFollowingObserver {
        void handleSuccess(List<User> followees, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface GetFollowersObserver {
        void handleSuccess(List<User> followers, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface GetFollowCountObserver {
        void handleFollowingCountSuccess(int count);
        void handleFollowerCountSuccess(int count);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface IsFollowerObserver {
        void isFollowerSuccess(boolean isFollower);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface FollowObserver {
        void handleFollowSuccess(boolean isFollower);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface UnfollowObserver {
        void handleUnfollowSuccess(boolean success);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     */
    public FollowService() {}

    /**
     * Requests the users that the user specified in the request is following.
     * Limits the number of followees returned and returns the next set of
     * followees after any that were returned in a previous request.
     * This is an asynchronous operation.
     *
     * @param authToken the session auth token.
     * @param targetUser the user for whom followees are being retrieved.
     * @param limit the maximum number of followees to return.
     * @param lastFollowee the last followee returned in the previous request (can be null).
     */
    public void getFollowees(AuthToken authToken, User targetUser, int limit, User lastFollowee, GetFollowingObserver observer) {
        GetFollowingTask followingTask = getGetFollowingTask(authToken, targetUser, limit, lastFollowee, observer);
        BackgroundTaskUtils.runTask(followingTask);
    }

    /**
     * Requests the users that the user specified in the request is following.
     * Limits the number of followees returned and returns the next set of
     * followees after any that were returned in a previous request.
     * This is an asynchronous operation.
     *
     * @param authToken the session auth token.
     * @param targetUser the user for whom followees are being retrieved.
     * @param limit the maximum number of followees to return.
     * @param lastFollower the last followee returned in the previous request (can be null).
     */
    public void getFollowers(AuthToken authToken, User targetUser, int limit, User lastFollower, GetFollowersObserver observer) {
        GetFollowersTask followersTask = getGetFollowersTask(authToken, targetUser, limit, lastFollower, observer);
        BackgroundTaskUtils.runTask(followersTask);
    }

    /**
     * Returns an instance of {@link GetFollowingTask}. Allows mocking of the
     * GetFollowingTask class for testing purposes. All usages of GetFollowingTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    // This method is public so it can be accessed by test cases
    public GetFollowingTask getGetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee, GetFollowingObserver observer) {
        return new GetFollowingTask(authToken, targetUser, limit, lastFollowee, new GetFollowingTaskHandler(observer));
    }

    /**
     * Returns an instance of {@link GetFollowersTask}. Allows mocking of the
     * GetFollowersTask class for testing purposes. All usages of GetFollowersTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    // This method is public so it can be accessed by test cases
    public GetFollowersTask getGetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower, GetFollowersObserver observer) {
        return new GetFollowersTask(authToken, targetUser, limit, lastFollower, new GetFollowersTaskHandler(observer));
    }

    /**
     *
     * @param authToken the session auth token.
     * @param targetUser the user for whom followers are being counted.
     */
    public void getFollowersCount(AuthToken authToken, User targetUser, GetFollowCountObserver observer) {
        GetFollowersCountTask task = getGetFollowersCountTask(authToken, targetUser, observer);
        BackgroundTaskUtils.runTask(task);
    }

    /**
     * Returns an instance of {@link GetFollowersCountTask}. Allows mocking of the
     * GetFollowersCountTask class for testing purposes. All usages of GetFollowersCountTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    public GetFollowersCountTask getGetFollowersCountTask(AuthToken authToken, User targetUser, GetFollowCountObserver observer) {
        return new GetFollowersCountTask(authToken, targetUser, new GetFollowersCountHandler(observer));
    }

    /**
     *
     * @param authToken the session auth token.
     * @param user the user for whom followees are being counted.
     */
    public void getFollowingCount(AuthToken authToken, User user, GetFollowCountObserver observer) {
        GetFollowingCountTask task = getGetFollowingCountTask(authToken, user, observer);
        BackgroundTaskUtils.runTask(task);
    }

    /**
     * Returns an instance of {@link GetFollowingCountTask}. Allows mocking of the
     * GetFollowingCountTask class for testing purposes. All usages of GetFollowingCountTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    public GetFollowingCountTask getGetFollowingCountTask(AuthToken authToken, User user, GetFollowCountObserver observer) {
        return new GetFollowingCountTask(authToken, user, new GetFollowingCountHandler(observer));
    }

    /**
     *
     * @param authToken the session auth token.
     * @param follower follower to check
     * @param followee followee to check
     */
    public void isFollower(AuthToken authToken, User follower, User followee, IsFollowerObserver observer) {
        IsFollowerTask task = getIsFollowerTask(authToken, follower, followee, observer);
        BackgroundTaskUtils.runTask(task);
    }

    /**
     * Returns an instance of {@link IsFollowerTask}. Allows mocking of the
     * IsFollowerTask class for testing purposes. All usages of IsFollowerTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    public IsFollowerTask getIsFollowerTask(AuthToken authToken, User follower, User followee, IsFollowerObserver observer) {
        return new IsFollowerTask(authToken, follower, followee, new IsFollowerHandler(observer));
    }

    /**
     *
     * @param authToken the session auth token.
     * @param user the user to follow
     */
    public void follow(AuthToken authToken, User user, FollowObserver observer) {
        FollowTask task = getFollowTask(authToken, user, observer);
        BackgroundTaskUtils.runTask(task);
    }

    /**
     * Returns an instance of {@link FollowTask}. Allows mocking of the
     * FollowTask class for testing purposes. All usages of FollowTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    public FollowTask getFollowTask(AuthToken authToken, User user, FollowObserver observer) {
        return new FollowTask(authToken, user, new FollowHandler(observer));
    }

    /**
     *
     * @param authToken the session auth token.
     * @param user the user to unfollow
     */
    public void unfollow(AuthToken authToken, User user, UnfollowObserver observer) {
        UnfollowTask task = getUnfollowTask(authToken, user, observer);
        BackgroundTaskUtils.runTask(task);
    }

    /**
     * Returns an instance of {@link UnfollowTask}. Allows mocking of the
     * UnfollowTask class for testing purposes. All usages of UnfollowTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    public UnfollowTask getUnfollowTask(AuthToken authToken, User user, UnfollowObserver observer) {
        return new UnfollowTask(authToken, user, new UnfollowHandler(observer));
    }
}
