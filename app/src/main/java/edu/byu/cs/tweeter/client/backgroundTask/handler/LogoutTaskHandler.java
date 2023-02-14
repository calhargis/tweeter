package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.UserService;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class LogoutTaskHandler extends BackgroundTaskHandler<UserService.LogoutObserver> {

    public LogoutTaskHandler(UserService.LogoutObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(UserService.LogoutObserver observer, Bundle data) {
        observer.handleLogoutSuccess();
    }
}
