package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.StatusService;

public class PostStatusHandler extends Handler {

    StatusService.PostStatusObserver observer;
    public PostStatusHandler(StatusService.PostStatusObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
        if (success) {
            observer.handlePostStatusSuccess(success);
        } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
            String message = "Failed to post status: " + msg.getData().getString(PostStatusTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
}
