package com.maternitytracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maternitytracker.R
import com.maternitytracker.data.entities.ShoppingItem
import com.maternitytracker.viewmodel.ShoppingViewModel
import java.text.NumberFormat

@Composable
fun QuickPurchaseDialog(
    viewModel: ShoppingViewModel,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf<ShoppingItem?>(null) }
    var actualPriceText by remember { mutableStateOf("") }
    
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val currencyFormat = NumberFormat.getCurrencyInstance()

    // Perform search when query changes
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            viewModel.searchUnpurchasedItems(searchQuery)
        } else {
            viewModel.clearSearchResults()
        }
    }

    Dialog(onDismissRequest = {
        viewModel.clearSearchResults()
        onDismiss()
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = stringResource(R.string.quick_purchase),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (selectedItem == null) {
                    // Search Phase
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text(stringResource(R.string.search_items)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (searchResults.isNotEmpty()) {
                        Text(
                            text = "Found ${searchResults.size} items:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(searchResults) { item ->
                                QuickPurchaseItemCard(
                                    item = item,
                                    onClick = { 
                                        selectedItem = item
                                        actualPriceText = item.estimatedPrice.toString()
                                    }
                                )
                            }
                        }
                    } else if (searchQuery.isNotBlank()) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No unpurchased items found",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    // Purchase Phase
                    Text(
                        text = "Purchase: ${selectedItem!!.name}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Quantity: ${selectedItem!!.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "Estimated: ${currencyFormat.format(selectedItem!!.estimatedPrice * selectedItem!!.quantity)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = actualPriceText,
                        onValueChange = { actualPriceText = it },
                        label = { Text("${stringResource(R.string.actual_price)} (per item)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val actualPrice = actualPriceText.toDoubleOrNull() ?: 0.0
                    if (actualPrice > 0) {
                        Text(
                            text = "Total: ${currencyFormat.format(actualPrice * selectedItem!!.quantity)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (selectedItem != null) {
                        OutlinedButton(
                            onClick = { 
                                selectedItem = null
                                actualPriceText = ""
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Back")
                        }
                    }
                    
                    OutlinedButton(
                        onClick = {
                            viewModel.clearSearchResults()
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    
                    if (selectedItem != null) {
                        Button(
                            onClick = {
                                val actualPrice = actualPriceText.toDoubleOrNull() ?: selectedItem!!.estimatedPrice
                                viewModel.markItemAsPurchased(selectedItem!!.id, actualPrice)
                                viewModel.clearSearchResults()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            enabled = actualPriceText.toDoubleOrNull() != null && actualPriceText.toDoubleOrNull()!! > 0
                        ) {
                            Text(stringResource(R.string.purchase))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickPurchaseItemCard(
    item: ShoppingItem,
    onClick: () -> Unit
) {
    val currencyFormat = NumberFormat.getCurrencyInstance()
    
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Qty: ${item.quantity}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = currencyFormat.format(item.estimatedPrice * item.quantity),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
            
            if (item.labels.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                LabelChips(labels = item.labels.split(",").map { it.trim() }.filter { it.isNotEmpty() })
            }
        }
    }
}