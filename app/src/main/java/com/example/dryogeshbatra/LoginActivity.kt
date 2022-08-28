package com.example.dryogeshbatra

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import com.example.dryogeshbatra.commonViewModel.BookViewModel
import com.example.dryogeshbatra.utils.Constants
import com.example.dryogeshbatra.models.UserData.User
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    val viewModel: BookViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Click event assigned to Forgot Password text.
        tv_forgot_password.setOnClickListener(this)
        // Click event assigned to Login button.
        btn_login.setOnClickListener(this)
        // Click event assigned to Register text.
        tv_register.setOnClickListener(this)




        if (viewModel.checkIfTheUserIsLoggedIn(this) != "" && viewModel.checkIfTheUserIsLoggedIn(this) != null) {
            alreadyLoggedInUserIntent()
            Log.i("loggedInFired", "true")
        }
       // Log.i("mytagggg", viewModel.checkIfTheUserIsLoggedIn(this))
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_forgot_password -> {

                    //  Launch the forgot password screen when the user clicks on the forgot password text.
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {
                    logInRegisteredUser()
                }

                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Get the text from editText and trim the space
            val email = et_email.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    hideProgressDialog()
                    if (task.isSuccessful) {
                        viewModel.getUserDetails(this@LoginActivity)
                    } else {
                        // Hide the progress dialog
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }


    }

    fun userLoggedInSuccess(user: User) {

        // Hide the progress dialog.
        val sharedPref = getSharedPreferences(Constants.LOGGED_USER_DETAILS, MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val userDetails = gson.toJson(user)
        editor.putString(Constants.LOGGED_STRING_KEY, userDetails)
        // Don't forget to commit your edits!!!
        // Don't forget to commit your edits!!!
        editor.commit()
        hideProgressDialog()

        if (user.profileCompleted == 0) {
            // If the user profile is incomplete then launch the UserProfileActivity.
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
           // intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)

        } else {
            // Redirect the user to Dashboard Screen after log in.
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
        }
        finish()
    }

    fun alreadyLoggedInUserIntent(){
        val intent = Intent(this, DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

   /* fun userLoggedInSuccess(user: User) {

        // Hide the progress dialog.
        hideProgressDialog()

        *//*if (user.profileCompleted == 0) {
            // If the user profile is incomplete then launch the UserProfileActivity.
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else {  }*//*
        // Redirect the user to Dashboard Screen after log in.
        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
        finish()
    }*/



    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }


}