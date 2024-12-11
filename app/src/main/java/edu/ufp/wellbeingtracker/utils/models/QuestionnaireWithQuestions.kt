package edu.ufp.wellbeingtracker.utils.models

import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.Questionnaire

data class QuestionnaireWithQuestions(
    val questionnaire: Questionnaire,
    val questions: List<QuestionQuestionnaire>
)
