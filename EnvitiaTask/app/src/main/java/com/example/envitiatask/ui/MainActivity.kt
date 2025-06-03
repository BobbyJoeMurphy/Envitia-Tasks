package com.example.envitiatask.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.envitiatask.R
import com.example.envitiatask.databinding.ActivityMainBinding
import com.example.envitiatask.model.LogEntry
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

        viewModel.logEntries.observe(this) { entries ->
            val log = entries.joinToString("\n") { "[${it.timestamp}] ${it.message}" }
            logText.text = log
            clearButton.isEnabled = entries.isNotEmpty()
        }
        binding.okButton.isEnabled = false

        binding.inputField.addTextChangedListener {
            binding.okButton.isEnabled = it.toString().isNotBlank()
        }

        binding.okButton.setOnClickListener {
            val message = binding.inputField.text.toString()
            viewModel.addEntry(message)
            binding.inputField.text.clear()
        }

        clearButton.setOnClickListener {
            viewModel.logEntries.value = mutableListOf()
            FileHelper.clearFile(this)
        }

        okButton.setOnClickListener {
            val message = inputField.text.toString().trim()
            if (message.isNotEmpty()) {
                viewModel.addEntry(message)
                FileHelper.writeToFile(
                    this, "[${
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                            Date()
                        )
                    }] $message"
                )
                inputField.setText("")
            }
        }
        val savedLog = FileHelper.readFromFile(this)
        if (savedLog.isNotBlank()) {
            val entries = savedLog.lines()
                .filter { it.isNotBlank() }
                .map { line ->
                    val match = Regex("""\[(.*?)] (.+)""").find(line)
                    if (match != null) {
                        val (time, message) = match.destructured
                        LogEntry(time, message)
                    } else {
                        LogEntry("--", line)
                    }
                }
            viewModel.logEntries.value = entries.toMutableList()
        }
    }
}
