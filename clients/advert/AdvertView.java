package clients.advert;

import clients.Picture;
import middle.MiddleFactory;
import middle.OrderProcessing;

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
    private static final int W = 400;       // Width  of window pixels
    private final JLabel theAction = new JLabel();
    private final JTextArea theOutput = new JTextArea();
    private Picture thePicture = new Picture(300, 200);
    private OrderProcessing theOrder = null;
    private AdvertController cont = null;

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

        thePicture.setBounds(50, 50, 300, 200);
        cp.add(thePicture);
        thePicture.clear();

        theOutput.setBounds(50, 260, 300, 30);
        theOutput.setEditable(false);
        cp.add(theOutput);
    }

    public void imageUpdate(ImageIcon image) {
        System.out.println(image);
        thePicture.set(image);
    }

    public void setController(AdvertController c) {
        cont = c;
    }

    @Override
    public void update(Observable modelC, Object arg) {
        System.out.println("dskjcdskvsd");
        AdvertModel model = (AdvertModel) modelC;
        String message = (String) arg;

        // Get the picture
        ImageIcon image = model.getPicture(thePicture.getWidth(), thePicture.getHeight());
        theOutput.setText(message); // Display theAction in theOutput

        // Display the picture and update theOutput
        if (image == null) {
            thePicture.clear();
        } else {
            thePicture.set(image);
        }
    }
}