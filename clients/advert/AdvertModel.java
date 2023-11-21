package clients.advert;

import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.*;
import clients.advert.AdvertView;


import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Image;


/**
 * Implements the Model of the collection client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class AdvertModel extends Observable {
    private String theAction = "";
    private String theOutput = "";
    private String pn = "0001"; // Initial value for pn
    private OrderProcessing theOrder = null;
    private StockReadWriter theStock = null;

    public AdvertModel(MiddleFactory mf) {
        try {
            theOrder = mf.makeOrderProcessing();
            theStock = mf.makeStockReadWriter();
            runAdverts();
        } catch (Exception e) {
            DEBUG.error("%s\n%s",
                    "CollectModel.constructor\n%s",
                    e.getMessage());
        }
    }

    public ImageIcon getPicture(int width, int height) {
        try {
            ImageIcon originalIcon = theStock.getImage(pn);
            Image originalImage = originalIcon.getImage();

            // Scale the image to fit the dimensions of thePicture
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Create a new ImageIcon from the scaled image
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            DEBUG.error("CustomerModel.constructor\n" +
                    "Error getting image for pn %s\n%s\n", pn, e.getMessage());
            return null;
        }
    }

    public void runAdverts() {
        // Schedule a task to switch images and update text every 5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Update pn to switch between 0001 and 0009
                pn = getNextPn(pn);

                // Get details and update theAction
                Product product = null;
                try {
                    product = theStock.getDetails(pn);
                } catch (StockException e) {
                    throw new RuntimeException(e);
                }
                if (product.getQuantity() >= 1) { // Check if product is in stock
                    theAction = String.format("%s : %7.2f (%2d)",
                            product.getDescription(),
                            product.getPrice(),
                            product.getQuantity());
                } else {
                    theAction = "Product not in stock";
                }

                setChanged();
                notifyObservers(theAction);
            }
        }, 0, 5000); // Start immediately and repeat every 5 seconds
    }

    private String getNextPn(String currentPn) {
        int currentNumber = Integer.parseInt(currentPn);
        int nextNumber = (currentNumber % 9) + 1; // Loop back to 1 after 9
        return String.format("%04d", nextNumber);
    }


}

