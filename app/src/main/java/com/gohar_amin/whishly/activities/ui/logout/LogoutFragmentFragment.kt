package com.gohar_amin.whishly.activities.ui.logout

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gohar_amin.whishly.R
import com.gohar_amin.whishly.activities.LoginActivity

class LogoutFragmentFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.logout_dailog, null, false)
        val btnCancel: Button =view.findViewById(R.id.btnCancel)
        val btnLogout: Button =view.findViewById(R.id.btnOk)
        btnLogout.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        btnCancel.setOnClickListener {
            view.findNavController().navigate(R.id.action_nav_logout_to_homeFragment)
        }
        return view
    }
}