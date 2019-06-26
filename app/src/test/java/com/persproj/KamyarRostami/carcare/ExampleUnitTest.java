package com.persproj.KamyarRostami.carcare;

import com.persproj.KamyarRostami.carcare.DataModel.Users;

import org.junit.Test;
import org.mockito.Mock;

import java.net.PortUnreachableException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Users user ;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void userClassTest() {
           user=new Users("Kamyar","Rostami","kamyar21rostami@gmail.com","+989367236162");
           assertEquals(user.getEmail(),"kamyar21rostami@gmail.com");
    }
}