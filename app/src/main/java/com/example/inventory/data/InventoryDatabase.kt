package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/** Kelas ini mendefinisikan database Room untuk item dengan pola singleton.*/

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    /** Mengembalikan instance ItemDao untuk akses data item.*/
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        /** Mendapatkan instance singleton dari InventoryDatabase.
         * Jika instance sudah ada, kembalikan yang ada; jika tidak, buat yang baru.
         *
         * @param context: Konteks aplikasi untuk membangun database.
         * @return InventoryDatabase: Instance dari database.*/

        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}