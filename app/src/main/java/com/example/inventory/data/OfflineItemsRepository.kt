package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

/** Kelas ini mengelola akses data untuk item dalam database menggunakan ItemDao.*/
class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {

    /** Mengambil semua item sebagai aliran data yang diperbarui secara real-time */
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    /** Mengambil item berdasarkan ID sebagai aliran data, mengembalikan null jika tidak ditemukan.*/
    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    /** Menyisipkan item baru ke dalam database secara asinkron.*/
    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    /** Menghapus item dari database secara asinkron.*/
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    /** Memperbarui item yang ada dalam database secara asinkron.*/
    override suspend fun updateItem(item: Item) = itemDao.update(item)
}