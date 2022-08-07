package com.example.dryogeshbatra.fragments.userSettings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dryogeshbatra.R

class userSettingsFragment : Fragment() {

    companion object {
        fun newInstance() = userSettingsFragment()
    }

    private lateinit var viewModel: UserSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserSettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}