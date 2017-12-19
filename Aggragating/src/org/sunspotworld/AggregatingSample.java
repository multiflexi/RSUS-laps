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
import java.util.Hashtable;
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
/*
    private RadiogramConnection connMove = null;
    private RadiogramConnection connLight = null;
    private RadiogramConnection connBroad = null;


    private Radiogram moveRad, lightRad, broadRad ;*/


    protected void startApp() throws MIDletStateChangeException {



       
        DatagramInfos tab[]=new DatagramInfos[6];

         for(int i=0;i<6;i++)
         {

               DatagramInfos receivedInfos=new DatagramInfos();
               receivedInfos.setAddress(Spot.getInstance().getRadioPolicyManager().getIEEEAddress());
               receivedInfos.setTime(System.currentTimeMillis());
               receivedInfos.setTest(false);
              receivedInfos.setCommand((byte)(i+1));
              tab[i]=receivedInfos;
         }


          CommandType cmdtype=new CommandType();
           
        ManageAggregating agg=new ManageAggregating(tab);

        // ComSInkManager comsink=new ComSInkManager( tab[0],130,cmdtype);

      Thread t=new Thread(agg);
     
         t.start();
       
     
     
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