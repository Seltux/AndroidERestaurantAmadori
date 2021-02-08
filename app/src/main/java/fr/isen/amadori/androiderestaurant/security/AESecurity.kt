package fr.isen.amadori.androiderestaurant.security

import android.os.Build
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.Key
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

const val ALGORTIHM_TYPE = "PBKDF2WithHmacSHA1"
const val CPR_TRANSFORMATION = "AES/CBC/PKCS7Padding"//API 23+ //https://miro.medium.com/max/2068/1*MNcknQeCrJMhTWx9JlpnKg.png
const val ENCRYPT_PASSWORD  = "azerty1234567890"

@RequiresApi(Build.VERSION_CODES.M)
fun encrypt(data: ByteArray): HashMap<String, ByteArray> {

    val salt = ByteArray(256)
    SecureRandom().nextBytes(salt)

    val iv = ByteArray(16)
    SecureRandom().nextBytes(iv)

    val cipher = Cipher.getInstance(CPR_TRANSFORMATION)
    cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(salt), IvParameterSpec(iv))

    return hashMapOf(Pair(EncryptConstants.SALT_VALUE,salt),Pair(EncryptConstants.IV_VALUE,iv),Pair(
        EncryptConstants.ENC_VALUE,cipher.doFinal(data)))

}

@RequiresApi(Build.VERSION_CODES.M)
fun decrypt(map: HashMap<String, ByteArray>): String {

    val salt = map[EncryptConstants.SALT_VALUE]
    val iv = map[EncryptConstants.IV_VALUE]
    val encrypted = map[EncryptConstants.ENC_VALUE]

    val cipher = Cipher.getInstance(CPR_TRANSFORMATION)
    cipher.init(Cipher.DECRYPT_MODE, getSecretKey(salt!!), IvParameterSpec(iv))

    return cipher.doFinal(encrypted).fromBytetoString()
}


@RequiresApi(Build.VERSION_CODES.M)
private fun getSecretKey(salt : ByteArray) : Key {
    val pbKeySpec = PBEKeySpec(ENCRYPT_PASSWORD.toCharArray(), salt, 1324, 256)
    val keyBytes = SecretKeyFactory.getInstance(ALGORTIHM_TYPE).generateSecret(pbKeySpec).encoded
    return SecretKeySpec(keyBytes, KeyProperties.KEY_ALGORITHM_AES)
}