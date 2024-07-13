package com.leodemo.taipei_tour_compose.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import com.leodemo.taipei_tour_compose.domain.model.AttractionInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object AttractionInfoType : NavType<AttractionInfo>(isNullableAllowed = false) {
    override fun put(bundle: Bundle, key: String, value: AttractionInfo) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): AttractionInfo? {
        return BundleCompat.getParcelable(bundle, key, AttractionInfo::class.java)
    }

    override fun parseValue(value: String): AttractionInfo {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: AttractionInfo): String {
        return Uri.encode(Json.encodeToString(value))
    }
}