package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import java.text.NumberFormat

/** ViewModel ini bertanggung jawab untuk memvalidasi dan menyimpan item dalam database Room. */
class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    /** Menyimpan status UI item saat ini.*/
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /** Memperbarui [itemUiState] dengan detail item baru dan memvalidasi input.*/
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    /** Memvalidasi input dari [ItemDetails]. Mengembalikan true jika semua field tidak kosong.*/
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    /** Menyimpan item ke dalam database jika input valid.*/
    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }
}

/** Representasi status UI untuk sebuah item.*/
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

/** Mengonversi [ItemDetails] menjadi [Item]. Mengatur harga dan jumlah ke 0 jika tidak valid.*/
fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

/** Mengembalikan harga item dalam format mata uang.*/
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/** Mengonversi [Item] menjadi [ItemUiState].*/
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/** Mengonversi [Item] menjadi [ItemDetails].*/
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)