/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HomeFrame.java
 *
 * Created on Dec 5, 2017, 1:19:41 PM
 */

package org.sunspotworld.demo;

import java.io.IOException;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;




/**
 *
 * @author userrsus
 */
public class HomeFrame extends javax.swing.JFrame {

    /** Creates new form HomeFrame */
   

   
    private ListofDataInfos list=new ListofDataInfos();
    private ContentPanel panelMovement;
    private  ContentPanel  panelRequest;
    private  String destAdress;
    private boolean  detection;
    private int treshold;
    private boolean pingDetection,lightStatusDetection,modeDetection,ledControlDetection,typeMode;
    public HomeFrame() throws IOException {

      
       list.initializer();
       JScrollPane scrolpane=new JScrollPane(ListAddress);
       scrolpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
       initComponents();
       panelMovement=new ContentPanel();
       panelMovement.setName("Movement Detection");
       panelRequest=new ContentPanel();
      panelRequest.setName("Request Information");
      TabbedPane.add(panelMovement);
      TabbedPane.add(panelRequest);
      ListAddress.setSelectedIndex(0);
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListAddress = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        TabbedPane = new javax.swing.JTabbedPane();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setName("home"); // NOI18N

        ListAddress.setModel(list);
        ListAddress.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListAddressValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(ListAddress);

        jLabel1.setText("Center Collection");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                        .addGap(54, 54, 54))
                    .addComponent(TabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)))
        );

        jButton2.setText("PING");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Treshold");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Light Status");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Mode");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        status.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
        status.setForeground(java.awt.Color.red);
        status.setText("Auto");

        jButton5.setText("ON/OFF");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(40, 40, 40)
                        .addComponent(jButton2)
                        .addGap(34, 34, 34)
                        .addComponent(jButton3)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ListAddressValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListAddressValueChanged
        // TODO add your handling code here:
       if(ListAddress.getSelectedIndex()!=-1)
        setDestAdress(ListAddress.getSelectedValue().toString());
   
             
    }//GEN-LAST:event_ListAddressValueChanged


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

    
         setDetection(true);
     

        
          
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        setPingDetection(true);


        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setLightStatusDetection(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
       if(status.getText().equals("Auto"))
       {
           status.setText("Manual");
         setTypeMode(true);
       }
       else {
            status.setText("Auto");
              setTypeMode(false);
        }
       setModeDetection(true);


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        setLedControlDetection(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
    * @param args the command line arguments
    */
    /*
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeFrame().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList ListAddress;
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the panelMovement
     */
    public ContentPanel getPanelMovement() {
        return panelMovement;
    }

    /**
     * @param panelMovement the panelMovement to set
     */
    public void setPanelMovement(ContentPanel panelMovement) {
        this.panelMovement = panelMovement;
    }

    /**
     * @return the panelRequest
     */
    public ContentPanel getPanelRequest() {
        return panelRequest;
    }

    /**
     * @param panelRequest the panelRequest to set
     */
    public void setPanelRequest(ContentPanel panelRequest) {
        this.panelRequest = panelRequest;
    }

    /**
     * @return the detection
     */
    public boolean  getDetection() {
        return detection;
    }

    /**
     * @param detection the detection to set
     */
    public void setDetection(boolean  detection) {
        this.detection = detection;
    }

    /**
     * @return the destAdress
     */
    public String getDestAdress() {
        return destAdress;
    }

    /**
     * @param destAdress the destAdress to set
     */
    public void setDestAdress(String destAdress) {
        this.destAdress = destAdress;
    }

    /**
     * @return the treshold
     */
    public int getTreshold() {
        return treshold;
    }

    /**
     * @param treshold the treshold to set
     */
    public void setTreshold(int treshold) {
        this.treshold = treshold;
    }

    /**
     * @return the pingDetection
     */
    public boolean isPingDetection() {
        return pingDetection;
    }

    /**
     * @param pingDetection the pingDetection to set
     */
    public void setPingDetection(boolean pingDetection) {
        this.pingDetection = pingDetection;
    }

    /**
     * @return the lightStatusDetection
     */
    public boolean isLightStatusDetection() {
        return lightStatusDetection;
    }

    /**
     * @param lightStatusDetection the lightStatusDetection to set
     */
    public void setLightStatusDetection(boolean lightStatusDetection) {
        this.lightStatusDetection = lightStatusDetection;
    }

    /**
     * @return the modeDetection
     */
    public boolean isModeDetection() {
        return modeDetection;
    }

    /**
     * @param modeDetection the modeDetection to set
     */
    public void setModeDetection(boolean modeDetection) {
        this.modeDetection = modeDetection;
    }

    /**
     * @return the ledControlDetection
     */
    public boolean isLedControlDetection() {
        return ledControlDetection;
    }

    /**
     * @param ledControlDetection the ledControlDetection to set
     */
    public void setLedControlDetection(boolean ledControlDetection) {
        this.ledControlDetection = ledControlDetection;
    }

    /**
     * @return the typeMode
     */
    public boolean isTypeMode() {
        return typeMode;
    }

    /**
     * @param typeMode the typeMode to set
     */
    public void setTypeMode(boolean typeMode) {
        this.typeMode = typeMode;
    }

   
}
