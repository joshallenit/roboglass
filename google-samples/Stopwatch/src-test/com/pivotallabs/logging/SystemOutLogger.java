package com.pivotallabs.logging;

import com.xtremelabs.utilities.ILogger;
import com.xtremelabs.utilities.TraceLogger;

/**
 * @author salvi/jallen@pivotallabs.com
 * @since 2014-01-17.
 */
public class SystemOutLogger implements ILogger {

    @Override
    public void i(String message, Object... objects) {
        println("I", message, objects);
    }

    @Override
    public void w(String message, Object... objects) {
        println("W", message, objects);
    }

    @Override
    public void v(String message, Object... objects) {
        println("V", message, objects);
    }

    @Override
    public void d(String message, Object... objects) {
        println("D", message, objects);
    }

    @Override
    public void e(String message, Object... objects) {
        println("E", message, objects);
    }

    private void println(String prefix, String message, Object... objects) {
        System.out.println(prefix + ": " + TraceLogger.formatMessageString(message, objects));
    }

}
