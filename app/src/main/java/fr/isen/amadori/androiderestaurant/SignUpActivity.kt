package fr.isen.amadori.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.*
import fr.isen.amadori.androiderestaurant.databinding.ActivitySignUpBinding
import fr.isen.amadori.androiderestaurant.model.UserJsonResult
import org.json.JSONException
import org.json.JSONObject


private lateinit var binding: ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), Validator.ValidationListener {

    protected var validator: Validator? = null


    @NotEmpty(message = "Please enter a Firstname.")
    @Length(min = 3, max = 15, message = "Your firstname must be between 3 and 15 characters.")
    private lateinit var inputFirstname: EditText

    @NotEmpty(message = "Please enter a Lastname.")
    @Length(min = 3, max = 15, message = "Your firstname must be between 3 and 15 characters.")
    private lateinit var inputLastname: EditText

    @NotEmpty(message = "Please enter your Address.")
    private lateinit var inputAddress: EditText

    @NotEmpty(message = "Please enter your email address.")
    @Email(message = "Your email doesn't respect our policy.")
    private lateinit var inputEmail: EditText

    @NotEmpty(message = "Please enter your password.")
    @Password(message = "Your password doesn't respect our policy.")
    @Length(min = 12, message = "Your password must be at least 12 characters long.")
    private lateinit var inputPassword: EditText

    @NotEmpty(message = "Please confirm your password.")
    @ConfirmPassword(message = "This password doesn't match with the original one :(.")
    @Length(
        min = 12,
        message = "Your confirm password must be at least 12 characters long like the first one."
    )
    private lateinit var inputVerificationPassword: EditText

    @Checked(message = "Plese check this box, you have to agree to continue.")
    private lateinit var termsAndCondition: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validator = Validator(this)
        validator!!.setValidationListener(this)
        initCreate()

    }

    private fun initCreate() {
        inputFirstname = binding.idFirstNameSignUp
        inputLastname = binding.idLastnameSignUp
        inputAddress = binding.idAddressSignUp
        inputEmail = binding.idEmailSignUp
        inputPassword = binding.idPasswordSignUp
        inputVerificationPassword = binding.idVerificationPassword
        termsAndCondition = binding.idAgreeTerms
        binding.idSubmitSignUp.setOnClickListener {
            validator!!.validate()
            sendForm()
        }
        binding.idGoToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onValidationSucceeded() {
        Toast.makeText(this, "Formulaire correct, il a été soumis", Toast.LENGTH_SHORT).show();
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error in errors!!) {
            val view: View = error.view
            val message = error.getCollatedErrorMessage(this)
            if (view is EditText) {
                (view).error = message
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendForm() {
        val postApiURL = "http://test.api.catering.bluecodegames.com/user/register"
        val request = Volley.newRequestQueue(this)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("firstname", binding.idFirstNameSignUp.text)
            postData.put("lastname", binding.idLastnameSignUp.text)
            postData.put("address", binding.idAddressSignUp.text)
            postData.put("email", binding.idEmailSignUp.text)
            postData.put("password", binding.idPasswordSignUp.text)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, postApiURL, postData, {
                val gson = Gson().fromJson(it.toString(), UserJsonResult::class.java)
                Toast.makeText(this, "Votre compte a bien été créé :)", Toast.LENGTH_SHORT)
                    .show()
                val sharedPref = this.getSharedPreferences(
                    BaseActivity.PREF_SHARED,
                    MODE_PRIVATE
                )
                sharedPref.edit().putInt(ID_USER,gson.data.id).apply()
            },
            { Log.e("error signu form", it.toString()) }
        )
        request.add(jsonRequest)
    }
    companion object{
        const val ID_USER = "id_user"
    }
}

