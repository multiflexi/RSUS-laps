package org.sunspotworld;

/*
 * NormalSensor.java
 *
 * Created on Nov 18, 2017, 7:35:53 PM
 *
 * To change this template, choose Tools | Template Manager and locate this template
 * under the Java Classes. You can then make changes to the template in the Source Editor.
 */




import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.util.IEEEAddress;
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
public class NorSensor extends MIDlet {

    private String ADDRESSAGGREGATING = "radiogram://7f00.0101.0000.1001:123";
    private String BROADCAST = "radiogram://:124";


    private ITriColorLEDArray leds = (ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class);
    private ISwitch sw1 = (ISwitch)Resources.lookup(ISwitch.class, "SW1");
    private RadiogramConnection connAgg = null;
    private RadiogramConnection connLight = null;
    private Radiogram xAgg, rAgg, lAgg;

    private boolean movement;
    private int movementPeriod = 1*1000;

    private boolean light;

    private boolean LEDStatus;
    private boolean prevStatus;

    private boolean specialState;
    private boolean LEDStatusRequired;

    Timer timer;


    public void runTimerMovement(int period){
            timer = new Timer();
            TimerTask task = new TimerTask() {

            public void run()
            {
                if(sw1.isClosed())
                    movement = true;
                else
                    movement = false;
            }
        };
            timer.schedule(task, 10, period);

    }

    public void stopTimer(){
        timer.cancel();
    }

    public void getLight(){
        try {
            long timeStamp = 0;
            long MAC = 0;
            byte command = 0;
            boolean value = false;

            if(connLight.packetsAvailable()){
                connLight.receive(lAgg);
                timeStamp = lAgg.readLong();
                MAC = lAgg.readLong();
                command = lAgg.readByte();
                value = lAgg.readBoolean();
                light = value;
                //System.out.println("TimeStamp = " + timeStamp+ " MAC = "+IEEEAddress.toDottedHex(MAC)+" command = "+ command + " value = " +value);
                lAgg.reset();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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
                    connAgg.receive(rAgg);
                    timeStamp = rAgg.readLong();
                    MAC = rAgg.readLong();
                    command = rAgg.readByte();
                    System.out.println("Command " + command);
                    switch (command){
                        case 1:
                            break;
                        case 2:
                            LEDStatusRequired = rAgg.readBoolean();
                            rAgg.reset();
                            break;

                        case 3:
                            rAgg.reset();
                            break;

                        case 4:
                            rAgg.reset();
                            break;

                        case 5:
                            Date d = new Date();
                            value = false;
                            xAgg = (Radiogram) connAgg.newDatagram(50);
                            xAgg.writeLong(d.getTime());
                            xAgg.writeLong(MAC);
                            xAgg.writeByte(5);
                            connAgg.send(xAgg);
                            xAgg.reset();
                            rAgg.reset();
                            break;

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
            connLight = (RadiogramConnection) Connector.open(BROADCAST);
            rAgg = (Radiogram) connAgg.newDatagram(50);
            lAgg = (Radiogram) connLight.newDatagram(50);

            runTimerMovement(movementPeriod);

            while(true){
                getLight();
                LEDManager();
                commandsManager();
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