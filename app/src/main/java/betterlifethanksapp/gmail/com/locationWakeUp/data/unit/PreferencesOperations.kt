package betterlifethanksapp.gmail.com.locationWakeUp.data.unit

class PreferencesOperations {



    fun getUnitEnumType(unitSystemValues:String): Unit {
        return if(unitSystemValues == "KM") {
            Unit.KM
        } else {
            Unit.MILES
        }
    }
}