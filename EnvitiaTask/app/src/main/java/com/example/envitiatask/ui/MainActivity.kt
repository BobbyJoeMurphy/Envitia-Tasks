package com.example.envitiatask.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.envitiatask.R
import com.example.envitiatask.databinding.ActivityMainBinding
import com.example.envitiatask.model.LogEntry
import com.example.envitiatask.utils.EncryptionHelper
import com.example.envitiatask.utils.FileHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val inputField = binding.inputField
        val logText = binding.logText
        val okButton = binding.okButton
        val clearButton = binding.clearButton

        binding.decryptButton.setOnClickListener {
            viewModel.decryptAll()
        }

        viewModel.formattedLog.observe(this) { log ->
            logText.text = log
        }

        viewModel.logEntries.observe(this) {
            clearButton.isEnabled = it.isNotEmpty()
        }

        binding.okButton.isEnabled = false

        binding.inputField.addTextChangedListener {
            binding.okButton.isEnabled = it.toString().isNotBlank()
        }

        viewModel.logEntries.observe(this) {
            clearButton.isEnabled = it.isNotEmpty()
            binding.decryptButton.isEnabled = viewModel.hasUndecryptedEntries()
        }

        okButton.setOnClickListener {
            val message = inputField.text.toString().trim()
            if (message.isNotEmpty()) {
                viewModel.saveEncryptedEntry(this, message)
                inputField.setText("")
            }
        }

        clearButton.setOnClickListener {
            viewModel.clearEntries()
            FileHelper.clearFile(this)
        }

        viewModel.loadSavedLog(FileHelper.readFromFile(this))
    }
}
