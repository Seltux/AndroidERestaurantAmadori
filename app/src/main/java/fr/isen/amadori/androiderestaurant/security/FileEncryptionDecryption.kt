package fr.isen.amadori.androiderestaurant.security

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Base64.*
import android.util.Log
import java.io.*
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

const val TAG = "Encryption"

/*fun jsonOrderFile(dishInfo: Dish, quantity: Int, context: Context) {
    val file_name = File(context.cacheDir.absolutePath + "Basket.json")
    val gson = GsonBuilder().setPrettyPrinting().create()
    val orderInfo = OrderInfo(dishInfo, quantity)
    if (file_name.exists()) {
        val contentFile = decryptContentFile(context)
        val json = gson.fromJson(contentFile, Order::class.java)
        json.orders.firstOrNull{ it.dish == dishInfo}?.let {
            it.quantity.apply { it.quantity += quantity }
        }?: run {
            json.orders.add(orderInfo)
        }
        file_name.writeText(gson.toJson(json))
        encryptContentFile(context)

    }else{
        val jsonObject =  gson.toJson(Order(mutableListOf(orderInfo)))
        file_name.writeText(jsonObject)
        encryptContentFile(context)
    }
}*/


fun readFile(filePath: String): ByteArray{
    val file = File(filePath)
    val fileContents = file.readBytes()
    val inputBuffer = BufferedInputStream(FileInputStream(file))

    inputBuffer.read(fileContents)
    inputBuffer.close()

    return fileContents
}

fun saveFile(fileData: ByteArray, path: String){
    val file = File(path)
    val bos = BufferedOutputStream(FileOutputStream(file,false))
    bos.write(fileData)
    bos.flush()
    bos.close()
}

fun encryptContentFile(context: Context){
    try {
        val filePath = context.cacheDir.absolutePath + "Basket.json"
        val fileData = readFile(filePath).toString()


        val sK = getSecretKey(PreferenceManager.getDefaultSharedPreferences(context))
        val encodeData = encrypt(sK,fileData)

        saveFile(encodeData,filePath)

    }catch (e: Exception){
        Log.e(TAG, "Encryption Failed" + e.toString())
    }
}

fun decryptContentFile(context: Context): String {
    val filePath = context.cacheDir.absolutePath + "Basket.json"
    val fileData = readFile(filePath)
    val sK = getSecretKey(PreferenceManager.getDefaultSharedPreferences(context))
    return decrypt(sK,fileData)
}

/**
 * Function to encrypt data in a file
 */
@SuppressLint("GetInstance")
fun encrypt(myKey: SecretKey, file: String): ByteArray{
    val byteArray = file.toByteArray(Charsets.UTF_8)
    val data = myKey.encoded
    val sKInfo = SecretKeySpec(data,0,data.size,"AES")
    val cipher = Cipher.getInstance("AES","BC")
    cipher.init(Cipher.ENCRYPT_MODE, sKInfo, IvParameterSpec(ByteArray(cipher.blockSize)))
    return cipher.doFinal(byteArray)
}

/**
 * Function to decrypt data in a file
 */
fun decrypt(myKey: SecretKey, file: ByteArray): String{
    val decrypted: ByteArray
    val cipher = Cipher.getInstance("AES", "BC")
    cipher.init(Cipher.DECRYPT_MODE, myKey, IvParameterSpec(ByteArray(cipher.blockSize)))
    decrypted = cipher.doFinal(file)
    return decrypted.toString()
}

/**
 * Function to generate a secret Key, used to encrypt the file
 */
fun generateSecretKey():SecretKey?{
    val secureRandom = SecureRandom()
    val keyGenerator = KeyGenerator.getInstance("AES")

    //Generation of the key randomly
    keyGenerator?.init(256,secureRandom)
    return keyGenerator?.generateKey()
}

/**
 * Function to save your secret Key
 */
fun saveSecretKey(sharedPreference: SharedPreferences, sK: SecretKey): String{
    val eK = String(encode(sK.encoded, NO_WRAP))
    sharedPreference.edit().putString("secretKey",eK).apply()
    return eK
}

/**
 * Function to get the secret Key from the shared Preferences
 */
fun getSecretKey(sharedPreference: SharedPreferences): SecretKey{
    val key = sharedPreference.getString("secretKey",null)

    if(key == null){
        val secretKey =  generateSecretKey()
        saveSecretKey(sharedPreference,secretKey!!)
        return secretKey
    }

    val decodedKey = decode(key, NO_WRAP)
    val originalKey = SecretKeySpec(decodedKey,0, decodedKey.size, "AES")

    return originalKey
}