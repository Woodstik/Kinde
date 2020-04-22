package com.wstik.kinde.presentation.splash

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wstik.kinde.R
import com.wstik.kinde.data.enums.AuthState
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.presentation.auth.startAuthMain
import com.wstik.kinde.presentation.main.startMain
import com.wstik.kinde.presentation.rules.startRules
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.nextScreenState.observe(this, Observer { handleNextScreenState(it) })
        viewModel.checkAuthState()
    }

    private fun handleNextScreenState(state: LoadState<AuthState>) {
        when (state) {
            is LoadState.Data -> handleAuthState(state.data)
            is LoadState.Error -> Toast.makeText(this, R.string.error_auth_state, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleAuthState(authState: AuthState) {
        when (authState) {
            AuthState.NONE -> startAuthMain()
            AuthState.ACTIVE -> startRules()
            AuthState.COMPLETE -> startMain()
        }
    }
}
