package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class FollowHandler extends BackgroundTaskHandler<FollowService.FollowObserver> {

    public FollowHandler(FollowService.FollowObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.FollowObserver observer, Bundle data) {
        observer.handleFollowSuccess(data.getBoolean(FollowTask.SUCCESS_KEY));
    }
}
