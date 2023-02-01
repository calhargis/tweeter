package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersTaskHandler extends Handler {

    private final FollowService.GetFollowersObserver observer;

    public GetFollowersTaskHandler(FollowService.GetFollowersObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(Message message) {
        Bundle bundle = message.getData();
        boolean success = bundle.getBoolean(GetFollowersTask.SUCCESS_KEY);
        if (success) {
            List<User> followees = (List<User>) bundle.getSerializable(GetFollowersTask.FOLLOWERS_KEY);
            boolean hasMorePages = bundle.getBoolean(GetFollowersTask.MORE_PAGES_KEY);
            observer.handleSuccess(followees, hasMorePages);
        } else if (bundle.containsKey(GetFollowersTask.MESSAGE_KEY)) {
            String errorMessage = bundle.getString(GetFollowersTask.MESSAGE_KEY);
            observer.handleFailure(errorMessage);
        } else if (bundle.containsKey(GetFollowersTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) bundle.getSerializable(GetFollowersTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
}