/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.demo;

import com.sun.spot.util.IEEEAddress;

/**
 *
 * @author userrsus
 */
class DataInfos {


    private int value;
    private long receiveTime;
    private long address;
    private byte codeCmd;
    private boolean test;

    public DataInfos()
    {


    }
    public String toString()
    {
      return IEEEAddress.toDottedHex(address);


    }


    /**
     * @return the receiveTime
     */
    public long getReceiveTime() {
        return receiveTime;
    }

    /**
     * @param receiveTime the receiveTime to set
     */
    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
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
     * @return the codeCmd
     */
    public byte getCodeCmd() {
        return codeCmd;
    }

    /**
     * @param codeCmd the codeCmd to set
     */
    public void setCodeCmd(byte codeCmd) {
        this.codeCmd = codeCmd;
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


}
