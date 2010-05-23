/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.logging.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author posilva
 */
public class EnergyMySQLDBLogger extends EnergyDBLogger {

    ArrayBlockingQueue<Record> recordsQueue = new ArrayBlockingQueue<Record>(5000);
    private boolean closed=false;

    class Record {

        short id;
        String event;
        long realTime;
        long simTime;
        double value;
        String state;

        public Record(short id, String event, long realTime, long simTime, double value, String state) {
            this.id = id;
            this.event = event;
            this.realTime = realTime;
            this.simTime = simTime;
            this.value = value;
            this.state = state;
        }
    }
//============================================================
//CREATE TABLE `WiSeNetDB`.`EnergyLogger` (
//`node` SMALLINT NOT NULL ,
//`event` VARCHAR( 50 ) NOT NULL ,
//`realtime` BIGINT NOT NULL ,
//`simtime` BIGINT NOT NULL ,
//`value` DOUBLE NOT NULL ,
//`state` VARCHAR( 50 ) NOT NULL
//) ENGINE = InnoDB;
//============================================================

    @Override
    public void init() {
        try {
            setDriver("com.mysql.jdbc.Driver");
            setHostname("localhost");
            setPort("3306");
            setSchema("WiSeNetDB");
            setUser("root");
            setPassword("");

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + getHostname() + ":"
                    + getPort() + "/"
                    + getSchema() + "?user="
                    + getUser() + "&password="
                    + getPassword();

            this.connection = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(EnergyMySQLDBLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EnergyMySQLDBLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void update(final short id, String event, final long realTime, final long simTime, final double value, String state) {
        try {
            addRecord2Queue(id, event, realTime, simTime, value, state);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyMySQLDBLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void open() {
        dispachQueue();
    }

    @Override
    public void close() {
//        while (!recordsQueue.isEmpty()) {
//            //nothing
//        }
//        ;
        closed = true;
    }

    void addRecord(short id, String event, long realTime, long simTime, double value, String state) throws SQLException {
        Statement st = getConnection().createStatement();
        st.executeUpdate("INSERT INTO EnergyLogger (`node`,`event`,`realtime`,`simtime`,`value`,`state`) VALUES("
                + id + ",'"
                + event + "',"
                + realTime + ","
                + simTime + ","
                + value + ",'"
                + state + "'"
                + ");");
    }

    void addRecord2Queue(short id, String event, long realTime, long simTime, double value, String state) throws SQLException {
        recordsQueue.offer(new Record(id, event, realTime, simTime, value, state));
    }

    void dispachQueue() {
        new Thread(new Runnable() {

            public void run() {
                while (!closed) {
                    try {
                        Record r = recordsQueue.take();
                        addRecord(r.id, r.event, r.realTime, r.simTime, r.value, r.state);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EnergyMySQLDBLogger.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(EnergyMySQLDBLogger.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }
}
