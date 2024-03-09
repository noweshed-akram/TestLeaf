package com.awsprep.user.domain.models

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by Md. Noweshed Akram on 9/3/24.
 * noweshed@gmail.com
 */
@Keep
data class Acronyms(
    var docId: String = "",
    var name: String = "",
    var phrase: String = ""
) : Serializable
