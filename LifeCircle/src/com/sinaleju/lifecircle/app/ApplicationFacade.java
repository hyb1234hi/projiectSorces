package com.sinaleju.lifecircle.app;

import org.puremvc.java.patterns.facade.Facade;

public class ApplicationFacade extends Facade{
    //
    private static ApplicationFacade instance = null;

    /**
     * 
     * @return 
     */
    public static ApplicationFacade getInstance(){
        if( instance == null) instance = new ApplicationFacade ();
        return instance ;
    }

	
}
