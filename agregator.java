/* @author  Jaroslav Svoboda <jaroslav.svoboda@alumnos.upm.es>
 * @version 1.0
 * agregator.java
 *
 * Created on Nov 15, 2017 10:17:03 AM;
 */

package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.util.IEEEAddress;
import java.io.IOException;
import javax.microedition.io.Connector;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 * 
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 *
 * It is used on aggregator.
 */
public class agregator extends MIDlet {

private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
private RadiogramConnection conn_sink = null;
private RadiogramConnection conn_movement = null;
private RadiogramConnection conn_light = null;
private Radiogram xdg_sink;
private Radiogram xdg_movement;
private Radiogram xdg_light;
private RadiogramConnection conn_mote;
    protected void startApp() throws MIDletStateChangeException {
         try {
            String data = null;
            conn_sink = (RadiogramConnection) Connector.open("radiogram://7f00.0101.0000.1004:125"); //specifying connection to sink
            conn_movement = (RadiogramConnection) Connector.open("radiogram://7f00.0101.0000.1001:123"); //specifying connection to temperature sensor
            conn_light = (RadiogramConnection) Connector.open("radiogram://7f00.0101.0000.1002:124"); //specifying connection to light intensity sensor
            xdg_sink = (Radiogram) conn_sink.newDatagram(conn_sink.getMaximumLength()); // specifying datagram size
            xdg_movement = (Radiogram) conn_movement.newDatagram(conn_movement.getMaximumLength()); // specifying datagram size
            xdg_light = (Radiogram) conn_light.newDatagram(conn_movement.getMaximumLength()); // specifying datagram size

            while (true) {
                try {
                     //recieve from light sensor
                    if (conn_light.packetsAvailable()==true){ //if message from light sensor is available
                    conn_light.receive(xdg_light);//reieve message from loght sensor
                    xdg_light.readDouble();
                    xdg_light.readLong();
                    if (xdg_light.readByte()==1){
                        System.out.println("TimeStamp = " + xdg_light.readDouble()+ " MAC = "+IEEEAddress.toDottedHex(xdg_light.readLong())+" command = "+ xdg_light.readByte() + " value = "+xdg_light.readBoolean());
                        xdg_movement=xdg_light;
                        conn_movement.send(xdg_light);
                        xdg_light.reset(); //delete the datagram for light sensor
                        xdg_movement.reset(); //delete the datagram for movement sensor

                    }
                    else {
                    xdg_sink=xdg_light; // write the data in datagram
                    conn_sink.send(xdg_sink); //send datagram to sink
                    System.out.println("TimeStamp = " + xdg_light.readDouble()+ " MAC = "+IEEEAddress.toDottedHex(xdg_light.readLong())+" command = "+ xdg_light.readByte() + " value = who knows");
                    xdg_sink.reset(); // delete the datagram for sink
                    xdg_light.reset(); //delete the datagram for light sensor
                        }

                    }
                     //recieve from movement sensor
                    if (conn_movement.packetsAvailable()==true){ //if message from movement sensor is available
                    conn_movement.receive(xdg_movement); //recieve message from movement sensor
                    System.out.println("TimeStamp = " + xdg_movement.readDouble()+ " MAC = "+IEEEAddress.toDottedHex(xdg_movement.readLong())+" command = "+ xdg_movement.readByte() + " value = who knows");
                    xdg_sink=xdg_movement; // write the string in datagram
                    conn_sink.send(xdg_sink); //send datagram to sink
                    xdg_sink.reset(); // delete the datagram for sink
                    xdg_movement.reset(); //delete datagram for movement sensor
                    }
                    
                    //recieve from sink
                    if (conn_sink.packetsAvailable()==true){ //if message from movement sensor is available
                    conn_movement.receive(xdg_sink); //recieve message from movement sensor
                    xdg_sink.readDouble();
                    conn_mote = (RadiogramConnection) Connector.open("radiogram://"+IEEEAddress.toDottedHex(xdg_sink.readLong())+":126"); //read the mac address of reciever
                    conn_mote.send(xdg_sink); //send datagram to mote
                    System.out.println("TimeStamp = " + xdg_sink.readDouble()+ " MAC = "+IEEEAddress.toDottedHex(xdg_sink.readLong())+" command = "+ xdg_sink.readByte() + " value = who knows ");
                    xdg_sink.reset(); // delete the datagram from sink
                    }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }
        }catch (IOException ex) {
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
