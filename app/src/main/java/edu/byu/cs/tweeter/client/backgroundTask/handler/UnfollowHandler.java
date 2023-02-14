package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class UnfollowHandler extends BackgroundTaskHandler<FollowService.UnfollowObserver> {

    FollowService.UnfollowObserver observer;

    public UnfollowHandler(FollowService.UnfollowObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.UnfollowObserver observer, Bundle data) {
        observer.handleUnfollowSuccess(data.getBoolean(UnfollowTask.SUCCESS_KEY));
    }
}
