package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;

public class GetFollowersCountHandler extends Handler{

    FollowService.GetFollowCountObserver observer;
    public GetFollowersCountHandler(FollowService.GetFollowCountObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);
        if (success) {
            int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
            observer.handleFollowerCountSuccess(count);
//            followerCount.setText(getString(R.string.followerCount, String.valueOf(count)));
        } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
            observer.handleFailure(message);
//            Toast.makeText(MainActivity.this, "Failed to get followers count: " + message, Toast.LENGTH_LONG).show();
        } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
            observer.handleException(ex);
//            Toast.makeText(MainActivity.this, "Failed to get followers count because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}


