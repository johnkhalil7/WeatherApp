package com.johnk.weatherapp.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnk.weatherapp.R

import kotlinx.android.synthetic.main.main_fragment.*

private lateinit var viewModel: MainViewModel
private lateinit var results: String

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null


    companion object {
        fun newInstance() = MainFragment()
    }

    var activityCallback: ButtonListener? = null

    interface ButtonListener {
        fun getData(): String
        fun getSevenData(): String
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activityCallback = context as ButtonListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                    context.toString()
                            + " must implement ButtonListener"
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        convertButton.setOnClickListener {
            results = activityCallback?.getData().toString()

            resultText.text = results
        }
        sevenDayButton.setOnClickListener {
            results = activityCallback?.getSevenData().toString()
            resultText.setMovementMethod(ScrollingMovementMethod())
            resultText.setText(results)
        }
    }

}

