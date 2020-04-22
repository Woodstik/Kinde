package com.wstik.kinde.di

import com.wstik.kinde.presentation.auth.forgotpassword.ForgotPasswordViewModel
import com.wstik.kinde.presentation.auth.login.LoginViewModel
import com.wstik.kinde.presentation.auth.signup.SignUpViewModel
import com.wstik.kinde.presentation.home.HomeViewModel
import com.wstik.kinde.presentation.inbox.InboxViewModel
import com.wstik.kinde.presentation.profile.ProfileViewModel
import com.wstik.kinde.presentation.rules.RulesViewModel
import com.wstik.kinde.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { RulesViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { InboxViewModel() }
    viewModel { ProfileViewModel() }
}
