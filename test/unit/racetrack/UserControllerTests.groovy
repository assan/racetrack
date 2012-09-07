package racetrack
import grails.test.mixin.Mock
import org.codehaus.groovy.grails.plugins.codecs.*
import grails.test.mixin.TestFor



@TestFor(UserController)
@Mock(User)


class UserControllerTests {

    protected  void setUp() {
        super.setUp()
        String.metaClass.encodeAsBase64 = {->
            Base64Codec.encode(delegate)
        }
        String.metaClass.encodeAsSHA = {->
            SHA1Codec.encode(delegate)
        }
    }
    def populateValidParams(params) {
        assert params != null

        //params["name"] = 'someValidName'
        params.login = 'user'
        params.password = 'user'.encodeAsSHA()
        params.role = 'user'
    }
    void testAuthenticate() {

        def jdoe = new User(login: "jdoe", password: "password".encodeAsSHA(), role: "user")
        mockDomain(User, [jdoe])
      //  controller.save()
        controller.params.login = 'jdoe'
        controller.params.password = 'password'
        controller.params.role = 'user'

        controller.authenticate()
        assertNotNull controller.session.user
        assertEquals "jdoe", controller.session.user.login



    }
    void testAuthenticateBadUser() {
        def jdoe = new User(login: "jdoe", password: "password".encodeAsSHA(), role: "user")
        mockDomain(User, [jdoe])

        controller.params.login = 'jdoe'
        controller.params.role = 'user'
        controller.params.password= 'foo'
        controller.authenticate()
        assertTrue controller.flash.message.startsWith("Sorry, jdoe")
    }
    void testIndex() {
        controller.index()
        assert "/user/list" == response.redirectedUrl

    }

    void testList() {

        def model = controller.list()

        assert model.userInstanceList.size() == 0
        assert model.userInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.userInstance != null
    }


    void testSave() {
        controller.save()

        assert model.userInstance != null
        assert view == '/user/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/user/show/1'
        assert controller.flash.message != null
        assert User.count() == 1
    }

    void testShow() {
    def jdoe = new User(login:"jdoe", password: "jdoe", role: "user")
    def suziq = new User(login: "suziq", password: "suziq", role:"user")
    mockDomain(User,[jdoe,suziq])
        controller.params.id =2
        def map = controller.show()
        assertEquals "suziq", map.userInstance.login

        controller.show()

      //  assert flash.message != null
       // assert response.redirectedUrl == '/user/list'


        populateValidParams(params)
        def user = new User(params)

        assert user.save() != null

        params.id = user.id

        def model = controller.show()

        assert model.userInstance == user
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/user/list'


        populateValidParams(params)
        def user = new User(params)

        assert user.save() != null

        params.id = user.id

        def model = controller.edit()

        assert model.userInstance == user
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/user/list'

        response.reset()


        populateValidParams(params)
        def user = new User(params)

        assert user.save() != null

        // test invalid parameters in update
        params.id = user.id
        //TODO: add invalid values to params object


        controller.update()

        assert view == '/user/edit'
        assert model.userInstance != null

        user.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/user/show/$user.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        user.clearErrors()

        populateValidParams(params)
        params.id = user.id
        params.version = -1
        controller.update()

        assert view == "/user/edit"
        assert model.userInstance != null
        assert model.userInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/user/list'

        response.reset()

        populateValidParams(params)
        def user = new User(login: "user", password:"user", role: "user")

        assert user.save() != null
        assert User.count() == 1

        params.id = user.id

        controller.delete()

        assert User.count() == 0
        assert User.get(user.id) == null
        assert response.redirectedUrl == '/user/list'
    }


}
