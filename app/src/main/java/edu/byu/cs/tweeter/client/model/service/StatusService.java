package edu.byu.cs.tweeter.client.model.service;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetFeedTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetStoryTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.PostStatusHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface GetFeedObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface GetStoryObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface PostStatusObserver {
        void handlePostStatusSuccess(boolean success);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * Requests the statuses that the user specified in the request is following.
     * Limits the number of status returned and returns the next set of
     * status after any that were returned in a previous request.
     * This is an asynchronous operation.
     *
     * @param authToken the session auth token.
     * @param targetUser the user for whom status are being retrieved.
     * @param limit the maximum number of status to return.
     * @param lastStatus the last status returned in the previous request (can be null).
     */
    public void getFeed(AuthToken authToken, User targetUser, int limit, Status lastStatus, StatusService.GetFeedObserver observer) {
        GetFeedTask feedTask = getGetFeedTask(authToken, targetUser, limit, lastStatus, observer);
        BackgroundTaskUtils.runTask(feedTask);
    }

    /**
     * Returns an instance of {@link GetFeedTask}. Allows mocking of the
     * GetFollowingTask class for testing purposes. All usages of GetFollowingTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    // This method is public so it can be accessed by test cases
    public GetFeedTask getGetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus, StatusService.GetFeedObserver observer) {
        return new GetFeedTask(authToken, targetUser, limit, lastStatus, new GetFeedTaskHandler(observer));
    }

    /**
     * Requests the statuses that the user specified in the request is following.
     * Limits the number of status returned and returns the next set of
     * status after any that were returned in a previous request.
     * This is an asynchronous operation.
     *
     * @param authToken the session auth token.
     * @param targetUser the user for whom status are being retrieved.
     * @param limit the maximum number of status to return.
     * @param lastStatus the last status returned in the previous request (can be null).
     */
    public void getStory(AuthToken authToken, User targetUser, int limit, Status lastStatus, StatusService.GetStoryObserver observer) {
        GetStoryTask storyTask = getGetStoryTask(authToken, targetUser, limit, lastStatus, observer);
        BackgroundTaskUtils.runTask(storyTask);
    }

    /**
     * Returns an instance of {@link GetFeedTask}. Allows mocking of the
     * GetFollowingTask class for testing purposes. All usages of GetFollowingTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    // This method is public so it can be accessed by test cases
    public GetStoryTask getGetStoryTask(AuthToken authToken, User targetUser, int limit, Status lastStatus, StatusService.GetStoryObserver observer) {
        return new GetStoryTask(authToken, targetUser, limit, lastStatus, new GetStoryTaskHandler(observer));
    }

    public void postStatus(AuthToken authToken, Status status, PostStatusObserver observer) {
        PostStatusTask task = getPostStatusTask(authToken, status, observer);
        BackgroundTaskUtils.runTask(task);
    }

    public PostStatusTask getPostStatusTask(AuthToken authToken, Status status, PostStatusObserver observer) {
        return new PostStatusTask(authToken, status, new PostStatusHandler(observer));
    }

}
