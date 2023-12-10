package clients.advert;

import clients.Picture;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReadWriter;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class AdvertView implements Observer {
    private static final String COLLECT = "Collect";
    private static final int H = 300;       // Height of window pixels
    private static final int W = 450;       // Width  of window pixels
    private final JLabel theAction = new JLabel();
    private final JTextArea theOutput = new JTextArea();

    private final JTextArea theHotItem = new JTextArea();
    private Picture thePicture = new Picture(300, 200);

    private Picture theHotPicture = new Picture(100, 100);
    private OrderProcessing theOrder = null;
    private AdvertController cont = null;

    private StockReadWriter theStock = null;



    public AdvertView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            theOrder = mf.makeOrderProcessing();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        Container cp = rpc.getContentPane();
        Container rootWindow = (Container) rpc;
        cp.setLayout(null);
        rootWindow.setSize(W, H);
        rootWindow.setLocation(x, y);

        Font f = new Font("Monospaced", Font.PLAIN, 12);

        theAction.setBounds(110, 25, 270, 20);
        theAction.setText("");
        cp.add(theAction);

        thePicture.setBounds(50, 70, 300, 200);
        cp.add(thePicture);
        thePicture.clear();

        theHotItem.setBounds(160,50,90, 30 );
        theHotItem.setEditable(true);
        theHotItem.setFont(new Font("Ariel", Font.BOLD, 18));
        theHotItem.setForeground(Color.RED);
        cp.add(theHotItem);


        theOutput.setBounds(50, 20, 300, 30);
        theOutput.setEditable(true);
        theOutput.setFont(new Font("Ariel", Font.BOLD, 18));
        cp.add(theOutput);
    }



    public AdvertView(MiddleFactory mf) {
        try {
            theOrder = mf.makeOrderProcessing();
            theStock = mf.makeStockReadWriter();
        } catch (Exception e) {
            DEBUG.error("%s\n%s",
                    "CollectModel.constructor\n%s",
                    e.getMessage());
        }
    }

    public void setController(AdvertController c) {
        cont = c;
    }

    @Override
    public void update(Observable modelC, Object arg) {

        AdvertModel model = (AdvertModel) modelC;
        String message = (String) arg;

        // Get the picture
        ImageIcon image = model.getPicture(thePicture.getWidth(), thePicture.getHeight());

         // Display theAction in theOutput
        theOutput.setText(model.updateText());

        theHotItem.setText(model.HotOrNot());

        // Display the picture and update theOutput
        if (image == null) {
            thePicture.clear();

        } else {
            thePicture.set(image);




        }

        if (image == null) {
            theHotPicture.clear();

        } else {
            theHotPicture.set(image);




        }
    }


}