package edu.ufp.wellbeingtracker.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import edu.ufp.wellbeingtracker.R
import androidx.recyclerview.widget.RecyclerView
import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire

class QuestionsAdapter(private val questions: List<QuestionQuestionnaire>,
                       private val meanings: List<String>,
                       private val responseCallback: (questionId: Int, answerId: Int) -> Unit,
) :
RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>(){

    class QuestionsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val questionText: TextView = view.findViewById(R.id.textViewQuestion)
        val agreeIcn: ImageView = view.findViewById(R.id.iconAgree)
        val disagreeIcn: ImageView = view.findViewById(R.id.iconDisagree)
        val neutralIcn: ImageView = view.findViewById(R.id.iconNeutral)
        val agreeDescription: TextView = view.findViewById(R.id.descriptionAgree)
        val disagreeDescription: TextView = view.findViewById(R.id.descriptionDisagree)
        val neutralDescription: TextView = view.findViewById(R.id.descriptionNeutral)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {

        val question = questions[position]

        holder.questionText.text = question.title

        // Set up click listeners for answer icons
        holder.disagreeIcn.setOnClickListener {
            resetIcons(holder)
            holder.disagreeIcn.setImageResource(R.drawable.ic_disagree_filled)

            responseCallback(question.id, 1)
        }
        holder.neutralIcn.setOnClickListener {
            resetIcons(holder)
            holder.neutralIcn.setImageResource(R.drawable.ic_neutral_filled)

            responseCallback(question.id, 2)
        }
        holder.agreeIcn.setOnClickListener {
            resetIcons(holder)
            holder.agreeIcn.setImageResource(R.drawable.ic_agree_filled)

            responseCallback(question.id, 3)
        }

        holder.disagreeDescription.text = meanings.getOrNull(0) ?: ""
        holder.neutralDescription.text = meanings.getOrNull(1) ?: ""
        holder.agreeDescription.text = meanings.getOrNull(2) ?: ""

    }

    private fun resetIcons(holder: QuestionsViewHolder) {

        holder.agreeIcn.setImageResource(R.drawable.ic_agree)
        holder.neutralIcn.setImageResource(R.drawable.ic_neutral)
        holder.disagreeIcn.setImageResource(R.drawable.ic_disagree)
    }



    override fun getItemCount(): Int  = questions.size
}