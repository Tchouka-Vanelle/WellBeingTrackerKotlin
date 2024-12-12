package edu.ufp.wellbeingtracker.pages

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
import edu.ufp.wellbeingtracker.controllers.QuestionnairesAdapter
import edu.ufp.wellbeingtracker.database.MainViewModel
import edu.ufp.wellbeingtracker.database.MainViewModelFactory
import edu.ufp.wellbeingtracker.database.WellBeingApp
import edu.ufp.wellbeingtracker.utils.functions.OnUserResponseListener

class HomeActivity : AppCompatActivity(), OnUserResponseListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionnairesAdapter
    private lateinit var mainViewModel: MainViewModel
    private var userId: Int = 0
    private lateinit var meanings: List<String>
    private val responses = mutableMapOf<Int, MutableMap<Int, Int>>()

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

        mainViewModel.loadMeanings { meaningsList ->
            meanings = meaningsList
        }


        mainViewModel.questionnaireWithQuestions.observe(this, Observer {
            questionnaireWithQuestions ->
            if(questionnaireWithQuestions != null && questionnaireWithQuestions.isNotEmpty()) {
                Log.d("HomeActivity", "questionnaireWithQuestions = $questionnaireWithQuestions")
                //set up adapter
                adapter = QuestionnairesAdapter(questionnaireWithQuestions, this, meanings)
                recyclerView.adapter = adapter
            }else {
                Log.d("HomeActivity", "No questionnaires found.")
            }
        })

        mainViewModel.fetchQuestionnaires()

    }

    override fun onUserResponse(questionnaireId: Int, questionId: Int, answerId: Int) {

        responses.getOrPut(questionnaireId) {
            mutableMapOf()
        }[questionId] = answerId

    }

    override fun onUserSaveQuestionnaire(questionnaireId: Int) {

        mainViewModel.countAllQuestionsForQuestionnaire(questionnaireId) {

                totalQuestions ->
            val allQuestionsAnswered = responses[questionnaireId]?.size == totalQuestions

            if(allQuestionsAnswered) {
                responses[questionnaireId]?.forEach{ (qId, aId) ->
                    mainViewModel.saveAnswer(userId, qId, aId)
                }
            }
        }

    }
}

