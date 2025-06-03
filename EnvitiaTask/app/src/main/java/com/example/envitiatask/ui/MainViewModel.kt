package com.example.envitiatask.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.envitiatask.model.LogEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {
    val logEntries = MutableLiveData<MutableList<LogEntry>>(mutableListOf())

    fun addEntry(message: String) {
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val newEntry = LogEntry(time, message)
        logEntries.value?.add(newEntry)
        logEntries.value = logEntries.value
    }

    fun clearEntries() {
        logEntries.value = mutableListOf()
    }
}
