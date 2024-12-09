package edu.ufp.wellbeingtracker.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ufp.wellbeingtracker.R
import edu.ufp.wellbeingtracker.database.AppDatabase
import edu.ufp.wellbeingtracker.database.MainViewModel
import edu.ufp.wellbeingtracker.database.MainViewModelFactory
import edu.ufp.wellbeingtracker.database.WellBeingApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionnairesAdapter
    private lateinit var mainViewModel: MainViewModel
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userId = intent.getIntExtra("userId", 0)

        recyclerView = findViewById(R.id.recyclerViewQuestions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //fetch data from database

        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory((applicationContext as WellBeingApp).appRepository)
        )[MainViewModel::class.java]

        mainViewModel.questionnaireWithQuestions.observe(this, Observer {
            questionnaireWithQuestions ->
            if(questionnaireWithQuestions != null) {
                Log.d("HomeActivity", "questionnaireWithQuestions = $questionnaireWithQuestions")
                //set up adapter
                adapter = QuestionnairesAdapter(questionnaireWithQuestions)
                recyclerView.adapter = adapter
            }
        })
        Log.d("HomeActivity 2", "questionnaireWithQuestions 2 = $mainViewModel.questionnaireWithQuestions")

        mainViewModel.fetchQuestionnaires()

    }
}

