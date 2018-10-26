package com.cmput301f18t25.healthx;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the User class
 */

public class UserTest {

    public String test_name = "name";
    public String test_id = "id";
    public String test_phone_number = "phone";
    public String test_email = "email";
    public String test_status = "status";

    public UserTest(){}

    @Test
    public void testGetName(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        assertEquals(user.getName(),test_name);
    }

    @Test
    public void testSetName(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        String new_name = "new_name";
        user.setName(new_name);
        assertEquals(user.getName(),new_name);
    }

    @Test
    public void testGetUserId(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        assertEquals(user.getUserId(),test_id);

    }

    @Test
    public void testSetUserId(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        String new_id = "new_id";
        user.setUserId(new_id);
        assertEquals(user.getUserId(),new_id);
    }

    @Test
    public void testGetPhoneNumber(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        assertEquals(user.getPhoneNumber(),test_phone_number);

    }

    @Test
    public void testSetPhoneNumber(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        String new_number = "new_number";
        user.setPhoneNumber(new_number);
        assertEquals(user.getPhoneNumber(),new_number);
    }

    @Test
    public void testGetEmail(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        assertEquals(user.getEmail(),test_email);

    }

    @Test
    public void testSetEmail(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        String new_email = "new_email";
        user.setEmail(new_email);
        assertEquals(user.getEmail(),new_email);
    }

    @Test
    public void testGetReminderFrequency(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        assertNull(user.getReminderFrequency());

    }

    @Test
    public void testSetReminderFrequency(){
        User user = new User(test_name,test_id,test_phone_number,test_email,test_status);
        String new_freq = "new_freq";
        user.setReminderFrequency(new_freq);
        assertEquals(user.getReminderFrequency(),new_freq);
    }


}
