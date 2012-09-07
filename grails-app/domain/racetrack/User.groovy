package racetrack

class User {
    String login
    String password
    String role = "user"
    static constraints = {
        login(blank: false, unique: true, nullable: false)
        password(blank: false, password:true)
        role(inList: [ "admin" , "user"])    }
    static transients = ['admin']
    boolean isAdmin(){
        return role == "admin"
    }
    def beforeInsert = {
        password = password.encodeAsSHA()
    }
    String toString (){
        login
       }
}
