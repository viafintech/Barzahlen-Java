package de.barzahlen.logger;

/**
 * Logs to the console
 */
public class ConsoleLogger implements Logger {
    @Override
    public void debug(final String name, final String s) {
        System.out.println("debug: " + name + " - " + s);
    }

    @Override
    public void info(final String name, final String s) {
        System.out.println("info: " + name + " - " + s);
    }

    @Override
    public void notice(final String name, final String s) {
        System.out.println("notice: " + name + " - " + s);
    }

    @Override
    public void warn(final String name, final String s) {
        System.out.println("warn: " + name + " - " + s);
    }

    @Override
    public void error(final String name, final String s) {
        System.out.println("error: " + name + " - " + s);
    }

    @Override
    public void crit(final String name, final String s) {
        System.out.println("crit: " + name + " - " + s);
    }

    @Override
    public void alert(final String name, final String s) {
        System.out.println("alert: " + name + " - " + s);
    }

    @Override
    public void emerg(final String name, final String s) {
        System.out.println("emerg: " + name + " - " + s);
    }
}
