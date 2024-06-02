package ch.framedev.simplejavautils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * / This Plugin was Created by FrameDev
 * / Package : de.framedev.javautils
 * / ClassName MyFormatter
 * / Date: 29.05.21
 * / Project: JavaUtils
 * / Copyrighted by FrameDev
 */

public class MyFormatter extends Formatter {
    // Create a DateFormat to format the logger timestamp.
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
    private final boolean timeFormat;

    public MyFormatter(boolean timeFormat) {
        this.timeFormat = timeFormat;
    }

    public MyFormatter() {
        this.timeFormat = false;
    }

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        if (timeFormat)
            builder.append(df.format(new Date(record.getMillis()))).append(" - ");
        builder.append("[").append(record.getLoggerName()).append("] - ");
        builder.append("[").append(record.getLevel()).append("] : ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }

    public Logger createEmptyLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        MyFormatter formatter = new MyFormatter(timeFormat);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(formatter);

        logger.addHandler(handler);
        return logger;
    }
}