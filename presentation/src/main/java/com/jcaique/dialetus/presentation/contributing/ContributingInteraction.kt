package com.jcaique.dialetus.presentation.contributing

internal sealed class ContributingInteraction(val url: String) {
    object OpenAndroid : ContributingInteraction(ContributingConst.ANDROID)
    object OpenWeb : ContributingInteraction(ContributingConst.WEB)
}
