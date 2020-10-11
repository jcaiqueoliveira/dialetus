package com.jcaique.dialetus.presentation.ktx

import android.widget.EditText

internal val EditText.value: String
    get() = text?.toString().orEmpty()
