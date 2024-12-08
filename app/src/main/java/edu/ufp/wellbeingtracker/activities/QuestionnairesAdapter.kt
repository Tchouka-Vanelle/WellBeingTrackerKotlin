package edu.ufp.wellbeingtracker.activities

import edu.ufp.wellbeingtracker.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.Questionnaire


class QuestionnairesAdapter(
    private val data: List<QuestionnaireWithQuestions>
) : RecyclerView.Adapter<QuestionnairesAdapter.QuestionnaireViewHolder>() {

    class QuestionnaireViewHolder(view: View): RecyclerView.ViewHolder(view){
        val questionnaireTitle: TextView = view.findViewById(R.id.textViewQuestionnaireTitle)
        val questionsRecyclerView: RecyclerView = view.findViewById(R.id.recyclerViewQuestions)

        init {
            questionsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionnaireViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_questionnaire_header, parent, false)
        return QuestionnaireViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionnaireViewHolder, position: Int) {
        val questionnaireWithQuestions = data[position]

        holder.questionnaireTitle.text = questionnaireWithQuestions.questionnaire.title

        val questionsAdapter = QuestionsAdapter(questionnaireWithQuestions.questions)
        holder.questionsRecyclerView.adapter = questionsAdapter
    }

    override fun getItemCount(): Int = data.size
}
