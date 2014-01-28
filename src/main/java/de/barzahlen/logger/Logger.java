package de.barzahlen.logger;

/**
 * Implements methods for log things
 * Levels implemented like described in RFC 5424
 */
public interface Logger {
    public enum LogLevel {
        DEBUG,
        INFO,
        NOTICE,
        WARN,
        ERROR,
        CRIT,
        ALERT,
        EMERG
    }

    void debug(final String name, final String s);

    void info(final String name, final String s);

    void notice(final String name, final String s);

    void warn(final String name, final String s);

    void error(final String name, final String s);

    void crit(final String name, final String s);

    void alert(final String name, final String s);

    void emerg(final String name, final String s);
}
