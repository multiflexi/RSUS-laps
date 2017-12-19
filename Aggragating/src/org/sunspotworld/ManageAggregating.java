/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import javax.microedition.io.Connector;

/**
 *
 * @author userrsus
 */
public class ManageAggregating implements Runnable {
    
       private RadiogramConnection connMove = null;
    private RadiogramConnection connLight = null;
    private RadiogramConnection connBroad = null;
    private RadiogramConnection connToSink=null;
    private Radiogram moveRad, lightRad, broadRad,dgSink;
    private DatagramInfos [] receiveInfos  ;
   
    public ManageAggregating(DatagramInfos [] infos)
    {
        this.receiveInfos=infos;
     
    }
    public void sendInformation(DatagramInfos infos,Radiogram datagram,RadiogramConnection conn) throws IOException
    {

        datagram.writeLong(infos.getTime());
        datagram.writeLong(infos.getAddress());
        datagram.write(infos.getCommand());
        datagram.writeBoolean(infos.isTest());
        conn.send(datagram);
        datagram.reset();
    }
    public void SendLightInfos(DatagramInfos infos,Radiogram datagram,RadiogramConnection conn) throws IOException
    {

        datagram.writeLong(infos.getTime());
        datagram.writeLong(infos.getAddress());
        datagram.write(infos.getCommand());
        datagram.writeInt(infos.getValue());
        conn.send(datagram);
        datagram.reset();
    }
    public DatagramInfos getInformation(Radiogram datagram,RadiogramConnection conn) throws IOException
    {
        DatagramInfos receivedInfos=new DatagramInfos();
        conn.receive(datagram);
        receivedInfos.setTime(datagram.readLong());
        receivedInfos.setAddress(datagram.readLong());
        receivedInfos.setCommand(datagram.readByte());
       
        if(receivedInfos.getCommand()==3 || receivedInfos.getCommand()==4)
            receivedInfos.setValue(datagram.readInt());
        else if(receivedInfos.getCommand()==2 || receivedInfos.getCommand()==5)
           receivedInfos.setTest(datagram.readBoolean());
        datagram.reset();
        return receivedInfos;

    }
    public void communicationManager() throws IOException
    {
           

            connLight= (RadiogramConnection) Connector.open("radiogram://7f00.0101.0000.1002:122");
            connMove = (RadiogramConnection) Connector.open("radiogram://7f00.0101.0000.1003:123");
            connBroad =  (RadiogramConnection)Connector.open("radiogram://broadcast:124");
            connToSink=(RadiogramConnection)Connector.open("radiogram://:130");
            moveRad = (Radiogram) connMove.newDatagram(connMove.getMaximumLength());
            lightRad = (Radiogram) connLight.newDatagram(connLight.getMaximumLength());
            broadRad = (Radiogram) connBroad.newDatagram(connBroad.getMaximumLength());
            dgSink=(Radiogram)connToSink.newDatagram(connToSink.getNominalLength());
            System.out.println("Aggregating Running");
         
            while(true){

                 
                 if(connToSink.packetsAvailable())
                 {
                       connToSink.receive(dgSink);
                    byte cmd  =dgSink.readByte();
                    boolean typeDest=dgSink.readBoolean();
                    boolean typeMode=dgSink.readBoolean();
                       if(cmd==0)
                       {
                          System.out.println("Stimulation from BaseStation");
                       }
                 else if(cmd==2)
                     {             receiveInfos[1].setTest(typeMode);
                         sendInformation(receiveInfos[1],lightRad,connLight);
                         dgSink.reset();


                     }
                   else if(cmd==3)
                     {

                       sendInformation(receiveInfos[2],lightRad,connLight);
                       dgSink.reset();
                   }
                else if(cmd==4)
                     {
                        sendInformation(receiveInfos[3],lightRad, connLight);
                        dgSink.reset();
                      }
                else if(cmd==5)
                      {
                        sendInformation(receiveInfos[4],lightRad, connLight);
                        dgSink.reset();
                      }
                else if(cmd==6)
                       {
                        receiveInfos[5].setTest(typeMode);
                        sendInformation(receiveInfos[5],lightRad, connLight);
                        dgSink.reset();

                }
 
                 }
                if(connLight.packetsAvailable()){      

                    receiveInfos[0]=getInformation(lightRad,connLight);
              
                    if( receiveInfos[0].getCommand() == 1){


                          sendInformation(receiveInfos[0], broadRad, connBroad);
                     
                     }
                    else if(receiveInfos[0].getCommand()==2)
                     {
                      receiveInfos[1]=receiveInfos[0];
                      sendInformation(receiveInfos[1],dgSink, connToSink);
                      receiveInfos[1].afficher();
                      
                     }
                    
                     else if(receiveInfos[0].getCommand()==3)
                     {
                       receiveInfos[2]=receiveInfos[0];
                       SendLightInfos(receiveInfos[2],dgSink, connToSink);
                       receiveInfos[2].afficher();
                     }
                    
                     else if(receiveInfos[0].getCommand()==4)
                    {
                       receiveInfos[3]=receiveInfos[0];
                       SendLightInfos(receiveInfos[3],dgSink, connToSink);
                       receiveInfos[3].afficher();
                     }
                    
                    else if(receiveInfos[0].getCommand()==5)
                    {
                       
                      receiveInfos[4]=receiveInfos[0];
                      sendInformation(receiveInfos[4],dgSink, connToSink);
                      receiveInfos[4].afficher();
                    }
                     
                    else if(receiveInfos[0].getCommand()==6)
                    {
                       
                      receiveInfos[5]=receiveInfos[0];
                      sendInformation(receiveInfos[5],dgSink, connToSink);
                      receiveInfos[5].afficher();
                    }


                }

                 

          }




                  
                   
}

            
     
    

    public void run() {
        try {
            communicationManager();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }





}
