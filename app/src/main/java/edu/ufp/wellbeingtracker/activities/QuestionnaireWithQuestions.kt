package edu.ufp.wellbeingtracker.activities

import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.Questionnaire

data class QuestionnaireWithQuestions(
    val questionnaire: Questionnaire,
    val questions: List<QuestionQuestionnaire>
)
