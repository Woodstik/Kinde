package com.wstik.kinde.di

import com.wstik.kinde.presentation.auth.forgotpassword.ForgotPasswordViewModel
import com.wstik.kinde.presentation.auth.login.LoginViewModel
import com.wstik.kinde.presentation.auth.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
}
