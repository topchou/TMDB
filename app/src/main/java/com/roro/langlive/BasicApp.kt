package com.roro.langlive

import android.app.Application
import com.roro.langlive.db.AppDatabase
import com.roro.langlive.db.DataRepository

/**
 * Android Application class. Used for accessing singletons.
 */
class BasicApp : Application() {

    val database: AppDatabase
        get() = AppDatabase.getInstance(this)
    val repository: DataRepository?
        get() = DataRepository.getInstance(database)

}