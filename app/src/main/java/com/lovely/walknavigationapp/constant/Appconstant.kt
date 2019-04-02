package com.lovely.walknavigationapp.constant

// intent extras

class Appconstant {

    companion object {

        const val INTENT_GEOLOCATION_DATA: String = "intent_geolocationdata"
        const val INTENT_ORIGIN: String = "intent_origin"
        const val INTENT_DESTINATION: String = "intent_destination"
        const val INTENT_WAYPOINTS: String = "intent_waypoints"

        const val FOUR_THOUSAND: Long = 4000L
        const val TWENTY_THOUSAND: Long = 20000L

        const val ORIGIN_CLICKED: Int = 1;
        const val DESTINATION_CLICKED: Int = 2;
        const val WAYPOINTS_CLICKED: Int = 3;
    }
}