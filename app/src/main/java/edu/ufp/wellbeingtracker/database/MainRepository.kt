package edu.ufp.wellbeingtracker.database

import edu.ufp.wellbeingtracker.database.entities.AnswerQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.Questionnaire
import edu.ufp.wellbeingtracker.database.entities.TypeAnswer
import edu.ufp.wellbeingtracker.database.entities.User
import edu.ufp.wellbeingtracker.database.entities_dao.AnswerQuestionnaireDAO
import edu.ufp.wellbeingtracker.database.entities_dao.QuestionQuestionnaireDAO
import edu.ufp.wellbeingtracker.database.entities_dao.QuestionnaireDAO
import edu.ufp.wellbeingtracker.database.entities_dao.TypeAnswerDAO
import edu.ufp.wellbeingtracker.database.entities_dao.UserDAO

class MainRepository(
    private val userDao: UserDAO,
    private val questionnaireDao: QuestionnaireDAO,
    private val questionQuestionnaireDAO: QuestionQuestionnaireDAO,
    private val typeAnswerDAO: TypeAnswerDAO,
    private val answerQuestionnaireDAO: AnswerQuestionnaireDAO
) {
    suspend fun insertUser(user: User) = userDao.insertUser(user)

    suspend fun getUserByName(username: String) = userDao.getUserByName(username)

    suspend fun insertQuestionnaires(vararg questionnaires: Questionnaire) =
        questionnaireDao.insertQuestionnaires(*questionnaires)

    suspend fun insertQuestions(vararg questions: QuestionQuestionnaire) =
        questionQuestionnaireDAO.insertQuestions(*questions)

    suspend fun insertTypeAnswers(vararg typeAnswers: TypeAnswer) =
        typeAnswerDAO.insertTypeAnswers(*typeAnswers)

    suspend fun insertAnswer(answer: AnswerQuestionnaire) =
        answerQuestionnaireDAO.insertAnswer(answer)

    suspend fun getAllQuestionnaires() =
        questionnaireDao.getAllQuestionnaires()

    suspend fun getAllQuestionsForQuestionnaire(id: Int) =
        questionQuestionnaireDAO.getAllQuestionsForQuestionnaire(id)

    suspend fun countAllQuestionsForQuestionnaire(id: Int) =
        questionQuestionnaireDAO.countAllQuestionsForQuestionnaire(id)

    suspend fun getAllMeanings() =
        typeAnswerDAO.getAllMeanings()
}
