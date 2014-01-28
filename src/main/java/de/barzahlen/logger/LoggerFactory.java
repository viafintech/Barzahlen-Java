package de.barzahlen.logger;

/**
 * Creates a logger
 */
public class LoggerFactory {
    public enum LoggerType {
        CONSOLE
    }

    /**
     * Creates a specific logger
     *
     * @param loggerType
     * @return logger
     */
    public static Logger getLogger(final LoggerType loggerType) {
        Logger logger;

        switch (loggerType) {
            case CONSOLE:
                logger = new ConsoleLogger();
                break;
            default:
                logger = null;
        }

        return logger;
    }
}
