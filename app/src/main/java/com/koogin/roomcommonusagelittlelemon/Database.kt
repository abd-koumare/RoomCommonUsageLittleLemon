package com.koogin.roomcommonusagelittlelemon

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var age: Int
)

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user_table WHERE name = :userName")
    fun getUserByName(userName: String): List<User>

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

}

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "room-common-usage-db"
                    )
                        .allowMainThreadQueries()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}