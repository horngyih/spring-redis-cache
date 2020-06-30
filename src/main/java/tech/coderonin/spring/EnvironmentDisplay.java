package tech.coderonin.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class EnvironmentDisplay {

    @Autowired
    Environment env;

    public void init(){
        displayActiveProfiles();
    }

    protected void displayActiveProfiles(){
        System.out.println( "Active Profiles :[" );
        if( env != null && env.getActiveProfiles() != null ){
            for( String profile : env.getActiveProfiles() ){
                System.out.println( profile );
            }
        }
        System.out.println( "]");
    }
}
