/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.demo;


import com.sun.spot.util.IEEEAddress;
import java.util.ArrayList;
import javax.swing.AbstractListModel;


/**
 *
 * @author userrsus
 */
public class ListofDataInfos extends AbstractListModel {

    String []tabAdres={"7f00.0101.0000.1002","7f00.0101.0000.1003"};
    private ArrayList<DataInfos> listElements;
    public ListofDataInfos()
    {
          listElements=new ArrayList<DataInfos>();
    }
    public void initializer()
    {
        for(int i=0;i<tabAdres.length;i++)
        {
        DataInfos d=new DataInfos();
        d.setAddress(IEEEAddress.toLong(tabAdres[i]));
        addElement(d);
        }
     
    }
  
    public int getSize() {
       return listElements.size();
    }

    public Object getElementAt(int index) {

        return listElements.get(index);
    }
    public void addElement(DataInfos infos)
    {
        listElements.add(infos);
        fireContentsChanged(this, 0, listElements.size());
    }

}
