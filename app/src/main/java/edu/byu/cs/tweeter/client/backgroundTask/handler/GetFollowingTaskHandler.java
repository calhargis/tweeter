package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class GetFollowingTaskHandler extends BackgroundTaskHandler<FollowService.GetFollowingObserver> {

    public GetFollowingTaskHandler(FollowService.GetFollowingObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.GetFollowingObserver observer, Bundle data) {
        List<User> followees = (List<User>) data.getSerializable(GetFollowingTask.FOLLOWEES_KEY);
        boolean hasMorePages = data.getBoolean(GetFollowingTask.MORE_PAGES_KEY);
        observer.handleSuccess(followees, hasMorePages);
    }
}
