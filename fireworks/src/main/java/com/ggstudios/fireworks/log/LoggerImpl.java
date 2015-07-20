package com.ggstudios.fireworks.log;

public class LoggerImpl implements Logger {

    private static final String RED = (char)27 + "[31m";
    private static final String YELLOW = (char)27 + "[33m";
    private static final String BLUE = (char)27 + "[34m";
    private static final String RESET = (char)27 + "[0m";

    @Override
    public void d(String tag, String msg) {
        safePrintln(String.format(BLUE + "[%s] %s" + RESET, tag, msg));
    }

    @Override
    public void i(String tag, String msg) {
        safePrintln(String.format("[%s] %s", tag, msg));
    }

    @Override
    public void w(String tag, String msg) {
        safePrintln(String.format(YELLOW + "[%s] %s" + RESET, tag, msg));
    }

    @Override
    public void e(String tag, String msg) {
        safePrintln(String.format(RED + "[%s] %s" + RESET, tag, msg));
    }

    public void safePrintln(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }
}
