package tech.coderonin.spring.redis.cache.data;

public interface INameData {
    String getID();
    void setID( String id );

    String getTitle();
    void setTitle( String title );

    String getFirstName();
    void setFirstName();

    String getLastName();
    void setLastName();
}
