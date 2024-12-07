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


        //Initialize ViewModel with a Factory
        val database = AppDatabase.getInstance(this)
        val repository = MainRepository(
            database.userDAO(),
            database.questionnaireDAO(),
            database.questionQuestionnaireDAO(),
            database.typeAnswerDAO(),
            database.answerQuestionnaireDAO()
        )
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository)
        )[MainViewModel::class.java]

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
                val toast = Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, -1200) // Position it in the center and move it upwards
                toast.show()
            }
            else if (password != confirmPassword) {
                errorMessageTextView.text= getString(R.string.passwords_do_not_match)
                errorMessageTextView.visibility = View.VISIBLE
                val toast = Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, -1200) // Position it in the center and move it upwards
                toast.show()
            }
            else {
                // Register the user
                mainViewModel.registerUser(username, password) { isSuccessful ->
                    if(isSuccessful) {
                        val toast = Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, -1200) // Position it in the center and move it upwards
                        toast.show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        //finish()// close registerActivity
                    } else {
                        errorMessageTextView.text= getString(R.string.username_already_exists)
                        errorMessageTextView.visibility = View.VISIBLE
                        val toast = Toast.makeText(this, "Username already exists.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, -1200) // Position it in the center and move it upwards
                        toast.show()
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