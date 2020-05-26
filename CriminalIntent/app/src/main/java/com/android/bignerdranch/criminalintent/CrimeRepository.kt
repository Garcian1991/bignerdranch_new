package com.android.bignerdranch.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.android.bignerdranch.criminalintent.database.CrimeDatabase
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {
    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao = database.crimeDao()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    companion object {
        private var INSTANSE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANSE == null) {
                INSTANSE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANSE ?: throw IllegalStateException(
                "CrimeRepository must be initialized"
            )
        }
    }
}