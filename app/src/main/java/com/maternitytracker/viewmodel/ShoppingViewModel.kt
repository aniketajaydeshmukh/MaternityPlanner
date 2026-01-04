package com.maternitytracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.maternitytracker.data.entities.Label
import com.maternitytracker.data.entities.ShoppingItem
import com.maternitytracker.data.repository.ShoppingRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingRepository) : ViewModel() {
    
    // State flows for UI state
    private val _selectedLabels = MutableStateFlow<Set<String>>(emptySet())
    val selectedLabels: StateFlow<Set<String>> = _selectedLabels.asStateFlow()
    
    private val _filterMode = MutableStateFlow(FilterMode.AND)
    val filterMode: StateFlow<FilterMode> = _filterMode.asStateFlow()
    
    private val _showPurchased = MutableStateFlow(false)
    val showPurchased: StateFlow<Boolean> = _showPurchased.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val searchResults: StateFlow<List<ShoppingItem>> = _searchResults.asStateFlow()
    
    // Data flows
    val allItems = repository.getAllItems()
    val allLabels = repository.getAllLabels()
    
    // Computed flows
    val filteredItems: Flow<List<ShoppingItem>> = combine(
        allItems,
        selectedLabels,
        filterMode,
        showPurchased
    ) { items, labels, mode, showPurchased ->
        items.filter { item ->
            val purchaseFilter = if (showPurchased) true else !item.isPurchased
            val labelFilter = if (labels.isEmpty()) {
                true
            } else {
                val itemLabels = item.labels.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                when (mode) {
                    FilterMode.AND -> labels.all { it in itemLabels }
                    FilterMode.OR -> labels.any { it in itemLabels }
                }
            }
            purchaseFilter && labelFilter
        }
    }
    
    val budgetInfo: Flow<BudgetInfo> = allItems.map { items ->
        val totalEstimated = items.sumOf { it.estimatedPrice * it.quantity }
        val totalActual = items.filter { it.isPurchased && it.actualPrice != null }
            .sumOf { (it.actualPrice ?: 0.0) * it.quantity }
        val purchasedCount = items.count { it.isPurchased }
        val totalCount = items.size
        val progressPercentage = if (totalCount > 0) purchasedCount.toFloat() / totalCount else 0f
        
        BudgetInfo(
            totalEstimated = totalEstimated,
            totalActual = totalActual,
            remaining = totalEstimated - totalActual,
            purchasedCount = purchasedCount,
            totalCount = totalCount,
            progressPercentage = progressPercentage
        )
    }
    
    // Shopping Item operations
    fun insertItem(item: ShoppingItem) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }
    
    fun updateItem(item: ShoppingItem) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }
    
    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
    
    fun markItemAsPurchased(id: Long, actualPrice: Double) {
        viewModelScope.launch {
            repository.markItemAsPurchased(id, actualPrice)
        }
    }
    
    fun markItemAsUnpurchased(id: Long) {
        viewModelScope.launch {
            repository.markItemAsUnpurchased(id)
        }
    }
    
    // Label operations
    fun insertLabel(label: Label) {
        viewModelScope.launch {
            repository.insertLabel(label)
        }
    }
    
    fun updateLabel(label: Label) {
        viewModelScope.launch {
            repository.updateLabel(label)
        }
    }
    
    fun deleteLabel(label: Label) {
        viewModelScope.launch {
            repository.deleteLabel(label)
        }
    }
    
    // Filter operations
    fun toggleLabelFilter(labelName: String) {
        val current = _selectedLabels.value.toMutableSet()
        if (labelName in current) {
            current.remove(labelName)
        } else {
            current.add(labelName)
        }
        _selectedLabels.value = current
    }
    
    fun clearLabelFilters() {
        _selectedLabels.value = emptySet()
    }
    
    fun setFilterMode(mode: FilterMode) {
        _filterMode.value = mode
    }
    
    fun setShowPurchased(show: Boolean) {
        _showPurchased.value = show
    }
    
    // Search operations
    fun searchUnpurchasedItems(query: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchUnpurchasedItems(query)
        }
    }
    
    fun clearSearchResults() {
        _searchResults.value = emptyList()
    }
    
    enum class FilterMode { AND, OR }
    
    data class BudgetInfo(
        val totalEstimated: Double,
        val totalActual: Double,
        val remaining: Double,
        val purchasedCount: Int,
        val totalCount: Int,
        val progressPercentage: Float
    )
}

class ShoppingViewModelFactory(private val repository: ShoppingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}