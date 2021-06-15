package com.zlyandroid.module.weather.event

class CloseFragmentEvent(var cityName: String) : BaseEvent() {

    companion object {
        fun postCloseFragmentEvent(mCityName: String) {
            CloseFragmentEvent(mCityName).post()
        }
    }
}