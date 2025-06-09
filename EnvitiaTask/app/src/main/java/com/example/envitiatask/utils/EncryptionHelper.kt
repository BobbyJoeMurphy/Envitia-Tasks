package com.example.envitiatask.utils

import android.util.Base64
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionHelper {

    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private const val AES = "AES"
    private const val STATIC_KEY_STRING = "b14ca5898a4e4133"

    private val key: SecretKey by lazy {
        KeyGenerator.getInstance(AES).apply {
            init(128)
        }.generateKey()
    }

    private val keyStatic: SecretKey by lazy {
        SecretKeySpec(STATIC_KEY_STRING.toByteArray(Charsets.UTF_8), AES)
    }

    init {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(BouncyCastleProvider())
        }
    }

    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION, "BC")
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)
        val encrypted = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encrypted, Base64.DEFAULT)
    }

    fun decrypt(encryptedText: String): String {
        val bytes = Base64.decode(encryptedText, Base64.DEFAULT)
        val iv = bytes.copyOfRange(0, 16)
        val encrypted = bytes.copyOfRange(16, bytes.size)
        val cipher = Cipher.getInstance(TRANSFORMATION, "BC")
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        return String(cipher.doFinal(encrypted), Charsets.UTF_8)
    }

    fun encryptKeyStatic(text: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION, "BC")
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, keyStatic, ivSpec)
        val encrypted = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encrypted, Base64.DEFAULT)
    }

    fun decryptKeyStatic(encryptedText: String): String {
        val bytes = Base64.decode(encryptedText, Base64.DEFAULT)
        val iv = bytes.copyOfRange(0, 16)
        val encrypted = bytes.copyOfRange(16, bytes.size)
        val cipher = Cipher.getInstance(TRANSFORMATION, "BC")
        cipher.init(Cipher.DECRYPT_MODE, keyStatic, IvParameterSpec(iv))
        return String(cipher.doFinal(encrypted), Charsets.UTF_8)
    }
}
