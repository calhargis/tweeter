package edu.byu.cs.tweeter.client.model.service.observer;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface FollowObserver extends ServiceObserver {

    void handleSuccess(List<User> followees, boolean hasMorePages);

}
