package com.example.shultetable.firebase

import com.example.shultetable.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

const val LINK_KEY = "link"
const val STATUS_KEY = "status"

class FirebaseHandler {
    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun getPairInfo(): Pair<String, Boolean> {
        fetchValues()
        return Pair(remoteConfig.getString(LINK_KEY), remoteConfig.getBoolean(STATUS_KEY))
    }

    private fun fetchValues() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
    }

}