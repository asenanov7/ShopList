package com.example.shoplist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false )
abstract class AppDatabase:RoomDatabase() {
    abstract fun shopListDao():ShopListDao

    companion object{
        private var INSTANCE:AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "db.name"

        fun getInstance(application:Application): AppDatabase {
            //Сюда могут зайти несколько потоков, все они при первой проверке увидят что БД==null и  пойдут дальше
            INSTANCE?.let {
                return it
            }
            //Создаем очередь и пускаем потоки сюда по очереди, первый заходит инициализирует БД, а остальные спотыкаются об проверку на нулл
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
                INSTANCE = db
                return db
            }
        }
    }
}