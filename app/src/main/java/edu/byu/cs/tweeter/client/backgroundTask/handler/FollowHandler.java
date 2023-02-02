package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.main.MainActivity;

public class FollowHandler extends Handler {

    FollowService.FollowObserver observer;

    public FollowHandler(FollowService.FollowObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);
        if (success) {
            observer.handleFollowSuccess(success);
        } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY)) {
            String message = "Failed to follow: " + msg.getData().getString(FollowTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
}
