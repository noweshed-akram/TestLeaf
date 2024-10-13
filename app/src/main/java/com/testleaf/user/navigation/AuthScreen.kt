package com.testleaf.user.navigation

/**
 * Created by noweshedakram on 23/6/23.
 */
sealed class AuthScreen(
    val route: String
) {

    object Intro : AuthScreen(route = "intro_screen")

    object EmailSignIn : AuthScreen(route = "email_sign_in_screen")

    object ForgotPassword : AuthScreen(route = "forgot_pass_screen")

    object EmailRegistration : AuthScreen(route = "email_reg_screen")

    object CheckEmail : AuthScreen(route = "check_email_screen")

}