package com.flightsearch.test;

import com.flightsearch.utils.DataGenerator;
import org.junit.Test;

import static org.junit.Assert.fail;

public class MainTest {

    @Test
    public void CheckIATACode(){
        System.out.println("Тест прогнан");
        if ("MOW".equals(DataGenerator.getIATA("Москва"))){
            fail("IATA code not matchs");
        }
    }
}
