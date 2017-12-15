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
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
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
public class Agg extends MIDlet {

    private RadiogramConnection connMove = null;
    private RadiogramConnection connLight = null;
    private RadiogramConnection connBroad = null;


    private Radiogram moveRad, lightRad, broadRad ;


    protected void startApp() throws MIDletStateChangeException {
        try {

            connLight= (RadiogramConnection) Connector.open("radiogram://7f00.0101.0000.1002:122");
            connMove = (RadiogramConnection) Connector.open("radiogram://7f00.0101.0000.1003:123");
            connBroad =  (RadiogramConnection)Connector.open("radiogram://broadcast:124");

            moveRad = (Radiogram) connMove.newDatagram(50);
            lightRad = (Radiogram) connLight.newDatagram(50);
            broadRad = (Radiogram) connBroad.newDatagram(50);

            System.out.println("Aggregating Running");

            while(true){

                
                if(connLight.packetsAvailable()){       // PACKETS FROM LIGHT SENSOR

                    connLight.receive(lightRad);
                    long timeStamp = lightRad.readLong();
                    long MAC = lightRad.readLong();
                    byte command = lightRad.readByte();
                    
                    System.out.println("TimeStamp = " + timeStamp+ " MAC = "+IEEEAddress.toDottedHex(MAC)+" command = "+ command);

                    if(command == 1){

                        boolean value = lightRad.readBoolean();
                        lightRad.reset();

                        

                        broadRad.writeLong(timeStamp);
                        broadRad.writeLong(Spot.getInstance().getRadioPolicyManager().getIEEEAddress());
                        broadRad.writeByte(1);
                        broadRad.writeBoolean(value);
                        connBroad.send(broadRad);
                        broadRad.reset();
                    }

                    //EXAMPLES TO SEND COMMANDS TO THE LIGHT AND MOVE SENSOR (INSIDE THE IF(PACKETSAVAILABLE) TO TEST IT)
                    Utils.sleep(3000);

                    lightRad.reset();
                    lightRad.writeLong(timeStamp);
                    lightRad.writeLong(Spot.getInstance().getRadioPolicyManager().getIEEEAddress());
                    lightRad.writeByte(3);
                    connLight.send(lightRad);
                    lightRad.reset();


                    moveRad.reset();
                    moveRad.writeLong(timeStamp);
                    moveRad.writeLong(Spot.getInstance().getRadioPolicyManager().getIEEEAddress());
                    moveRad.writeByte(5);
                    connMove.send(moveRad);
                    moveRad.reset();


                }

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