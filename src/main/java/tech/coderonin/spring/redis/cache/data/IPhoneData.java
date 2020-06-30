package tech.coderonin.spring.redis.cache.data;

import java.io.Serializable;

public interface IPhoneData extends Cloneable, Serializable {
    String getID();
    void setID( String id );

    String getCountryAccessCode();
    void setCountryAccessCode( String countryAccessCode );

    String getPhoneNumber();
    void setPhoneNumber( String phoneNumber );

    String getExtension();
    void setExtension( String extension );

    String getType();
    void setType( String phoneType );

    IPhoneData clone();
}
