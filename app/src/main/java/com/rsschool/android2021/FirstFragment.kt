package com.rsschool.android2021

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var min : EditText? = null
    private var max : EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        min = view.findViewById(R.id.min_value)
        // TODO: val max = ...
        max = view.findViewById(R.id.max_value)

        generateButton?.isEnabled = false

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                generateButton?.isEnabled = dataValid()
            }

        }

        min?.addTextChangedListener(watcher)
        max?.addTextChangedListener(watcher)

        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            if (activity is FragmentNavigator) {
                val bundle = Bundle()
                bundle.putInt(MIN_VALUE_KEY, min?.text.toString().toInt())
                bundle.putInt(MAX_VALUE_KEY, max?.text.toString().toInt())
                (activity as FragmentNavigator).moveForward(bundle)
            }

        }
    }

    private fun dataValid() : Boolean {
        val minValue = min?.text.toString()
        val maxValue = max?.text.toString()

        return checkValue(maxValue)
                && checkValue(minValue)
                && minValue.toInt() <= maxValue.toInt()
    }

    private fun checkValue(value : String) : Boolean{
        val regex = Regex("\\d+")

        if (value.isEmpty()) return false
        if (!value.matches(regex)) return false

        try {
            val intValue = value.toInt()
            if (intValue < 0 || intValue > Int.MAX_VALUE) return false
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            return false
        }

        return true
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
        @JvmField val MIN_VALUE_KEY = this::class.java.name + ".MIN_VALUE"
        @JvmField val MAX_VALUE_KEY = this::class.java.name + ".MAX_VALUE"
    }
}