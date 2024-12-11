package edu.ufp.wellbeingtracker.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import edu.ufp.wellbeingtracker.R
import edu.ufp.wellbeingtracker.database.MainViewModel
import edu.ufp.wellbeingtracker.database.MainViewModelFactory
import edu.ufp.wellbeingtracker.database.WellBeingApp
import edu.ufp.wellbeingtracker.utils.functions.showSnackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory((this.applicationContext as WellBeingApp).appRepository)
        )[MainViewModel::class.java]

        Log.d("LoginActivity", "mainViewModel initialized: $mainViewModel")

        //Set up UI elements
        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val confirmPasswordEdit = findViewById<EditText>(R.id.editTextConfirmPassword)
        val registerButton = findViewById<Button>(R.id.buttonRegister)
        val errorMessageTextView = findViewById<TextView>(R.id.textViewErrorMessage)
        val alreadyHaveAccountTextView = findViewById<TextView>(R.id.textViewAlreadyHaveAccount)

        errorMessageTextView.visibility = View.INVISIBLE

        //Handle register button click
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEdit.text.toString()

            //validate input
            if(username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                errorMessageTextView.visibility = View.VISIBLE
                errorMessageTextView.text = getString(R.string.all_fields_are_required)
                showSnackbar(findViewById(R.id.main), "All fields are required")
            }
            else if (password != confirmPassword) {
                errorMessageTextView.text= getString(R.string.passwords_do_not_match)
                errorMessageTextView.visibility = View.VISIBLE
                showSnackbar(findViewById(R.id.main), "Passwords do not match!")
            }
            else {
                // Register the user
                mainViewModel.registerUser(username, password) { isSuccessful ->
                    if(isSuccessful) {
                        showSnackbar(findViewById(R.id.main), "Registration successful!")

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()// close registerActivity
                    } else {
                        errorMessageTextView.text= getString(R.string.username_already_exists)
                        errorMessageTextView.visibility = View.VISIBLE
                        showSnackbar(findViewById(R.id.main), "Username already exists.")

                    }

                }
            }
        }
        alreadyHaveAccountTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}