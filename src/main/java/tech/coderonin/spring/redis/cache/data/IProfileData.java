package tech.coderonin.spring.redis.cache.data;

public interface IProfileData {
    String getID();
    void setID( String id );

    INameData getNameData();
    void setNameData( INameData INameData);

    IPhoneData getPhoneData();
    void setPhoneData( IPhoneData IPhoneData);
}
