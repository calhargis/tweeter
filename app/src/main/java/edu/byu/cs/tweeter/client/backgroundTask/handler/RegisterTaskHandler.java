package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class RegisterTaskHandler extends Handler {

    private final UserService.RegisterObserver observer;

    public RegisterTaskHandler(UserService.RegisterObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(Message message) {
        Bundle bundle = message.getData();
        boolean success = bundle.getBoolean(RegisterTask.SUCCESS_KEY);
        if (success) {
            User user = (User) bundle.getSerializable(RegisterTask.USER_KEY);
            AuthToken authToken = (AuthToken) bundle.getSerializable(RegisterTask.AUTH_TOKEN_KEY);
            Cache.getInstance().setCurrUser(user);
            Cache.getInstance().setCurrUserAuthToken(authToken);
            observer.handleSuccess(user, authToken);
        } else if (bundle.containsKey(RegisterTask.MESSAGE_KEY)) {
            String errorMessage = bundle.getString(RegisterTask.MESSAGE_KEY);
            observer.handleFailure(errorMessage);
        } else if (bundle.containsKey(RegisterTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) bundle.getSerializable(RegisterTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
}
