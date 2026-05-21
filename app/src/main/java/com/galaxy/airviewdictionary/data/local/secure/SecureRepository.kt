package com.galaxy.airviewdictionary.data.local.secure

import android.content.Context
import com.galaxy.airviewdictionary.data.AVDRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GMS/Play Integrity 미사용 빌드에서는 로컬 신뢰 상태만 사용한다.
 */
@Singleton
class SecureRepository @Inject constructor(@ApplicationContext val context: Context) : AVDRepository() {

    companion object {
        var VERDICT_APP_RECOGNITION_FAILED = false
    }

    private val _secureAssessmentInfoFlow = MutableStateFlow<SecureAssessmentInfo?>(null)

    val secureAssessmentInfoFlow: StateFlow<SecureAssessmentInfo?> get() = _secureAssessmentInfoFlow

    private fun initApiKeyInfo() = Unit

    private fun securityAssessment() {
        val isSecureStoreSupported = SecureStore.isSupported()
        Timber.tag(TAG).d("SecureStore.isSupported() : $isSecureStoreSupported")

        if (!isSecureStoreSupported) {
            _secureAssessmentInfoFlow.value = SecureAssessmentInfo(
                deviceInspection = DeviceInspection.KEYSTORE_NOT_AVAILABLE,
            )
            return
        }

        launchInAVDCoroutineScope {
            if (VERDICT_APP_RECOGNITION_FAILED) {
                _secureAssessmentInfoFlow.value = SecureAssessmentInfo(
                    verdictAppRecognition = VerdictAppRecognition.UNEVALUATED,
                )
            } else {
                applyLocalAssessment()
            }
        }
    }

    private fun applyLocalAssessment() {
        // In non-GMS builds, skip remote integrity verdicts and use local default assessment (all fields null/unevaluated).
        _secureAssessmentInfoFlow.value = SecureAssessmentInfo()
    }

    fun playIntegrity(
        apiKeyVersionAzure: Int = 1,
        apiKeyVersionDeepl: Int = 1,
        apiKeyVersionPapago: Int = 1,
        apiKeyVersionYandex: Int = 1,
        apiKeyVersionChatgpt: Int = 1,
        retry: Boolean = false
    ) {
        applyLocalAssessment()
    }

    fun increaseTrialCount(): Int {
        val trialCount = getTrialCount() + 1
        SecureStore.set(context, SecureStoreKey.TRANSLATE_TRIAL_COUNT, trialCount.toString())
        return trialCount
    }

    fun getTrialCount(): Int {
        return SecureStore.get(context, SecureStoreKey.TRANSLATE_TRIAL_COUNT)?.get()?.toInt() ?: 0
    }

    init {
        Timber.tag(TAG).i("=========================== SecureRepository ==========================")
        initApiKeyInfo()
        securityAssessment()
    }

    override fun onZeroReferences() = Unit
}
