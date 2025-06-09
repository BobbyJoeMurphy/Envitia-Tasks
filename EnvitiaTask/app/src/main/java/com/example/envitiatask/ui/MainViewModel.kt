package com.example.envitiatask.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.envitiatask.model.LogEntry
import com.example.envitiatask.utils.EncryptionHelper
import com.example.envitiatask.utils.FileHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {
    val logEntries = MutableLiveData<MutableList<LogEntry>>(mutableListOf())
    val formattedLog = MutableLiveData<String>()

    fun addEntry(plainMessage: String) {
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val encrypted = EncryptionHelper.encrypt(plainMessage)
        val newEntry = LogEntry(time, encrypted)
        logEntries.value?.add(newEntry)
        logEntries.value = logEntries.value
        updateFormattedLog()
    }

    fun saveEncryptedEntry(context: Context, plainMessage: String) {
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val encrypted = EncryptionHelper.encrypt(plainMessage)
        val entry = LogEntry(time, encrypted)

        logEntries.value?.add(entry)
        logEntries.value = logEntries.value

        FileHelper.writeToFile(context, "[$time] $encrypted")
        updateFormattedLog()
    }

    fun loadSavedLog(savedLog: String) {
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
            logEntries.value = entries.toMutableList()
            updateFormattedLog()
        }
    }

    fun decryptAll() {
        logEntries.value = logEntries.value?.map { entry ->
            if (entry.decryptedMessage == null) {
                try {
                    entry.decryptedMessage = EncryptionHelper.decrypt(entry.encryptedMessage)
                } catch (e: Exception) {
                    entry.decryptedMessage =  "Failed to decrypt"
                }
            }
            entry
        }?.toMutableList()
        updateFormattedLog()
    }

    fun clearEntries() {
        logEntries.value = mutableListOf()
        formattedLog.value = ""
    }

    private fun updateFormattedLog() {
        val entries = logEntries.value.orEmpty()
        val log = entries.joinToString("\n") {
            val displayMessage = it.decryptedMessage ?: "Encrypted"
            "[${it.timestamp}] $displayMessage"
        }
        formattedLog.value = log
    }
}
