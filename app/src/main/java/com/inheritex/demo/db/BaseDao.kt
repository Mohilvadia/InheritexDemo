package com.inheritex.demo.db

import androidx.room.*

/// [BaseDao] This interface is used for base
@Dao
interface BaseDao<in T> {
    //insert single entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T): Long

    //insert list entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: List<T>): List<Long>

    //delete single entity
    @Delete
    fun delete(type: T)

    //update single entity
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(type: T): Int
}