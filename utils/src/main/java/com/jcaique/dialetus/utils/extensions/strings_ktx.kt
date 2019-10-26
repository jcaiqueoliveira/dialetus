package com.jcaique.dialetus.utils.extensions

import android.app.Activity
import android.content.ClipDescription
import androidx.core.app.ShareCompat

fun String.share(activity: Activity) =
    ShareCompat.IntentBuilder
        .from(activity)
        .setText(this)
        .setType(ClipDescription.MIMETYPE_TEXT_PLAIN)
        .startChooser()
