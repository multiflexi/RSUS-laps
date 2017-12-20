/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.demo;

import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.util.IEEEAddress;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;

/**
 *
 * @author userrsus
 */
public class Sink  implements  Runnable{
    
    private RadiogramConnection recvConn = null;
     private Radiogram dg;

    private int port;
    private int flag;
    private DataInfos infos;
    private String address;
     private HomeFrame homeframe;
    
        public Sink(int port,String addres,HomeFrame homeframe)
    {
       this.port=port;
       infos=new DataInfos();
   
       this.address=addres;
       this.homeframe=homeframe;
      }




  

   public void connnexion()
    {

           try {

            recvConn = (RadiogramConnection) Connector.open("radiogram://"+this.address+":"+port);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            setDg((Radiogram) recvConn.newDatagram(recvConn.getMaximumLength()));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public void sendCommandToAgg(byte cmd,boolean typeDest,boolean typeMode,RadiogramConnection conn,Radiogram dg) throws IOException
    {
        dg.write(cmd);
        dg.writeBoolean(typeDest);
        dg.writeBoolean(typeMode);
        conn.send(dg);
        dg.reset();
    }

    public void recvDataMvtDetect() throws IOException
    {
      String message="";


         message+=">: waiting for Time of movement detection \n";
     boolean start=false;
       
        while(true)
        {

                     if(start==false)
                     {
                         sendCommandToAgg((byte)0,false,false,getRecvConn(),getDg());
                         start=true;
                     }


                     if(homeframe.getDetection())
                     {
                         sendCommandToAgg((byte)3,false,false,getRecvConn(),getDg());
                     
                         homeframe.setDetection(false);
                     }
                    else if(homeframe.isLightStatusDetection())
                     {
                        sendCommandToAgg((byte)4,false,false,getRecvConn(),getDg());
                        homeframe.setLightStatusDetection(false);

                     }
                    else if(homeframe.isPingDetection())
                     {
                         if(homeframe.getDestAdress().substring(homeframe.getDestAdress().length()-1).equals("2"))
                        sendCommandToAgg((byte)5,false,false,getRecvConn(),getDg());
                         else
                         sendCommandToAgg((byte)5,true,false,getRecvConn(),getDg());

                        
                        homeframe.setPingDetection(false);
                       
                     }
                     else if(homeframe.isModeDetection())
                     {
                         if(homeframe.getDestAdress().substring(homeframe.getDestAdress().length()-1).equals("2"))
                         sendCommandToAgg((byte)6,false,homeframe.isTypeMode(),getRecvConn(),getDg());
                         else
                          sendCommandToAgg((byte)6,true,homeframe.isTypeMode(),getRecvConn(),getDg());
                         homeframe.setModeDetection(false);

                     }
                     else if(homeframe.isLedControlDetection())
                     {
                          if(homeframe.getDestAdress().substring(homeframe.getDestAdress().length()-1).equals("2"))
                         sendCommandToAgg((byte)2,false,homeframe.isTypeMode(),getRecvConn(),getDg());
                          else
                            sendCommandToAgg((byte)2,true,homeframe.isTypeMode(),getRecvConn(),getDg());
                         homeframe.setLedControlDetection(false);
                     }

                 if(getRecvConn().packetsAvailable())
                 {

                getRecvConn().receive(getDg());
                infos.setReceiveTime(getDg().readLong()) ;

               Date date=new Date(infos.getReceiveTime());
               message+=">: [Received Date ] "+date.toString()+" \n";
               /* the read the address of the sending mote*/
               infos.setAddress(getDg().readLong());

              message+=">: [Address of the sending mote is ] ["+IEEEAddress.toDottedHex(infos.getAddress())+"] \n";
              infos.setCodeCmd(getDg().readByte());

              message+=">: Command receive is ["+infos.getCodeCmd()+"] \n";
                if(infos.getCodeCmd()==2)
              {

               /* read the status of the light either it was off or on*/
              infos.setTest(getDg().readBoolean());
               message+=">:The status of the light has changed  ";
            
                  
                   this.homeframe.getPanelMovement().addText(message);

              }
               else if(infos.getCodeCmd()==3)
                {
                   infos.setValue(getDg().readInt());
                   message+=">: the current treshol is "+infos.getValue();
                   this.homeframe.getPanelRequest().addText(message);


               }
                else if(infos.getCodeCmd()==4)
                {
                        infos.setValue(getDg().readInt());
                        message+=">: the current light status   is "+infos.getValue();
                        this.homeframe.getPanelRequest().addText(message);
                }
                else if(infos.getCodeCmd()==5)
                {

                         infos.setTest(getDg().readBoolean());
                         if(infos.isTest())
                        message+=">: the Mote is working ";
                        else
                        message+=">:Unreachable mote";

                        this.homeframe.getPanelRequest().addText(message);

                }
             else
              {
                     this.homeframe.getPanelMovement().addText("Wrong command");
               }

                 getDg().reset();
                 message="";
            }
            
        }
    }



  public void run() {

      if(this.port==130)
      {
            try {
                connnexion();
                recvDataMvtDetect();
            } catch (IOException ex) {

                Logger.getLogger(Sink.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

               
   
 }

 
 

    
  
   

    /**
     * @return the recvConn
     */
    public RadiogramConnection getRecvConn() {
        return recvConn;
    }

    /**
     * @param recvConn the recvConn to set
     */
    public void setRecvConn(RadiogramConnection recvConn) {
        this.recvConn = recvConn;
    }

    /**
     * @return the dg
     */
    public Radiogram getDg() {
        return dg;
    }

    /**
     * @param dg the dg to set
     */
    public void setDg(Radiogram dg) {
        this.dg = dg;
    }






}
