package tech.coderonin.spring.redis.cache;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.coderonin.spring.redis.cache.data.IPhoneData;
import tech.coderonin.spring.redis.cache.data.PhoneData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:spring/applicationContext.xml"
})
public class PhoneServiceTest {

    @Autowired
    PhoneService phoneService;

    @Before
    public void setUp(){
        assertNotNull( "Should autowire a non-null PhoneService", phoneService );
    }

    @Test
    public void defaultTest(){
        System.out.println( "==== START Phone Service Default Test ====" );

        IPhoneData newPhone = new PhoneData.PhoneDataBuilder()
            .phoneNumber("111")
        .build();

        System.out.println( "- Save New Phone Number");
        IPhoneData savedPhone = phoneService.save(newPhone);
        assertFalse( "Should return a new instance", savedPhone == newPhone );
        assertNotNull("Should return a non-null saved phone number", savedPhone );
        assertNotNull("Should return a saved phone number with non-null ID", savedPhone.getID() );
        System.out.println( "-- PASSED" );

        System.out.println( "- Retrieve Saved Phone Number" );
        IPhoneData retrievedPhone = phoneService.getById(savedPhone.getID());
        assertFalse( "Should return a new instance", savedPhone == retrievedPhone );
        assertNotNull("Should retrieve a non-null phone number", retrievedPhone );
        assertEquals("Should return the same saved phone number", savedPhone.getID(), retrievedPhone.getID() );
        assertEquals( "Retrieved phone should have the same phone number", savedPhone.getPhoneNumber(), retrievedPhone.getPhoneNumber() );
        System.out.println("-- PASSED" );

        System.out.println( "- Retrieve again Saved Phone Number" );
        IPhoneData reRetrievedPhone = phoneService.getById(savedPhone.getID());
        System.out.println( "-- PASSED" );

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        System.out.println( "- Retrieve again Saved Phone Number" );
        IPhoneData expiredRetrievePhone = phoneService.getById(savedPhone.getID());
        System.out.println("-- PASSED" );

        System.out.println("- Updated Phone Number");
        retrievedPhone.setCountryAccessCode("60");
        IPhoneData updatedPhone = phoneService.save(retrievedPhone);
        assertFalse( "Should return a new instance", retrievedPhone == updatedPhone );
        assertNotNull("Should saved a non-null phone number", updatedPhone );
        assertEquals( "Should return the same saved phone number",  retrievedPhone.getID(), updatedPhone.getID() );
        assertEquals( "Updated phone should have the same updated value", retrievedPhone.getCountryAccessCode(), updatedPhone.getCountryAccessCode());
        System.out.println( "-- PASSED" );

        System.out.println( "==== END Phone Service Default Test ====" );
    }

    @Test
    public void testCaching(){
        System.out.println( "==== START Test Caching ====" );

        IPhoneData newPhone = new PhoneData.PhoneDataBuilder()
            .phoneNumber("111")
        .build();

        System.out.println( "- Save New Phone" );
        IPhoneData saved = phoneService.save(newPhone);
        assertNotNull("Should return a non-null saved phone", saved);
        System.out.println( "-- SUCCESS" );

        System.out.println( "- Retrieve Phone" );
        IPhoneData retrieved = phoneService.getById(saved.getID());
        assertNotNull("Should return a non-null retrieved phone", retrieved );
        assertEqualPhones(saved, retrieved);
        System.out.println( "-- SUCCESS" );

        System.out.println( "- Retrieve Phone again" );
        IPhoneData secondRetrieval = phoneService.getById(saved.getID());
        assertNotNull( "Should return a non-null retrieved phone", secondRetrieval );
        assertEqualPhones( saved, secondRetrieval );
        System.out.println("-- SUCCESS" );

        System.out.println( "- Update Phone" );
        secondRetrieval.setCountryAccessCode("60");
        saved = phoneService.save(secondRetrieval);
        assertNotNull("Should return a non-null updated phone", saved);
        assertEqualPhones(secondRetrieval, saved);
        System.out.println("-- SUCCESS" );

        System.out.println( "- Retrieve Phone again" );
        IPhoneData thirdRetrieval = phoneService.getById(saved.getID());
        assertNotNull("Should return a non-null retrieved phone", thirdRetrieval );
        assertEqualPhones(saved, thirdRetrieval);
        System.out.println("-- SUCCESS" );

        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
        }

        System.out.println( "- Retrieve Phone again after 1s" );
        IPhoneData fourthRetrieval = phoneService.getById(saved.getID());
        assertNotNull( "Should return a non-null retrieved phone", fourthRetrieval );
        assertEqualPhones(saved, fourthRetrieval);
        System.out.println("-- SUCCESS");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        System.out.println( "- Retrieve Phone again after 1s" );
        IPhoneData fifthRetrieval = phoneService.getById(saved.getID());
        assertNotNull("Should return a non-null retrieved phone", fifthRetrieval );
        assertEqualPhones(saved, fifthRetrieval );
        System.out.println("-- SUCCESS" );

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}

        System.out.println( "- Retrieve Phone again after 1s" );
        IPhoneData sixthRetrieval = phoneService.getById(saved.getID());
        assertNotNull("Should return a non-null retrieved phone", sixthRetrieval );
        assertEqualPhones(saved, sixthRetrieval );
        System.out.println("-- SUCCESS" );

        System.out.println( "==== END Test Caching ====" );
    }

    protected void assertEqualPhones( IPhoneData expected, IPhoneData target ){
        if( expected == null ){
            assertNull("Should be null", target );
        } else {
            assertEquals( "Should have the expected ID", expected.getID(), target.getID() );
            assertEquals( "Should have the expected PhoneNumber", expected.getPhoneNumber(), target.getPhoneNumber() );
            assertEquals( "Should have the expected CountryAccessCode", expected.getCountryAccessCode(), target.getCountryAccessCode() );
            assertEquals( "Should have the expected extension", expected.getExtension(), target.getExtension() );
            assertEquals( "Should have the expected type", expected.getType(), target.getType() );
        }
    }
}
