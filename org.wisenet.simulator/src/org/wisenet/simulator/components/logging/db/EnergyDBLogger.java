/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.logging.db;

import java.sql.Connection;
import org.wisenet.simulator.components.logging.EnergyLogger;

/**
 *
 * @author posilva
 */
public abstract class EnergyDBLogger extends EnergyLogger {

    protected String hostname;
    protected String port;
    protected String schema;
    protected String user;
    protected String password;
    protected String driver;

    protected Connection connection;

    public EnergyDBLogger() {
        init();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

  
}
