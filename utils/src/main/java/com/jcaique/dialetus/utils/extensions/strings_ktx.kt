package com.jcaique.dialetus.utils.extensions

import android.app.Activity
import android.content.ClipDescription
import androidx.core.app.ShareCompat
import java.text.Normalizer

private val REGEX_NORMALIZE = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun CharSequence.share(activity: Activity) =
    ShareCompat.IntentBuilder
        .from(activity)
        .setText(this)
        .setType(ClipDescription.MIMETYPE_TEXT_PLAIN)
        .startChooser()

fun CharSequence.normalize(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_NORMALIZE.replace(temp, "")
}
