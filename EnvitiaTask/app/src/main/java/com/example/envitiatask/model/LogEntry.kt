package com.example.envitiatask.model

data class LogEntry(
    val timestamp: String,
    val encryptedMessage: String,
    var decryptedMessage: String? = null
)
