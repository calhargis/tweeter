package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class GetFeedTaskHandler extends BackgroundTaskHandler<StatusService.GetFeedObserver> {

    public GetFeedTaskHandler(StatusService.GetFeedObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(StatusService.GetFeedObserver observer, Bundle data) {
        List<Status> statuses = (List<Status>) data.getSerializable(GetFeedTask.STATUSES_KEY);
        boolean hasMorePages = data.getBoolean(GetFeedTask.MORE_PAGES_KEY);
        observer.handleSuccess(statuses, hasMorePages);
    }
}