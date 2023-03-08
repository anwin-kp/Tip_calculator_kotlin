package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.calculateButton.setOnClickListener {
            calculateTip()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = getText(R.string.no_string)
            return
        }
        val selectedId = binding.tipOptions.checkedRadioButtonId
        val roundUp = binding.roundUpSwitch.isChecked
        val tipPercentage = when (selectedId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            R.id.option_fifteen_percent -> 0.15
            else -> 0.05
        }
        var tip = tipPercentage * cost
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)


    }
}