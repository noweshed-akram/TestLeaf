package com.awsprep.user.utils

/**
 * Created by noweshedakram on 14/7/23.
 */
object AppConstant {

    // ------------------- Firebase Firestore -------------------
    // Collection Reference
    const val COLL_USER = "users"
    const val COLL_HISTORY = "history"
    const val COLL_COURSES = "courses"
    const val COLL_CHAPTERS = "chapters"
    const val COLL_SECTIONS = "sections"

    // Storage Reference
    const val STORAGE_PROFILE_PIC = "profile_pic"

    // COLL_USER Field Reference
    const val FIELD_NAME = "name"
    const val FIELD_IMAGE_URL = "image"
    const val FIELD_EMAIL = "email"
    const val FIELD_PHONE = "phone"
    const val FIELD_ACTIVE = "isActive"
    const val FIELD_LAT = "lat"
    const val FIELD_LNG = "lng"
    const val FIELD_ADDRESS = "address"
    const val FIELD_CREATED_AT = "createdAt"
    const val FIELD_UPDATED_AT = "updatedAt"

    // Others
    const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

}