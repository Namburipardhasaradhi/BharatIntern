package com.example.tempconvertor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var editInput: EditText? = null
    private var textResult: TextView? = null
    private var textResultType: TextView? = null
    private var selectType: Spinner? = null // Change ImageView to Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editInput = findViewById(R.id.editInput)
        textResult = findViewById(R.id.textResult)
        textResultType = findViewById(R.id.textResultType)
        selectType = findViewById(R.id.selectType)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.temperature_units,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectType?.adapter = adapter

        selectType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                calculateTemperature()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

        editInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                calculateTemperature()
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })
    }

    private fun calculateTemperature() {
        val inputText = editInput?.text.toString()
        if (!inputText.isEmpty()) {
            val inputValue = inputText.toDouble()
            val selectedUnit = selectType?.selectedItem.toString()
            val result: Double
            if (selectedUnit == getString(R.string.fahrenheit)) {
                // Convert from Fahrenheit to Celsius
                result = (inputValue - 32) * 5 / 9
                textResultType?.setText(R.string.celsius)
            } else {
                result = inputValue * 9 / 5 + 32
                textResultType?.setText(R.string.fahrenheit)
            }
            textResult?.text = String.format("%.2f", result)
        } else {
            textResult?.text = ""
        }
    }
}
