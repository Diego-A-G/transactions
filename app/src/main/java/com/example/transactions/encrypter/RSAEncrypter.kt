package com.example.transactions.encrypter

import android.content.Context
import android.util.Base64
import android.util.Log
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RSAEncrypter {
    private lateinit var publicKey: PublicKey

    private fun loadKey(context: Context) {
        try {
            val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")

            fun readPublicKey(fileName: String): String {
                return context.assets.open(fileName).bufferedReader().use { it.readText() }
            }

            val key = readPublicKey("publicKey")
            val publicKeyBytes = Base64.decode(key, Base64.DEFAULT)
            val spec = X509EncodedKeySpec(publicKeyBytes)
            publicKey = keyFactory.generatePublic(spec)
            Log.d("RSAEncrypter", publicKey.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun encrypt(text: String): ByteArray? {
        var cipherText: ByteArray? = null
        try {
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            cipherText = cipher.doFinal(text.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cipherText
    }

    fun getEncryptedString(c: Context, s: String): String? {
        return try {
            loadKey(c)
            val encryptionResult = encrypt(s)
            val base64Data = Base64.encode(encryptionResult, 0)
            String(base64Data, Charset.forName("UTF-8")).replace("\n", "")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}