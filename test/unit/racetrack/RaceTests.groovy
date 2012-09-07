package racetrack



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Race)
class RaceTests {

    void testInMiles() {
           def race = new Race(distance: 5.0)
       assertTrue 3.107 - race.inMiles() < 0.00000001
    }
}
