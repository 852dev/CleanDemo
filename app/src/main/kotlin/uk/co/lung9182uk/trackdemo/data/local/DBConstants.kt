package uk.co.lung9182uk.trackdemo.data.local


object DBConstants {

    const val TABLE_PLACE = "place"
    const val QUERY_PLACE = "SELECT * FROM $TABLE_PLACE ORDER BY create_time DESC LIMIT 1"

}