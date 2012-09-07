package racetrack

class Registration {


   static belongsTo = [race:Race, runner:Runner]
    Boolean paid
    Date dateCreated
    static constraints = {
    race()
    runner()
    paid()
    dateCreated()
    }
}
