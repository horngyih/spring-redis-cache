package tech.coderonin.spring.redis.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.coderonin.spring.redis.cache.data.IPhoneData;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static tech.coderonin.spring.redis.cache.Utility.and;
import static tech.coderonin.spring.redis.cache.Utility.isEmpty;

@Service("phoneService")
public class PhoneServiceImpl implements PhoneService {

    AtomicInteger currentId = new AtomicInteger(1);
    Map<String, IPhoneData> phoneStore = new TreeMap<String, IPhoneData>();

    @Cacheable(value="phones",key="#id", condition="#id!=null")
    public IPhoneData getById(String id) {
        System.out.println( "Get By ID" );
        if(!isEmpty(id)){
            IPhoneData retrieved = phoneStore.get(id);
            if( retrieved != null ){
                return retrieved.clone();
            }
        }
        return null;
    }

    public List<IPhoneData> search(IPhoneData searchCriteria) {
        System.out.println( "Search" );
        List<IPhoneData> results = new LinkedList<IPhoneData>();
        if(searchCriteria != null){
            for(IPhoneData entry : phoneStore.values()){
                if(and(
                    (searchCriteria.getCountryAccessCode()!=null)?searchCriteria.getCountryAccessCode().equals(entry.getCountryAccessCode()):true,
                    searchCriteria.getPhoneNumber()!=null?searchCriteria.getPhoneNumber().equals(entry.getPhoneNumber()):true,
                    searchCriteria.getExtension()!=null?searchCriteria.getExtension().equals(entry.getExtension()):true
                )){
                    results.add(entry.clone());
                }
            }
        }
        return results;
    }

    @CacheEvict(value="phones", key="#phone.ID", condition = "#phone.ID != null")
    public IPhoneData save(IPhoneData phone) {
        if(phone!=null){
            IPhoneData savePhone = phone.clone();
            if(isEmpty(savePhone.getID())){
                savePhone.setID(""+currentId.getAndIncrement());
            }
            phoneStore.put( savePhone.getID(), savePhone.clone());
            return savePhone;
        }
        return null;
    }

    @CacheEvict(value="phones", key="#phone.ID")
    public IPhoneData delete(IPhoneData phone) {
        if( phone != null && !isEmpty(phone.getID()) ){
            phoneStore.remove(phone.getID());
        }
        return null;
    }
}
