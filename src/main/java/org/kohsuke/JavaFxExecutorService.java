package org.kohsuke;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class JavaFxExecutorService extends AbstractExecutorService {
    private static boolean javaFxRunning;
            
    JavaFxExecutorService() throws InterruptedException {
        synchronized (getClass()) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    Application.launch(ApplicationImpl.class);
                }
            };
            t.setDaemon(true);
            t.start();

            while (!javaFxRunning)
                getClass().wait();
        }
    }

    public void shutdown() {
        Platform.exit();
    }

    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void execute(Runnable command) {
        Platform.runLater(command);
    }

    public static class ApplicationImpl extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            synchronized (JavaFxExecutorService.class) {
                javaFxRunning = true;
                JavaFxExecutorService.class.notifyAll();
            }
        }
    }
}
