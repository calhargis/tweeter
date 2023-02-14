package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.StatusService;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class PostStatusHandler extends BackgroundTaskHandler<StatusService.PostStatusObserver> {

    public PostStatusHandler(StatusService.PostStatusObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(StatusService.PostStatusObserver observer, Bundle data) {
        boolean success = data.getBoolean(PostStatusTask.SUCCESS_KEY);
        observer.handlePostStatusSuccess(success);
    }
}
