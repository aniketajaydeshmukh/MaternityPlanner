package com.maternitytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.maternitytracker.R
import com.maternitytracker.data.entities.ShoppingItem
import com.maternitytracker.ui.theme.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ShoppingItemCard(
    item: ShoppingItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onTogglePurchase: (Double) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showPurchaseDialog by remember { mutableStateOf(false) }
    var actualPriceText by remember { mutableStateOf("") }

    val currencyFormat = NumberFormat.getCurrencyInstance()
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isPurchased) 
                MaterialTheme.colorScheme.surfaceVariant 
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with name and actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (item.isPurchased) TextDecoration.LineThrough else null,
                    modifier = Modifier.weight(1f)
                )
                
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_item),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete),
                            tint = ErrorRed
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quantity and Price Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${stringResource(R.string.quantity)}: ${item.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${stringResource(R.string.estimated_price)}: ${currencyFormat.format(item.estimatedPrice * item.quantity)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    if (item.isPurchased && item.actualPrice != null) {
                        Text(
                            text = "${stringResource(R.string.actual_price)}: ${currencyFormat.format(item.actualPrice * item.quantity)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Labels
            if (item.labels.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LabelChips(labels = item.labels.split(",").map { it.trim() }.filter { it.isNotEmpty() })
            }

            // Purchase info
            if (item.isPurchased && item.purchasedAt != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Purchased on ${dateFormat.format(item.purchasedAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = SuccessGreen
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Purchase Button
            Button(
                onClick = {
                    if (item.isPurchased) {
                        onTogglePurchase(0.0)
                    } else {
                        showPurchaseDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (item.isPurchased) WarningOrange else SuccessGreen
                )
            ) {
                Icon(
                    imageVector = if (item.isPurchased) Icons.Default.Undo else Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (item.isPurchased) 
                        stringResource(R.string.unpurchase) 
                    else stringResource(R.string.purchase)
                )
            }
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.confirm_delete)) },
            text = { Text(stringResource(R.string.delete_item_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }

    // Purchase Dialog
    if (showPurchaseDialog) {
        AlertDialog(
            onDismissRequest = { showPurchaseDialog = false },
            title = { Text(stringResource(R.string.purchase)) },
            text = {
                Column {
                    Text("Enter the actual price for ${item.name}:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = actualPriceText,
                        onValueChange = { actualPriceText = it },
                        label = { Text(stringResource(R.string.actual_price)) },
                        placeholder = { Text(currencyFormat.format(item.estimatedPrice)) },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val actualPrice = actualPriceText.toDoubleOrNull() ?: item.estimatedPrice
                        onTogglePurchase(actualPrice)
                        showPurchaseDialog = false
                        actualPriceText = ""
                    }
                ) {
                    Text(stringResource(R.string.purchase))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        showPurchaseDialog = false
                        actualPriceText = ""
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun LabelChips(labels: List<String>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        labels.take(3).forEach { label ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(LabelLavender)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black
                )
            }
        }
        if (labels.size > 3) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.outline)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "+${labels.size - 3}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}