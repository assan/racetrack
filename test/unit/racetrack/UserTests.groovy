package racetrack



import grails.test.mixin.*
import org.junit.*
import grails.test.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserTests {

    void testSimpleConstraints() {
        mockForConstraintsTests(User)
        def user = new User(login: "someone",
                            password: "blah",
                            role: "SuperUser")
        //oops role should be either 'admin' or 'user'
        //will the validation pick that up?
        assert !user.validate()
        assertEquals "inList", user.errors["role"]
    }
    void testUniqueConstraint() {
        def jdoe = new User(login: "jdoe", password: "jdoe", role: "user")
        def admin = new User(login:  "admin", password: "admin", role: "admin")

        mockDomain(User, [jdoe,admin])
        def badUser = new User(login: "jdoe")
        badUser.save()
        assertEquals 2, User.count()
        assertEquals true, badUser.errors.hasFieldErrors("login")

        def goodUser = new User(login: "good", password: "password", role: "user")
        goodUser.save()
        assertEquals 3, User.count ()
        assertNotNull User.findByLoginAndPassword ("good", "password")
    }
}