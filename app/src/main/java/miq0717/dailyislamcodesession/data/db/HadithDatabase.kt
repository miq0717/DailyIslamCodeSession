package miq0717.dailyislamcodesession.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

//@Database(entities = [], version = 1, exportSchema = false)
abstract class HadithDatabase : RoomDatabase() {

    abstract fun hadithBooksDao(): HadithBooksDao

    private class HadithDatabaseCallback(
        private val scope: CoroutineScope,
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HadithDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): HadithDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HadithDatabase::class.java,
                    "hadith_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(HadithDatabaseCallback(scope, context.applicationContext))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}