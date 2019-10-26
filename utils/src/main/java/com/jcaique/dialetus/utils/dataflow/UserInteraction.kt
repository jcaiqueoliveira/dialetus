package com.jcaique.dialetus.utils.dataflow

interface UserInteraction {
    object OpenedScreen : UserInteraction
    object RequestedFreshContent : UserInteraction
}
