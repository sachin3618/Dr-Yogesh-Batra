package com.example.dryogeshbatra.fragments.userChat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dryogeshbatra.R

class userChatFragment : Fragment() {

    companion object {
        fun newInstance() = userChatFragment()
    }

    private lateinit var viewModel: UserChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}