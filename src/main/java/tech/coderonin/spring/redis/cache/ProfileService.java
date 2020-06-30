package tech.coderonin.spring.redis.cache;

import tech.coderonin.spring.redis.cache.data.IProfileData;

import java.util.List;

public interface ProfileService {
    IProfileData getById(IProfileData profile );
    List<IProfileData> getList(IProfileData searchCriteria);
    IProfileData save(IProfileData profile);
    IProfileData delete(IProfileData profile);
}
