package betterlifethanksapp.gmail.com.locationWakeUp.data.unit

class UnitSettings(var unit:Unit) {

    lateinit var unitName: String
    var multiplierUnit: Float = 0.0f

    init {
        if(unit == Unit.KM)
        {
            setKmValues()
        }
        else
        {
            setMilesValues()
        }
    }

    private fun setMilesValues() {
        unitName = "miles"
        multiplierUnit = 0.000621f
    }

    private fun setKmValues() {
        unitName = "km"
        multiplierUnit = 0.001f
    }


}
