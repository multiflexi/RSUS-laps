/*
 * NormalSensor.java
 *
 * Created on Nov 18, 2017, 7:35:53 PM
 *
 * To change this template, choose Tools | Template Manager and locate this template
 * under the Java Classes. You can then make changes to the template in the Source Editor.
 */

package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
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
public class LigSensor extends MIDlet {

    private String ADDRESSAGGREGATING = "radiogram://7f00.0101.0000.7700:123";

    private ITriColorLEDArray leds = (ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class);
    private ISwitch sw1 = (ISwitch)Resources.lookup(ISwitch.class, "SW1");
    private ILightSensor lightSensor = (ILightSensor)Resources.lookup(ILightSensor.class);

    private RadiogramConnection connAgg = null;
    private Radiogram xAgg, rAgg;


    private boolean movement;
    private int movementPeriod = 1*1000;

    private int lightMeasure;
    private boolean light;
    private int lightPeriod = 1*1000;
    private int threshold = 400;

    private boolean LEDStatus;
    private boolean prevStatus;

    private boolean specialState;
    private boolean LEDStatusRequired;

    Timer timerMovement;
    Timer timerLight;


    public void runTimerMovement(int period){
            timerMovement = new Timer();
            TimerTask task = new TimerTask() {

            public void run()
            {
                if(sw1.isClosed())
                    movement = true;
                else
                    movement = false;
            }
        };
            timerMovement.schedule(task, 10, period);

    }

    public void stopTimerMovement(){
        timerMovement.cancel();
    }

    public void runTimerLight(int period){
            timerLight = new Timer();
            TimerTask task = new TimerTask() {

            public void run()
            {
                try {
                    lightMeasure = lightSensor.getAverageValue();

                    if(lightMeasure < threshold)
                        light = true;
                    else
                        light = false;

                    Date d = new Date();
                    xAgg = (Radiogram) connAgg.newDatagram(50);
                    xAgg.writeLong(d.getTime());
                    xAgg.writeLong(Spot.getInstance().getRadioPolicyManager().getIEEEAddress());
                    xAgg.writeByte(1);
                    xAgg.writeBoolean(light);
                    connAgg.send(xAgg);
                    xAgg.reset();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
            timerLight.schedule(task, 10, period);

    }

    public void stopTimerLight(){
        timerLight.cancel();
    }


    public void LEDManager(){

    if(!specialState){

            if(light && movement ){
                leds.setColor(LEDColor.GREEN);
                leds.setOn();
                LEDStatus = true;
            }
             else{
                leds.setOff();
                LEDStatus = false;
             }
        }
        else{
            if(LEDStatusRequired){
                leds.setColor(LEDColor.GREEN);
                leds.setOn();
                LEDStatus = true;
            }
            else{
                leds.setOff();
                LEDStatus = false;
        }
      }

        if(LEDStatus != prevStatus){
            prevStatus = LEDStatus;
            try {
                Date d = new Date();
                xAgg = (Radiogram) connAgg.newDatagram(50);
                xAgg.writeLong(d.getTime());
                xAgg.writeLong(Spot.getInstance().getRadioPolicyManager().getIEEEAddress());
                xAgg.writeByte(2);
                xAgg.writeBoolean(LEDStatus);
                connAgg.send(xAgg);
                xAgg.reset();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }


    }

    public void commandsManager(){

        try{
            long timeStamp = 0;
            long MAC = 0;
            byte command = 0;
            boolean value = false;
            if(connAgg.packetsAvailable()){
                    timeStamp = rAgg.readLong();
                    MAC = Spot.getInstance().getRadioPolicyManager().getIEEEAddress();
                    command = rAgg.readByte();
                    Date d = new Date();

                    switch (command){
                        case 1:
                            xAgg = (Radiogram) connAgg.newDatagram(50);
                            xAgg.writeLong(d.getTime());
                            xAgg.writeLong(MAC);
                            xAgg.writeByte(1);
                            xAgg.writeBoolean(light);
                            connAgg.send(xAgg);
                            xAgg.reset();
                            break;

                        case 2:
                            LEDStatus = rAgg.readBoolean();
                            if(LEDStatus){
                                leds.setColor(LEDColor.GREEN);
                                leds.setOn();
                            }
                            else{
                                leds.setOff();
                            }
                            break;

                        case 3:
                            xAgg = (Radiogram) connAgg.newDatagram(50);
                            xAgg.writeLong(d.getTime());
                            xAgg.writeLong(MAC);
                            xAgg.writeByte(3);
                            xAgg.writeInt(threshold);
                            connAgg.send(xAgg);
                            xAgg.reset();
                            break;

                        case 4:
                            xAgg = (Radiogram) connAgg.newDatagram(50);
                            xAgg.writeLong(d.getTime());
                            xAgg.writeLong(MAC);
                            xAgg.writeByte(4);
                            xAgg.writeInt(lightMeasure);
                            connAgg.send(xAgg);
                            xAgg.reset();
                            break;

                        case 5:
                            xAgg = (Radiogram) connAgg.newDatagram(50);
                            xAgg.writeLong(d.getTime());
                            xAgg.writeLong(MAC);
                            xAgg.writeByte(5);
                            xAgg.reset();

                        case 6:
                            specialState = xAgg.readBoolean();
                            break;
                    }
            }

         } catch (IOException ex) {
                ex.printStackTrace();
         }
    }

    protected void startApp() throws MIDletStateChangeException {

        try {
            movement = false;
            light = false;
            LEDStatus = false;
            prevStatus = false;
            specialState = false;
            LEDStatusRequired = false;

            connAgg = (RadiogramConnection) Connector.open(ADDRESSAGGREGATING);
            rAgg = (Radiogram) connAgg.newDatagram(50);
            runTimerMovement(movementPeriod);
            runTimerLight(lightPeriod);

            while(true){
                LEDManager();
                //commandsManager();
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