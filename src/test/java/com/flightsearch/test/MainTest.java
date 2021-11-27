package com.flightsearch.test;

import com.flightsearch.TicketSearchAmadeusService;
import com.flightsearch.exeptions.NoTicketThisDataException;
import com.flightsearch.exeptions.WrongInputDataException;
import com.flightsearch.utils.DataGenerator;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.fail;

public class MainTest {

    @Test
    public void CheckIATACode(){
        if (!"MOW".equals(DataGenerator.getIATA("Москва"))){
            fail("IATA code not matchs");
        }
    }

    @Test
    public void CheckTicketReturn() {
        TicketSearchAmadeusService search = new TicketSearchAmadeusService();
        String origin = DataGenerator.getIATA("Москва");
        String destination = DataGenerator.getIATA("Казань");
        try {
            search.getOffers(origin, destination, LocalDate.now().plusDays(10), null);
        } catch (IOException e) {
            fail("Wrong Stream treatment or REST request failed");
        } catch (ParseException e) {
            fail("Wrong API parse");
        } catch (NoTicketThisDataException e) {
            fail("Wrong Data treatment");
        } catch (WrongInputDataException e) {
            fail("Wrong Data Input treatment");
        }

    }
}
