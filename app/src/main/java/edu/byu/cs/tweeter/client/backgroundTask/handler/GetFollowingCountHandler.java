package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.main.MainActivity;

public class GetFollowingCountHandler extends Handler {

    FollowService.GetFollowCountObserver observer;

    public GetFollowingCountHandler(FollowService.GetFollowCountObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(GetFollowingCountTask.SUCCESS_KEY);
        if (success) {
            int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
            observer.handleFollowingCountSuccess(count);
//            followeeCount.setText(getString(R.string.followeeCount, String.valueOf(count)));
        } else if (msg.getData().containsKey(GetFollowingCountTask.MESSAGE_KEY)) {
            String message = "Failed to get following count: " + msg.getData().getString(GetFollowingCountTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(GetFollowingCountTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetFollowingCountTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
}
