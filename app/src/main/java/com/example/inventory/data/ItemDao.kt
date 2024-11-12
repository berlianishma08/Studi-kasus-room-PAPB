package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/** @Dao: Menandakan bahwa antarmuka ini adalah DAO yang berisi metode untuk berinteraksi dengan database.*/
@Dao
interface ItemDao {

    /** @Insert: Anotasi yang menunjukkan bahwa metode ini akan digunakan untuk menyisipkan data baru ke dalam tabel.
    *          Metode ini akan menerima objek dari entitas (InventoryItem) dan menambahkannya ke database */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    /** @Update: Anotasi yang menunjukkan bahwa metode ini akan digunakan untuk memperbarui data yang sudah ada dalam tabel.
    *          Metode ini juga menerima objek dari entitas yang telah dimodifikasi */
    @Update
    suspend fun update(item: Item)

    /** @Delete: Anotasi yang menunjukkan bahwa metode ini akan digunakan untuk menghapus data dari tabel.
              Metode ini menerima objek dari entitas yang ingin dihapus.*/
    @Delete
    suspend fun delete(item: Item)

    /** @Query: Anotasi yang digunakan untuk mendefinisikan kueri SQL secara langsung.
    *         Dalam metode ini, Anda dapat menuliskan kueri SQL untuk mengambil data dari tabel.
    *         Misalnya, getItemById akan mengambil item berdasarkan id yang diberikan.*/
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}