package com.jcaique.presentation.utils.dataflow

internal interface UserInteraction {
    object OpenedScreen : UserInteraction
    object RequestedFreshContent : UserInteraction
}
