package com.awsprep.user.utils

/**
 * Created by noweshedakram on 14/7/23.
 */
object AppConstant {

    // ------------------- Firebase Firestore -------------------
    // Collection Reference
    const val COLL_USER = "users"
    const val COLL_COURSES = "courses"
    const val COLL_CHAPTERS = "chapters"
    const val COLL_SECTIONS = "sections"
    const val COLL_QUESTIONS = "questions"
    const val COLL_TEST_RESULTS = "test_results"
    const val COLL_REVIEW_QUES = "review_ques"
    const val COLL_FEEDBACK = "feedback"

    // Storage Reference
    const val STORAGE_PROFILE_PIC = "profile_pic"

    // COLL_USER Field Reference
    const val FIELD_NAME = "name"
    const val FIELD_IMAGE_URL = "image"
    const val FIELD_EMAIL = "email"
    const val FIELD_PHONE = "phone"
    const val FIELD_ADDRESS = "address"
    const val FIELD_CREATED_AT = "createdAt"
    const val FIELD_UPDATED_AT = "updatedAt"

    const val FIELD_ORDER = "order"
    const val FIELD_ACTIVE = "is_active"

    const val STATUS_PASS = "Pass"
    const val STATUS_FAILED = "Failed"

    // Others
    const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

}