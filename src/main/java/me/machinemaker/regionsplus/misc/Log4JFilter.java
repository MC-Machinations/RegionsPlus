package me.machinemaker.regionsplus.misc;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.isNull;

public class Log4JFilter implements Filter {
    public Result filter(LogEvent record) {
        try {
            if (!isNull(record) && record.getMessage() != null) {
                String msg = record.getMessage().getFormattedMessage().toLowerCase();
                return msg.contains("issued server command: /rg flag list") || msg.contains("issued server command: /region flag list") ? Result.DENY : Result.NEUTRAL;
            } else return Result.NEUTRAL;
        } catch (NullPointerException e) {
            return Result.NEUTRAL;
        }
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return getResult(message);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return getResult(message);
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
        try {
            if (isNull(message)) return Result.NEUTRAL;
            else {
                String msg = message.toString().toLowerCase();
                return msg.contains("issued server command: /rg flag list") || msg.contains("issued server command: /region flag list") ? Result.DENY : Result.NEUTRAL;
            }
        } catch (NullPointerException e) {
            return Result.NEUTRAL;
        }
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
        try {
            if (isNull(message)) return Result.NEUTRAL;
            else {
                String msg = message.getFormattedMessage().toLowerCase();
                return msg.contains("issued server command: /rg flag list") || msg.contains("issued server command: /region flag list") ? Result.DENY : Result.NEUTRAL;
            }
        } catch (NullPointerException e) {
            return Result.NEUTRAL;
        }
    }

    @NotNull
    private Result getResult(String message) {
        try {
            if (isNull(message)) return Result.NEUTRAL;
            else {
                String msg = message.toLowerCase();
                return msg.contains("issued server command: /rg flag list") || msg.contains("issued server command: /region flag list") ? Result.DENY : Result.NEUTRAL;
            }
        } catch (NullPointerException e) {
            return Result.NEUTRAL;
        }
    }

    public Result getOnMatch() {
        return Result.NEUTRAL;
    }

    public Result getOnMismatch() {
        return Result.NEUTRAL;
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isStopped() {
        return false;
    }
}
