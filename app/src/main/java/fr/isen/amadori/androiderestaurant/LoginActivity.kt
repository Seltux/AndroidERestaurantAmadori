package fr.isen.amadori.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.google.gson.Gson
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.Length
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import fr.isen.amadori.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.amadori.androiderestaurant.model.UserJsonResult
import org.json.JSONObject

private lateinit var binding: ActivityLoginBinding

class LoginActivity : AppCompatActivity(), Validator.ValidationListener {

    protected var validator: Validator? = null

    @NotEmpty(message = "Please enter your email address.")
    @Email(message = "Your email doesn't respect our policy.")
    private lateinit var  inputEmail: EditText

    @NotEmpty(message = "Please enter your password.")
    @Password(message = "Your password doesn't respect our policy.")
    @Length(min = 12, message = "Your password must be at least 12 characters long.")
    private lateinit var inputPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validator = Validator(this)
        validator!!.setValidationListener(this)
        initCreate()
    }

    private fun initCreate(){
        inputEmail = binding.idEmailLogin
        inputPassword = binding.idPasswordLogin
        binding.idGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.idSubmitLogin.setOnClickListener {
            validator!!.validate()
            verifyLoginUser()
        }
    }

    override fun onValidationSucceeded() {
        Toast.makeText(this, "Formulaire correct, connecté", Toast.LENGTH_SHORT).show()
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

    private fun verifyLoginUser(){
        val url = "http://test.api.catering.bluecodegames.com/user/login"
        val jsonRequet = Volley.newRequestQueue(this)
        val postData = JSONObject()
        try{
            postData.put("id_shop", "1")
            postData.put("email", binding.idEmailLogin.text)
            postData.put("password", binding.idPasswordLogin.text)
        }catch (e: Exception){
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,url, postData, { response ->
            val gson = Gson().fromJson(response.toString(), UserJsonResult::class.java)
            this.getSharedPreferences(BaseActivity.PREF_SHARED, MODE_PRIVATE)?.edit()
                ?.putInt(SignUpActivity.ID_USER, gson.data.id)
                ?.apply()
            Toast.makeText(
                this,
                "Connexion réussis, bienvenue" + gson.data.firstname + gson.data.lastname + ".",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, FinalOrderActivity::class.java)
            startActivity(intent)
        },
            {
                Log.e("error login form", it.toString())
            })
        jsonRequet.add(jsonObjectRequest)
    }
}