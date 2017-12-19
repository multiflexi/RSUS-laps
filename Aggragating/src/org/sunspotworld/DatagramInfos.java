/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld;

import com.sun.spot.util.IEEEAddress;
import java.util.Date;

/**
 *
 * @author userrsus
 */
public class DatagramInfos {

    private long address;
    private byte command;
    private long time;
    private int value;
    private boolean test;
    public void afficher()
    {
        String mes="the address is "+IEEEAddress.toDottedHex(address)+"\n";
        mes+="[Time]: "+new Date(time).toString()+"\n ";
        mes+="[value]: "+value+" and boolean value is "+test;
        mes+="command is "+command;
        System.out.println(mes);

    }

    /**
     * @return the address
     */
    public long getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(long address) {
        this.address = address;
    }

    /**
     * @return the command
     */
    public byte getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(byte command) {
        this.command = command;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the test
     */
    public boolean isTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(boolean test) {
        this.test = test;
    }

  

}
