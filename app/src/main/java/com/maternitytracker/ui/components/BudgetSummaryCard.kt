package com.maternitytracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maternitytracker.R
import com.maternitytracker.ui.theme.*
import com.maternitytracker.viewmodel.ShoppingViewModel
import java.text.NumberFormat

@Composable
fun BudgetSummaryCard(
    budgetInfo: ShoppingViewModel.BudgetInfo,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance()

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.budget_summary),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Estimated Total
                BudgetStatItem(
                    icon = Icons.Default.Calculate,
                    label = stringResource(R.string.estimated_total),
                    value = currencyFormat.format(budgetInfo.totalEstimated),
                    color = InfoBlue
                )

                // Actual Total
                BudgetStatItem(
                    icon = Icons.Default.ShoppingCart,
                    label = stringResource(R.string.actual_total),
                    value = currencyFormat.format(budgetInfo.totalActual),
                    color = SuccessGreen
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Remaining Budget
                BudgetStatItem(
                    icon = Icons.Default.AccountBalance,
                    label = stringResource(R.string.remaining_budget),
                    value = currencyFormat.format(budgetInfo.remaining),
                    color = if (budgetInfo.remaining >= 0) SuccessGreen else ErrorRed
                )

                // Progress
                BudgetStatItem(
                    icon = Icons.Default.TrendingUp,
                    label = stringResource(R.string.progress),
                    value = "${(budgetInfo.progressPercentage * 100).toInt()}%",
                    color = PrimaryLavender
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${stringResource(R.string.purchased_items)}: ${budgetInfo.purchasedCount}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "${stringResource(R.string.remaining_items)}: ${budgetInfo.totalCount - budgetInfo.purchasedCount}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = budgetInfo.progressPercentage,
                    modifier = Modifier.fillMaxWidth(),
                    color = SuccessGreen,
                    trackColor = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
private fun BudgetStatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}