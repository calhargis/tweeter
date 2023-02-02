package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.main.MainActivity;

public class IsFollowerHandler extends Handler {

    FollowService.IsFollowerObserver observer;

    public IsFollowerHandler(FollowService.IsFollowerObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);
        if (success) {
            boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
            observer.isFollowerSuccess(isFollower);
        } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY)) {
            String message = "Failed to determine following relationship: " + msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
}
