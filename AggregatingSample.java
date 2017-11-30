/*
 * AggregatingSample.java
 *
 * Created on Nov 20, 2017, 3:32:48 PM
 *
 * To change this template, choose Tools | Template Manager and locate this template
 * under the Java Classes. You can then make changes to the template in the Source Editor.
 */

package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.util.IEEEAddress;
import java.io.IOException;
import java.util.Date;
import javax.microedition.io.Connector;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 * 
 * The manifest specifies this class as MIDlet-N, which means it will not
 * be automatically selected for execution.
 *
 * @author userrsus

 */
public class AggregatingSample extends MIDlet {

    private RadiogramConnection connR = null;
    private RadiogramConnection connX = null;


    private Radiogram rRad, xRad, lRad ;

    protected void startApp() throws MIDletStateChangeException {
        try {
            connR = (RadiogramConnection) Connector.open("radiogram://:123");
            connX =  (RadiogramConnection)Connector.open("radiogram://broadcast:124");

            rRad = (Radiogram) connR.newDatagram(50);
            xRad = (Radiogram) connX.newDatagram(50);
            lRad = (Radiogram) connX.newDatagram(50);



            while(true){
                if(connR.packetsAvailable()){
                    connR.receive(rRad);
                    long timeStamp = rRad.readLong();
                    long MAC = rRad.readLong();
                    byte command = rRad.readByte();
                    boolean value = rRad.readBoolean();

                    System.out.println("TimeStamp = " + timeStamp+ " MAC = "+IEEEAddress.toDottedHex(MAC)+" command = "+ command + " value = " +value);

                    rRad.reset();

                    lRad.writeLong(timeStamp);
                    lRad.writeLong(Spot.getInstance().getRadioPolicyManager().getIEEEAddress());
                    lRad.writeByte(command);
                    lRad.writeBoolean(value);
                    connX.send(lRad);
                    xRad.reset();
                }
                Date d = new Date();
                xRad.writeLong(d.getTime());
                xRad.writeLong(Spot.getInstance().getRadioPolicyManager().getIEEEAddress());
                xRad.writeByte(5);
                connX.send(xRad);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * It is not called if MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true the MIDlet must cleanup and release all resources.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
