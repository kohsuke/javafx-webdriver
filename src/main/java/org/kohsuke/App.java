package org.kohsuke;

import javafx.scene.web.WebEngine;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        ExecutorService exec = new JavaFxExecutorService();

        try {
            WebEngine view = exec.submit(new Callable<WebEngine>() {
                public WebEngine call() throws Exception {
                    WebEngine webView = new WebEngine();
                    webView.load("http://www.google.com/");
                    return webView;
                }
            }).get();
            System.out.println(view);
        } finally {
            exec.shutdown();
        }
    }
}
