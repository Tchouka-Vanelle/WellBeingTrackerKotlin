package edu.ufp.wellbeingtracker.utils.functions

interface OnUserResponseListener {
    fun onUserResponse(questionnaireId: Int, questionId: Int, answerId: Int)
    fun onUserSaveQuestionnaire(questionnaireId: Int)
}