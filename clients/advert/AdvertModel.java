package clients.advert;

import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import middle.OrderProcessing;

import javax.swing.*;
import java.util.Observable;
import java.awt.Image;
import java.text.DecimalFormat;



/**
 * Implements the Model of the collection client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class AdvertModel extends Observable {
    private String theAction = "";

    private String pn = "0001"; // Initial value for pn

    private OrderProcessing theOrder = null;
    private StockReadWriter theStock = null;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private AdvertView  view  = null;
    private  String messageDisplayedPrice = "";
    private String messageDisplayedRating = "";
    private String messageDisplayedFinal = "";
    private String pn2 = "";








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
        // Schedule a task to switch images and update text
        scheduler.schedule(() -> runTask(), 0, TimeUnit.SECONDS);
    }

    private void runTask() {
        // Update pn to switch between 0001 and 0009
        pn = getNextPn(pn);



        long InteractionBasedTime = 0;

        try {
            InteractionBasedTime = (1000 + (theStock.Interaction(pn)) * 1000);
        } catch (StockException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Delay: " + InteractionBasedTime);




        setChanged();
        notifyObservers(theAction);

        // Reschedule the task with the new InteractionBasedTime
        scheduler.schedule(() -> runTask(), InteractionBasedTime, TimeUnit.MILLISECONDS);
    }

    private String getNextPn(String currentPn) {
        int currentNumber = Integer.parseInt(currentPn);
        int nextNumber = (currentNumber < 7) ? currentNumber + 1 : 1;

        return String.format("%04d", nextNumber);
    }

    public String updateText(){
        try {
            Product pr = theStock.getDetails( pn );

            if (Float.isNaN(theStock.getRating(pn)) == true){
                messageDisplayedRating = "No Ratings";



            }else{
                messageDisplayedRating = (df.format((theStock.getRating( pn ))) + " Stars");


            }
            //messageDisplayedRating = (String.valueOf(theStock.getRating(pn) + " Stars!"));
            messageDisplayedPrice = String.valueOf(pr.getPrice());



            messageDisplayedFinal = ("ONLY £" + messageDisplayedPrice + "       " + messageDisplayedRating);




        } catch (StockException e) {
            throw new RuntimeException(e);
        }
        return(messageDisplayedFinal);

    }

    public String HotOrNot() {


        try {
            if( theStock.Interaction(pn) >= 5 ){
                return("HOT ITEM!");
            }
            else{
                return("");
            }


        } catch (StockException e) {
            throw new RuntimeException(e);
        }
    }
}

