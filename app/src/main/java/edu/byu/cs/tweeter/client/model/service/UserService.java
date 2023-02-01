package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.LoginTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.RegisterTaskHandler;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains the business logic to support the login operation.
 */
public class UserService {

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface LoginObserver {
        void handleSuccess(User user, AuthToken authToken);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public interface RegisterObserver {
        void handleSuccess(User user, AuthToken authToken);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public interface GetUserObserver {
        void handleSuccess(User user);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     */
    public UserService() {
    }

    /**
     * Makes an asynchronous login request.
     *
     * @param username the user's name.
     * @param password the user's password.
     */
    public void login(String username, String password, LoginObserver observer) {
        LoginTask loginTask = getLoginTask(username, password, observer);
        BackgroundTaskUtils.runTask(loginTask);
    }

    /**
     * Returns an instance of {@link LoginTask}. Allows mocking of the LoginTask class for
     * testing purposes. All usages of LoginTask should get their instance from this method to
     * allow for proper mocking.
     *
     * @return the instance.
     */
    LoginTask getLoginTask(String username, String password, LoginObserver observer) {
        return new LoginTask(username, password, new LoginTaskHandler(observer));
    }

    public void register(String firstname, String lastname, String username, String password, String image, RegisterObserver observer) {
        RegisterTask registerTask = getRegisterTask(firstname, lastname, username, password, image, observer);
        BackgroundTaskUtils.runTask(registerTask);
    }

    RegisterTask getRegisterTask(String firstname, String lastname, String username, String password, String image, RegisterObserver observer) {
        return new RegisterTask(firstname, lastname, username, password, image, new RegisterTaskHandler(observer));
    }

    public void getUser(String username, AuthToken authToken, GetUserObserver observer) {
        GetUserTask getUserTask = getGetUserTask(username, authToken, observer);
        BackgroundTaskUtils.runTask(getUserTask);
    }

    GetUserTask getGetUserTask(String username, AuthToken authToken, GetUserObserver observer) {
        return new GetUserTask(authToken, username, new GetUserHandler(observer));
    }

}
