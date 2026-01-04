package com.maternitytracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maternitytracker.R
import com.maternitytracker.data.entities.ShoppingItem
import com.maternitytracker.ui.components.AddEditItemDialog
import com.maternitytracker.ui.components.BudgetSummaryCard
import com.maternitytracker.ui.components.LabelFilterChips
import com.maternitytracker.ui.components.PurchaseAnalyticsChart
import com.maternitytracker.ui.components.QuickPurchaseDialog
import com.maternitytracker.ui.components.ShoppingItemCard
import com.maternitytracker.viewmodel.ShoppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ShoppingViewModel,
    onNavigateToLabelManagement: () -> Unit
) {
    val filteredItems by viewModel.filteredItems.collectAsStateWithLifecycle(initialValue = emptyList())
    val allLabels by viewModel.allLabels.collectAsStateWithLifecycle(initialValue = emptyList())
    val selectedLabels by viewModel.selectedLabels.collectAsStateWithLifecycle()
    val filterMode by viewModel.filterMode.collectAsStateWithLifecycle()
    val showPurchased by viewModel.showPurchased.collectAsStateWithLifecycle()
    val budgetInfo by viewModel.budgetInfo.collectAsStateWithLifecycle(
        initialValue = ShoppingViewModel.BudgetInfo(0.0, 0.0, 0.0, 0, 0, 0f)
    )

    var showAddDialog by remember { mutableStateOf(false) }
    var showQuickPurchaseDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ShoppingItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with title and settings
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onNavigateToLabelManagement) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.label_management)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Budget Summary Card
        BudgetSummaryCard(budgetInfo = budgetInfo)

        Spacer(modifier = Modifier.height(16.dp))

        // Purchase Analytics Chart
        if (budgetInfo.totalCount > 0) {
            PurchaseAnalyticsChart(
                purchasedCount = budgetInfo.purchasedCount,
                totalCount = budgetInfo.totalCount,
                modifier = Modifier.height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Filter Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (filterMode == ShoppingViewModel.FilterMode.AND) 
                        stringResource(R.string.filter_and) else stringResource(R.string.filter_or),
                    style = MaterialTheme.typography.bodySmall
                )
                Switch(
                    checked = filterMode == ShoppingViewModel.FilterMode.OR,
                    onCheckedChange = { 
                        viewModel.setFilterMode(
                            if (it) ShoppingViewModel.FilterMode.OR 
                            else ShoppingViewModel.FilterMode.AND
                        )
                    }
                )
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.show_purchased),
                    style = MaterialTheme.typography.bodySmall
                )
                Switch(
                    checked = showPurchased,
                    onCheckedChange = viewModel::setShowPurchased
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Label Filter Chips
        LabelFilterChips(
            labels = allLabels,
            selectedLabels = selectedLabels,
            onLabelToggle = viewModel::toggleLabelFilter,
            onClearFilters = viewModel::clearLabelFilters
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.add_item))
            }
            
            OutlinedButton(
                onClick = { showQuickPurchaseDialog = true },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.quick_purchase))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Shopping Items List
        if (filteredItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.no_items),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = stringResource(R.string.add_first_item),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredItems) { item ->
                    ShoppingItemCard(
                        item = item,
                        onEdit = { editingItem = item },
                        onDelete = { viewModel.deleteItem(item) },
                        onTogglePurchase = { actualPrice ->
                            if (item.isPurchased) {
                                viewModel.markItemAsUnpurchased(item.id)
                            } else {
                                viewModel.markItemAsPurchased(item.id, actualPrice)
                            }
                        }
                    )
                }
            }
        }
    }

    // Dialogs
    if (showAddDialog) {
        AddEditItemDialog(
            item = null,
            labels = allLabels,
            onDismiss = { showAddDialog = false },
            onSave = { item ->
                viewModel.insertItem(item)
                showAddDialog = false
            }
        )
    }

    editingItem?.let { item ->
        AddEditItemDialog(
            item = item,
            labels = allLabels,
            onDismiss = { editingItem = null },
            onSave = { updatedItem ->
                viewModel.updateItem(updatedItem)
                editingItem = null
            }
        )
    }

    if (showQuickPurchaseDialog) {
        QuickPurchaseDialog(
            viewModel = viewModel,
            onDismiss = { showQuickPurchaseDialog = false }
        )
    }
}