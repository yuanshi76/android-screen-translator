package com.galaxy.airviewdictionary.data.remote.firebase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

class RemoteConfigValue(private val raw: String) {
    fun asString(): String = raw
    fun asLong(): Long = raw.toLongOrNull() ?: 0L
    fun asBoolean(): Boolean = raw.equals("true", ignoreCase = true)
}

@Singleton
class RemoteConfigRepository @Inject constructor(@ApplicationContext val context: Context) {

    companion object PreferencesKeys {
        const val SERVICE_AVAILABLE_KEY = "service_available"
        const val LATEST_VERSION_CODE_KEY = "latest_version_code"
        const val FORCE_UPDATE_VERSION_CODE_KEY = "force_update_version_code"
        const val API_KEY_VERSION_AZURE = "api_key_version_azure"
        const val API_KEY_VERSION_DEEPL = "api_key_version_deepl"
        const val API_KEY_VERSION_PAPAGO = "api_key_version_papago"
        const val API_KEY_VERSION_YANDEX = "api_key_version_yandex"
        const val API_KEY_VERSION_CHATGPT = "api_key_version_chatgpt"
        const val TRIAL_TIME_LIMIT_MINUTE = "trial_time_limit_minute"
        const val FIXED_AREA_VIEW_CAMPAIGN_PERIOD_MINUTE = "fixed_area_view_campaign_period_minute"
        const val AD_UNIT_ID = "ad_unit_id"
    }

    private val _remoteConfigFlow = MutableStateFlow(
        mapOf(
            SERVICE_AVAILABLE_KEY to RemoteConfigValue("{\"default\":true}"),
            LATEST_VERSION_CODE_KEY to RemoteConfigValue("0"),
            FORCE_UPDATE_VERSION_CODE_KEY to RemoteConfigValue("0"),
            API_KEY_VERSION_AZURE to RemoteConfigValue("0"),
            API_KEY_VERSION_DEEPL to RemoteConfigValue("0"),
            API_KEY_VERSION_PAPAGO to RemoteConfigValue("0"),
            API_KEY_VERSION_YANDEX to RemoteConfigValue("0"),
            API_KEY_VERSION_CHATGPT to RemoteConfigValue("0"),
            TRIAL_TIME_LIMIT_MINUTE to RemoteConfigValue("0"),
            FIXED_AREA_VIEW_CAMPAIGN_PERIOD_MINUTE to RemoteConfigValue("10"),
            AD_UNIT_ID to RemoteConfigValue(""),
        )
    )

    val remoteConfigFlow: StateFlow<Map<String, RemoteConfigValue>> get() = _remoteConfigFlow
}
