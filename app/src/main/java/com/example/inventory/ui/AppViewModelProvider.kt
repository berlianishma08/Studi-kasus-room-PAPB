package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel
import com.example.inventory.ui.item.ItemEntryViewModel

/** Menyediakan Factory untuk membuat instance ViewModel untuk seluruh aplikasi Inventory.*/
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Inisialisasi untuk ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle() // Membuat SavedStateHandle untuk menyimpan status UI
            )
        }
        // Inisialisasi untuk ItemEntryViewModel
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository) // Menggunakan repository untuk mengelola data item
        }

        // Inisialisasi untuk ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle() // Membuat SavedStateHandle untuk menyimpan status UI
            )
        }

        // Inisialisasi untuk HomeViewModel
        initializer {
            HomeViewModel() // Menginisialisasi HomeViewModel tanpa parameter
        }
    }
}

/** Ekstensi fungsi untuk mendapatkan objek [Application] dan mengembalikan instance dari [InventoryApplication].*/
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)