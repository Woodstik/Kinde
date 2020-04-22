package com.wstik.kinde.presentation.inbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wstik.kinde.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class InboxFragment : Fragment() {

    companion object {
        fun newInstance() = InboxFragment()
    }

    private val viewModel: InboxViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }
}
