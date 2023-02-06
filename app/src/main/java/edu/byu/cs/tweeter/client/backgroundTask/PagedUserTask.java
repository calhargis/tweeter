package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedUserTask extends PagedTask<User> {

    public static final String SUCCESS_KEY = "success";
    public static final String FOLLOWERS_KEY = "items";
    public static final String FOLLOWEES_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";

    protected PagedUserTask(AuthToken authToken, User targetUser, int limit, User lastItem, Handler messageHandler) {
        super(authToken, targetUser, limit, lastItem, messageHandler);
    }

    @Override
    protected final List<User> getUsersForItems(List<User> items) {
        return items;
    }
}
