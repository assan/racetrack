package racetrack

class Race {
    static searchable = true
    String name
    Date startDate
    String city
    String state
    BigDecimal distance
    BigDecimal inMiles() {
        return distance * 0.6214
    }
    BigDecimal cost
    Integer maxRunners = 10000
    static hasMany = [registrations:Registration]
    String toString() {
        return "${name}, ${startDate.format('dd/MM/yyyy')}"
    }
    static constraints = {
        name(blank: false, maxSize:50)
        startDate(validator: {return(it > new Date())})
        city()
        state(inList: ["GA","NC","SC","VA"])
        distance(nullable: false, scale: 2, min: 0.0)
        cost(nullable: false, scale: 2, min: 0.0, max: 100.0)
        maxRunners(min:0, max: 100000)
    }
}
