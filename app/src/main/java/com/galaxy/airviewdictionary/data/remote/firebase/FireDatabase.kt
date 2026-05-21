package com.galaxy.airviewdictionary.data.remote.firebase

import android.content.Context
import com.android.billingclient.api.Purchase
import com.galaxy.airviewdictionary.data.local.secure.DeviceActivityLevel
import com.galaxy.airviewdictionary.data.local.secure.VerdictAppLicensing
import com.galaxy.airviewdictionary.data.local.secure.VerdictAppRecognition
import com.galaxy.airviewdictionary.data.local.secure.VerdictDeviceRecognition
import com.galaxy.airviewdictionary.data.local.secure.VerdictPlayProtect
import com.galaxy.airviewdictionary.data.local.vision.TextDetectMode
import com.galaxy.airviewdictionary.data.remote.ai.CorrectionKitType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * GMS/Firebase 미사용 빌드에서는 원격 보고를 비활성화한다.
 */
object FireDatabase {

    fun badIntegrityReport(
        verdictAppRecognition: VerdictAppRecognition,
        verdictDeviceRecognition: VerdictDeviceRecognition,
        deviceActivityLevel: DeviceActivityLevel,
        verdictAppLicensing: VerdictAppLicensing,
        verdictPlayProtect: VerdictPlayProtect
    ) = Unit

    fun secureReport(eventDetail: String) = Unit

    fun screenViewReport(className: String) = Unit

    fun settingsReport(
        dockDelay: String,
        haptic: String,
        menuTransparency: String,
        menuComposition: String,
        transTransparency: String,
        closeDelay: String,
        replyTransparency: String,
        correctionKit: String,
        autoTTS: String,
        TTSRate: String,
    ) = Unit

    fun translationReport(
        transaction: com.galaxy.airviewdictionary.data.remote.translation.Transaction,
        textDetectMode: TextDetectMode?,
        correctionKitType: CorrectionKitType?
    ) = Unit

    fun replyReport(transaction: com.galaxy.airviewdictionary.data.remote.translation.Transaction) = Unit

    fun hoursTakenReport(trialCount: Int, hour: Int) = Unit

    fun daysTakenReport(trialCount: Int, day: Int) = Unit

    fun purchaseReport(context: Context, purchase: Purchase) = Unit

    fun formatTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        return format.format(date)
    }
}
