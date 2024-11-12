package com.example.inventory.data

import android.content.Context

/** Antarmuka ini mendefinisikan kontainer aplikasi untuk injeksi ketergantungan. */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/** Implementasi [AppContainer] yang menyediakan instance dari [OfflineItemsRepository].*/
class AppDataContainer(private val context: Context) : AppContainer {
    /** Menginisialisasi [ItemsRepository] menggunakan lazy loading.
     * Instance [OfflineItemsRepository] dibuat dengan mengakses DAO dari database.*/
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}