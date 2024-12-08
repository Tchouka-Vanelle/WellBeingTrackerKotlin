package edu.ufp.wellbeingtracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        User::class,
        Questionnaire::class,
        QuestionQuestionnaire::class,
        TypeAnswer::class,
        AnswerQuestionnaire::class,
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun questionnaireDAO(): QuestionnaireDAO
    abstract fun questionQuestionnaireDAO(): QuestionQuestionnaireDAO
    abstract fun typeAnswerDAO(): TypeAnswerDAO
    abstract fun answerQuestionnaireDAO(): AnswerQuestionnaireDAO

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            //Singleton impl: if instance not null then return it, else create new instance
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wellbeing.db"
                )
                    /*.addCallback(object: RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            scope.launch {
                                val instance = getInstance(context, scope)
                                instance.pre
                            }
                        }
                    })*/
                    .build().also { INSTANCE = it }

            }

        }
    }
}