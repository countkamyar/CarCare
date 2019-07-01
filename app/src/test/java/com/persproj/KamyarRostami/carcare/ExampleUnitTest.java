package com.persproj.KamyarRostami.carcare;

import com.persproj.KamyarRostami.carcare.CarView.MileageCalculator;
import com.persproj.KamyarRostami.carcare.DataModel.Cars;
import com.persproj.KamyarRostami.carcare.DataModel.Users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    private Users user;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void userClassTest() {
        user = new Users("Kamyar", "Rostami", "kamyar21rostami@gmail.com", "+989367236162");
        assertEquals(user.getEmail(), "kamyar21rostami@gmail.com");
    }

    @Mock
    Cars cars;
    @Captor
    private ArgumentCaptor<String> argumentCaptor;

    @Test
    public void testCars() {
        when(cars.getDate()).thenReturn("1807");
        String s = cars.getDate();
        verify(cars, times(1)).getDate();
        assertEquals("1807", s);
        cars.setModel("bmw");
        verify(cars).setModel(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), "bmw");
    }

    @Mock
    MileageCalculator mileageCalculator;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    public void testmileage() {
        when(mileageCalculator.mileageCalculator(5000, "Audi", 1)).thenReturn(true);
        Boolean b = mileageCalculator.mileageCalculator(5000, "Audi", 1);
        String URL = mileageCalculator.carUrl("Samand");
        assertEquals(true, b);
        verify(mileageCalculator).carUrl(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(),"Samand");
        verify(mileageCalculator,times(1)).carUrl(anyString());
    }
}