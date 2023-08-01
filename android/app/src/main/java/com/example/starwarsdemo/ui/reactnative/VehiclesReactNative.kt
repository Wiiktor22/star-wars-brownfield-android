package com.example.starwarsdemo.ui.reactnative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starwarsdemo.R
import com.facebook.react.ReactFragment

class VehiclesReactNative : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vehicles_react_native, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val reactNativeFragment = ReactFragment.Builder()
            .setComponentName("VehiclesRNScreen")
            .setFabricEnabled(false)
            .build()

        parentFragmentManager
            .beginTransaction()
            .add(R.id.vehicles_react_native, reactNativeFragment)
            .commit()
    }

    private fun getLaunchOptions(message: String) = Bundle().apply {
        putString("message", message)
    }
}