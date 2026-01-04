package com.maternitytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.maternitytracker.R
import com.maternitytracker.data.entities.Label
import com.maternitytracker.data.entities.ShoppingItem

@Composable
fun AddEditItemDialog(
    item: ShoppingItem?,
    labels: List<Label>,
    onDismiss: () -> Unit,
    onSave: (ShoppingItem) -> Unit
) {
    var name by remember { mutableStateOf(item?.name ?: "") }
    var quantity by remember { mutableStateOf(item?.quantity?.toString() ?: "1") }
    var estimatedPrice by remember { mutableStateOf(item?.estimatedPrice?.toString() ?: "") }
    var selectedLabels by remember { 
        mutableStateOf(
            item?.labels?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }?.toSet() ?: emptySet()
        )
    }
    
    var nameError by remember { mutableStateOf(false) }
    var quantityError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = if (item == null) stringResource(R.string.add_item) else stringResource(R.string.edit_item),
                    style = MaterialTheme.typography.headlineSmall
                )

                // Item Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { 
                        name = it
                        nameError = false
                    },
                    label = { Text(stringResource(R.string.item_name)) },
                    isError = nameError,
                    supportingText = if (nameError) {
                        { Text(stringResource(R.string.error_name_required)) }
                    } else null,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Quantity
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { 
                            quantity = it
                            quantityError = false
                        },
                        label = { Text(stringResource(R.string.quantity)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = quantityError,
                        supportingText = if (quantityError) {
                            { Text(stringResource(R.string.error_quantity_invalid)) }
                        } else null,
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    // Estimated Price
                    OutlinedTextField(
                        value = estimatedPrice,
                        onValueChange = { 
                            estimatedPrice = it
                            priceError = false
                        },
                        label = { Text(stringResource(R.string.estimated_price)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = priceError,
                        supportingText = if (priceError) {
                            { Text(stringResource(R.string.error_price_invalid)) }
                        } else null,
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                // Labels Section
                if (labels.isNotEmpty()) {
                    Column {
                        Text(
                            text = stringResource(R.string.labels),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(labels) { label ->
                                LabelSelectionChip(
                                    label = label,
                                    isSelected = label.name in selectedLabels,
                                    onToggle = {
                                        selectedLabels = if (label.name in selectedLabels) {
                                            selectedLabels - label.name
                                        } else {
                                            selectedLabels + label.name
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    
                    Button(
                        onClick = {
                            // Validation
                            nameError = name.isBlank()
                            quantityError = quantity.toIntOrNull()?.let { it <= 0 } ?: true
                            priceError = estimatedPrice.toDoubleOrNull()?.let { it <= 0 } ?: true
                            
                            if (!nameError && !quantityError && !priceError) {
                                val newItem = ShoppingItem(
                                    id = item?.id ?: 0,
                                    name = name.trim(),
                                    quantity = quantity.toInt(),
                                    estimatedPrice = estimatedPrice.toDouble(),
                                    actualPrice = item?.actualPrice,
                                    labels = selectedLabels.joinToString(","),
                                    isPurchased = item?.isPurchased ?: false,
                                    purchasedAt = item?.purchasedAt,
                                    createdAt = item?.createdAt ?: java.util.Date()
                                )
                                onSave(newItem)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Composable
private fun LabelSelectionChip(
    label: Label,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val backgroundColor = Color(android.graphics.Color.parseColor(label.color))
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable { onToggle() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black
                )
            }
            Text(
                text = label.name,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
        }
    }
}