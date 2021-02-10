package fr.isen.amadori.androiderestaurant.security

import android.content.Context
import android.preference.PreferenceManager
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


private const val pswdIterations = 10
private const val keySize = 256
private const val cypherInstance = "AES/CBC/PKCS5Padding"
private const val secretKeyInstance = "PBKDF2WithHmacSHA1"
private const val plainText = "3ABC670967E99C7E"
private const val AESSalt = "E1F53135E559C253"

fun encrypt(textToEncrypt: String, context: Context): String {
    val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
    val cipher = Cipher.getInstance(cypherInstance)
    cipher.init(
        Cipher.ENCRYPT_MODE, skeySpec)
    val encrypted = cipher.doFinal(textToEncrypt.toByteArray())
    saveInitializationVector(context,cipher.iv)
    return Base64.encodeToString(encrypted, Base64.DEFAULT)
}

fun decrypt(textToDecrypt: String?, context: Context): String {
    val encryted_bytes = Base64.decode(textToDecrypt, Base64.DEFAULT)
    val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
    val cipher = Cipher.getInstance(cypherInstance)
    cipher.init(
        Cipher.DECRYPT_MODE, skeySpec, IvParameterSpec(
            getSavedInitializationVector(context)
        )
    )
    val decrypted = cipher.doFinal(encryted_bytes)
    return String(decrypted, Charsets.UTF_8)
}

private fun getRaw(plainText: String, salt: String): ByteArray {
    try {
        val factory = SecretKeyFactory.getInstance(secretKeyInstance)
        val spec: KeySpec =
            PBEKeySpec(plainText.toCharArray(), salt.toByteArray(), pswdIterations, keySize)
        return factory.generateSecret(spec).encoded
    } catch (e: InvalidKeySpecException) {
        e.printStackTrace()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ByteArray(0)
}



fun saveInitializationVector(context: Context, initializationVector: ByteArray) {
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(initializationVector)
    val strToSave = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPref.edit()
    editor.putString("initialization_vector", strToSave)
    editor.apply()
}

fun getSavedInitializationVector(context: Context) : ByteArray {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val strInitializationVector = sharedPref.getString("initialization_vector", "")
    val bytes = Base64.decode(strInitializationVector, Base64.DEFAULT)
    val ois = ObjectInputStream(ByteArrayInputStream(bytes))
    val initializationVector = ois.readObject() as ByteArray
    return initializationVector
}
