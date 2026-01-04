package com.maternitytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maternitytracker.R
import com.maternitytracker.data.entities.Label
import com.maternitytracker.ui.theme.LabelColors

@Composable
fun LabelFilterChips(
    labels: List<Label>,
    selectedLabels: Set<String>,
    onLabelToggle: (String) -> Unit,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.labels),
                style = MaterialTheme.typography.titleSmall
            )
            
            if (selectedLabels.isNotEmpty()) {
                TextButton(onClick = onClearFilters) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Clear")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(labels) { label ->
                LabelFilterChip(
                    label = label,
                    isSelected = label.name in selectedLabels,
                    onClick = { onLabelToggle(label.name) }
                )
            }
        }
    }
}

@Composable
private fun LabelFilterChip(
    label: Label,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color(android.graphics.Color.parseColor(label.color))
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    }
    
    val contentColor = if (isSelected) {
        Color.Black
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    FilterChip(
        onClick = onClick,
        label = {
            Text(
                text = label.name,
                color = contentColor
            )
        },
        selected = isSelected,
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(android.graphics.Color.parseColor(label.color)))
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = backgroundColor,
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}