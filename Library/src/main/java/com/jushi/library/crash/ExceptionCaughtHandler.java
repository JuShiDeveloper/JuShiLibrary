package com.jushi.library.crash;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionCaughtHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler uncaughtExceptionHandler;

    public ExceptionCaughtHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ExceptionInfoActivity.showException(ex);
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    }
}
