package com.ggstudios.fireworks.log;

public interface Logger {
    void d(String tag, String msg);
    void i(String tag, String msg);
    void w(String tag, String msg);
    void e(String tag, String msg);
}
