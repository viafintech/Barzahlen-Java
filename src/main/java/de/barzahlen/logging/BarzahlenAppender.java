/**
 * Barzahlen Payment Module SDK
 *
 * NOTICE OF LICENSE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 *
 * @copyright   Copyright (c) 2012 Zerebro Internet GmbH (http://www.barzahlen.de/)
 * @author      Jesus Javier Nuno Garcia
 * @license     http://opensource.org/licenses/GPL-3.0  GNU General Public License, version 3 (GPL-3.0)
 */
package de.barzahlen.logging;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Configure the Barzahlen log appender
 * 
 * @author Jesus Javier Nuno Garcia
 */
public class BarzahlenAppender {

    /**
     * The console appender. Outputs the log in the console.
     */
    protected ConsoleAppender consoleAppender;

    /**
     * The file appender. Outputs the log into a file.
     */
    protected FileAppender fileAppender;

    /**
     * The pattern layout. Configures the output.
     */
    private PatternLayout patternLayout;

    /**
     * Default constructor. Sets up the appender.
     */
    public BarzahlenAppender() {
        try {
            this.patternLayout = new PatternLayout("%d{dd-MMM HH:mm:ss} %-5p (%F:%M:%L) %m%n)");
            this.consoleAppender = new ConsoleAppender();
            this.consoleAppender.setWriter(new OutputStreamWriter(System.out));
            this.consoleAppender.setLayout(this.patternLayout);
            this.consoleAppender.setName("Console Appender");
            this.fileAppender = new FileAppender(this.patternLayout, "./logs/Barzahlen.log");
            this.fileAppender.setName("Barzahlen Appender");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the console appender
     * 
     * @return the console appender
     */
    public ConsoleAppender getConsoleAppender() {
        return this.consoleAppender;
    }

    /**
     * Returns the file appender
     * 
     * @return the file appender
     */
    public FileAppender getFileAppender() {
        return this.fileAppender;
    }

}
