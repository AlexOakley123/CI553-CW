package clients.advert;

/**
 * The Collection Controller
 * @author M A Smith (c) June 2014
 */

public class AdvertController
{
    private AdvertModel model = null;
    private AdvertView  view  = null;

    /**
     * Constructor
     * @param model The model
     * @param view  The view from which the interaction came
     */
    public AdvertController( AdvertModel model, AdvertView view )
    {
        this.view  = view;
        this.model = model;
    }

    /**
     * Collect interaction from view
     * @param orderNum The order collected
     */

}


