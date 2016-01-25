package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestClient.class,
        TestPeriod.class,
        TestRoom.class,
        TestReservation.class,
        TestHotel.class
})
public class JunitTestSuite {
}