package edu.ufp.wellbeingtracker.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import edu.ufp.wellbeingtracker.R
import androidx.recyclerview.widget.RecyclerView
import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire

class QuestionsAdapter(private val questions: List<QuestionQuestionnaire>) :
RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>(){

    class QuestionsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val questionText: TextView = view.findViewById(R.id.textViewQuestion)
        val agreeIcn: ImageView = view.findViewById(R.id.iconAgree)
        val disagreeIcn: ImageView = view.findViewById(R.id.iconDisagree)
        val neutralIcn: ImageView = view.findViewById(R.id.iconNeutral)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsAdapter.QuestionsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionsAdapter.QuestionsViewHolder, position: Int) {

       val question = questions[position]

        holder.questionText.text = question.title

        // Set up click listeners for answer icons
        holder.disagreeIcn.setOnClickListener {
            // Handle disagree
        }
        holder.neutralIcn.setOnClickListener {
            // Handle neutral
        }
        holder.agreeIcn.setOnClickListener {
            // Handle agree
        }
    }


    override fun getItemCount(): Int  = questions.size //+1 for the header
}