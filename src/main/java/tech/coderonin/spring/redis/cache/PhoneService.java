package tech.coderonin.spring.redis.cache;

import tech.coderonin.spring.redis.cache.data.IPhoneData;

import java.util.List;

public interface PhoneService {
    IPhoneData getById(String id );
    List<IPhoneData> search(IPhoneData searchCriteria);
    IPhoneData save(IPhoneData phone);
    IPhoneData delete(IPhoneData phone);
}
