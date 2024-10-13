package com.testleaf.user.domain.models

import androidx.annotation.Keep
import java.io.Serializable


/**
 * Created by Md. Noweshed Akram on 9/3/24.
 * noweshed@gmail.com
 */
@Keep
data class Definition(
    var docId: String = "",
    var word: String = "",
    var explanation: String = ""
) : Serializable
