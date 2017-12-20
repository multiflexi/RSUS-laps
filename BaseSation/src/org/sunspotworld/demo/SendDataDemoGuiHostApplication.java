/*
 * DatabaseDemoHostApplication.java
 *
 * Copyright (c) 2008-2009 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package org.sunspotworld.demo;


import com.sun.spot.peripheral.ota.OTACommandServer;
import java.io.IOException;


/**
 * This application is the 'on Desktop' portion of the SendDataDemo. 
 * This host application collects sensor samples sent by the 'on SPOT'
 * portion running on neighboring SPOTs and graphs them in a window. 
 *   
 * @author Vipul Gupta
 * modified Ron Goldman
 */
public class SendDataDemoGuiHostApplication {
    // Broadcast port on which we listen for sensor samples
    
    private HomeFrame homeFrame;
    private void setup() throws IOException {
        
        

       homeFrame=new HomeFrame();
       homeFrame.pack();
       homeFrame.setLocationRelativeTo(null);
       homeFrame.setVisible(true);
    }
    
  
    
    private void run() throws Exception {
       
         Sink movementDetection=new Sink(130,"7f00.0101.0000.1001",homeFrame);
         Thread t =new Thread(movementDetection);
         t.start();
        /* Sink tresholdupdate=new Sink(121,"7f00.0101.0000.1002", homeFrame);
         Sink ping=new Sink(122,"7f00.0101.0000.1002", homeFrame);

         

         Thread t1=new Thread(tresholdupdate);
         t1.start();

          Thread t2=new Thread(ping);
          t2.start();*/
      

    }

        

        
      
        
       
    
    
    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) throws Exception {
        // register the application's name with the OTA Command server & start OTA running
     OTACommandServer.start("SendDataDemo-GUI");

        SendDataDemoGuiHostApplication app = new SendDataDemoGuiHostApplication();
        app.setup();
        app.run();
    }
}
