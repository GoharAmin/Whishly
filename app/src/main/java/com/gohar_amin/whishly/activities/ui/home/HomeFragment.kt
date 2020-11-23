package com.gohar_amin.whishly.activities.ui.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gohar_amin.whishly.R
import com.gohar_amin.whishly.activities.CameraActivity
import com.gohar_amin.whishly.adapter.CategoryAdapter
import com.gohar_amin.whishly.callback.ActionCallback
import com.google.ar.core.ArCoreApk
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.listener.PermissionDeniedResponse

import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest

import com.karumi.dexter.listener.single.PermissionListener





class HomeFragment : Fragment(), ActionCallback {


    lateinit var recyclerview: RecyclerView
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.home_fragment, container, false)
        recyclerview = root.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = GridLayoutManager(context, 2)
        categoryAdapter = CategoryAdapter(requireContext(), this)
        recyclerview.adapter = categoryAdapter
        return root
    }

    override fun onResume() {
        super.onResume()

    }
    override fun onSuccess() {
        maybeEnableArButton()
    }

    override fun onError(msg: String) {
    }

    fun maybeEnableArButton() {
        val availability = ArCoreApk.getInstance().checkAvailability(context)
        if (availability.isTransient) {
            // Re-query at 5Hz while compatibility is checked in the background.
            Handler().postDelayed(Runnable { maybeEnableArButton() }, 200)
        }
        if (availability.isSupported) {
            //mArButton.setVisibility(View.VISIBLE)
            //mArButton.setEnabled(true)
            // indicator on the button.
            moveToCameraActivity()
        } else { // Unsupported or unknown.
            Toast.makeText(context, "Your device does not support this feature", Toast.LENGTH_SHORT)
                .show()
            //mArButton.setVisibility(View.INVISIBLE)
            //mArButton.setEnabled(false)
        }
    }

    private fun moveToCameraActivity() {

        Dexter.withContext(context)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    startActivity(Intent(context,CameraActivity::class.java))
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    response.requestedPermission
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).check()
    }
}