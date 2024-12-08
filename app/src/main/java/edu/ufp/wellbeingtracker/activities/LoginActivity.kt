package edu.ufp.wellbeingtracker.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import edu.ufp.wellbeingtracker.R
import edu.ufp.wellbeingtracker.database.AppDatabase
import edu.ufp.wellbeingtracker.database.MainRepository
import edu.ufp.wellbeingtracker.database.MainViewModel
import edu.ufp.wellbeingtracker.database.MainViewModelFactory
import edu.ufp.wellbeingtracker.database.WellBeingApp

class LoginActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Apply edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory((this.applicationContext as WellBeingApp).appRepository)
        )[MainViewModel::class.java]


        // Set up UI elements
        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val errorMessageTextView = findViewById<TextView>(R.id.textViewErrorMessage)
        val signUpTextView = findViewById<TextView>(R.id.textViewSignUp)

        errorMessageTextView.visibility = View.INVISIBLE

        // Handle login button click
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isBlank() || password.isBlank()) {
                errorMessageTextView.text = getString(R.string.all_fields_are_required)
                errorMessageTextView.visibility = View.VISIBLE
                val toast = Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0) // Position it in the center and move it upwards
                toast.show()
            } else {
                mainViewModel.loginUser(username, password) { userId ->
                    if (userId > 0) {
                        val toast = Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0) // Position it in the center and move it upwards
                        toast.show()

                        //change page
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra("userId", userId)
                        startActivity(intent)
                        finish() // Close LoginActivity
                    } else {
                        errorMessageTextView.text = getString(R.string.invalid_credentials)
                        errorMessageTextView.visibility = View.VISIBLE
                        val toast = Toast.makeText(this, "Invalid username or password! Please check your credentials.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0) // Position it in the center and move it upwards
                        toast.show()
                    }
                }
            }
        }

        // Handle "Sign up" link click
        signUpTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
