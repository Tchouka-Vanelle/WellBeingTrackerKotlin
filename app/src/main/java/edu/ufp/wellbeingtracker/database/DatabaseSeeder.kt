package edu.ufp.wellbeingtracker.database

import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.Questionnaire
import edu.ufp.wellbeingtracker.database.entities.TypeAnswer
import edu.ufp.wellbeingtracker.database.entities_dao.QuestionQuestionnaireDAO
import edu.ufp.wellbeingtracker.database.entities_dao.QuestionnaireDAO
import edu.ufp.wellbeingtracker.database.entities_dao.TypeAnswerDAO
import kotlinx.coroutines.runBlocking

object DatabaseSeeder {

    fun preFillDatabase(
        questionnaireDao: QuestionnaireDAO,
        questionQuestionnaireDAO: QuestionQuestionnaireDAO,
        typeAnswerDAO: TypeAnswerDAO
    ) {
        try {
            runBlocking {
                questionnaireDao.insertQuestionnaires(
                    Questionnaire(1, "SLEEP", true, false),
                    Questionnaire(2, "WELLBEING1", true, false),
                    Questionnaire(3, "WELLBEING2", true, true)
                )

                questionQuestionnaireDAO.insertQuestions(
                    QuestionQuestionnaire(1, 1, "I slept very well and feel that my sleep was totally restorative."),
                    QuestionQuestionnaire(2, 1, "I feel totally rested after this night's sleep."),
                    QuestionQuestionnaire(3, 2, "I related easily to the people around me."),
                    QuestionQuestionnaire(4, 2, "I was able to face difficult situations in a positive way."),
                    QuestionQuestionnaire(5, 2, "I felt that others liked me and appreciated me."),
                    QuestionQuestionnaire(6, 2, "I felt satisfied with what I was able to achieve, I felt proud of myself."),
                    QuestionQuestionnaire(7, 2, "My life was well balanced between my family, personal and academic activities."),
                    QuestionQuestionnaire(8, 3, "I felt emotionally balanced."),
                    QuestionQuestionnaire(9, 3, "I felt good, at peace with myself"),
                    QuestionQuestionnaire(10, 3, "I felt confident.")
                )

                typeAnswerDAO.insertTypeAnswers(
                    TypeAnswer(1, 1, "DISAGREE"),
                    TypeAnswer(2, 2, "EITHER DISAGREE NOR AGREE"),
                    TypeAnswer(3, 3, "AGREE")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
