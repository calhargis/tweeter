package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.util.List;
import java.util.stream.Collectors;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedStatusTask extends PagedTask<Status> {

    public static final String SUCCESS_KEY = "success";
//    public static final String STATUSES_KEY = "statuses";
    public static final String STATUSES_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";

    protected PagedStatusTask(AuthToken authToken, User targetUser, int limit, Status lastItem, Handler messageHandler) {
        super(authToken, targetUser, limit, lastItem, messageHandler);
    }

    @Override
    protected final List<User> getUsersForItems(List<Status> items) {
        return items.stream().map(x -> x.user).collect(Collectors.toList());
    }
}
