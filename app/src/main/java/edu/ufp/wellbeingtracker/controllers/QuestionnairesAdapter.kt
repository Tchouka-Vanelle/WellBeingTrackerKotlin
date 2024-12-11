package edu.ufp.wellbeingtracker.controllers

import edu.ufp.wellbeingtracker.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ufp.wellbeingtracker.utils.functions.OnUserResponseListener
import edu.ufp.wellbeingtracker.utils.models.QuestionnaireWithQuestions


class QuestionnairesAdapter(
    private val data: List<QuestionnaireWithQuestions>,
    private val responseListener: OnUserResponseListener,
    private val meanings: List<String>
) : RecyclerView.Adapter<QuestionnairesAdapter.QuestionnaireViewHolder>(){

    class QuestionnaireViewHolder(view: View): RecyclerView.ViewHolder(view){
        val questionnaireTitle: TextView = view.findViewById(R.id.textViewQuestionnaireTitle)
        val questionsRecyclerView: RecyclerView = view.findViewById(R.id.recyclerViewQuestions)
        val saveButton: Button = view.findViewById(R.id.saveButton)

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

        val questionsAdapter = QuestionsAdapter(
            questionnaireWithQuestions.questions, meanings) {
            questionId: Int, answerId: Int ->
            responseListener.onUserResponse(questionnaireWithQuestions.questionnaire.id,
                questionId, answerId)
        }

        holder.questionsRecyclerView.adapter = questionsAdapter

        holder.saveButton.setOnClickListener {
            responseListener.onUserSaveQuestionnaire(questionnaireWithQuestions.questionnaire.id)
        }
    }


    override fun getItemCount(): Int = data.size
}
