package com.jcaique.utils.dataflow

interface UserInteraction {
    object OpenedScreen : UserInteraction
    object RequestedFreshContent : UserInteraction
}
