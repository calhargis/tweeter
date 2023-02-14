package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class GetFollowersTaskHandler extends BackgroundTaskHandler<FollowService.GetFollowersObserver> {

    public GetFollowersTaskHandler(FollowService.GetFollowersObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.GetFollowersObserver observer, Bundle data) {
        List<User> followees = (List<User>) data.getSerializable(GetFollowersTask.FOLLOWERS_KEY);
        boolean hasMorePages = data.getBoolean(GetFollowersTask.MORE_PAGES_KEY);
        observer.handleSuccess(followees, hasMorePages);
    }
}