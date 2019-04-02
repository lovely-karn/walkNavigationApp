package com.lovely.walknavigationapp.data.network

import com.lovely.walknavigationapp.data.model.CommonResponse
import com.lovely.walknavigationapp.data.model.directionResult.DirectionResults

import retrofit2.Call
import retrofit2.http.*

/**
 *
 * The API interface for your application
 */

interface ApiInterface {

    companion object {

        //todo Declare your API endpoints here
        const val REVERSE_GEOCODE_PLACE_ID: String = "place/details/json";
        const val DIRECTIONS_API: String = "directions/json";
    }


    /**
     * REVERSE_GEOCODE_PLACE_ID : parameters ->
     *
     * @param map the map of params to go along with request
     * @return parsed common response object
     */
    @POST(REVERSE_GEOCODE_PLACE_ID)
    fun reverseGeocodeResultByPlaceId(@QueryMap map: HashMap<String, String>): Call<CommonResponse>

    /**
     * get the diresctions api
     * takes query as parameter
     * returns the directions result
     */
    @GET(DIRECTIONS_API)
    fun getDirectionResults(@QueryMap map: HashMap<String, String>): Call<DirectionResults>
}
