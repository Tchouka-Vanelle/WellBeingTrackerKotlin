package edu.ufp.wellbeingtracker.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ufp.wellbeingtracker.R
import edu.ufp.wellbeingtracker.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HomeActivity  : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionnairesAdapter
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
        val database = AppDatabase.getInstance(this)
        val questionnaireWithQuestions = runBlocking {
            withContext(Dispatchers.IO) {

                val questionnaires = database.questionnaireDAO().getAllQuestionnaires()
                val questionnairesWithQuestions = mutableListOf<QuestionnaireWithQuestions>()

                for (questionnaire in questionnaires) {
                    val questions = database.questionQuestionnaireDAO().getAllQuestionsForQuestionnaire(questionnaire.id)
                    questionnairesWithQuestions.add(QuestionnaireWithQuestions(questionnaire, questions))
                }
                questionnairesWithQuestions
            }
        }

        //set up adapter
        adapter = QuestionnairesAdapter(questionnaireWithQuestions)
        recyclerView.adapter = adapter



    }
}

