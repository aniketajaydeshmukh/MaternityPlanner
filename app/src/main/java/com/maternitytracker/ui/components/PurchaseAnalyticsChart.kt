package com.maternitytracker.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maternitytracker.ui.theme.*
import kotlin.math.*

@Composable
fun PurchaseAnalyticsChart(
    purchasedCount: Int,
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    val purchasedPercentage = if (totalCount > 0) purchasedCount.toFloat() / totalCount else 0f
    val remainingPercentage = 1f - purchasedPercentage
    
    // Animation for the chart
    val animatedPurchasedPercentage by animateFloatAsState(
        targetValue = purchasedPercentage,
        animationSpec = tween(durationMillis = 1000, easing = EaseInOutCubic),
        label = "purchased_animation"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Purchase Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawPieChart(
                        purchasedPercentage = animatedPurchasedPercentage,
                        remainingPercentage = 1f - animatedPurchasedPercentage
                    )
                }
                
                // Center text showing percentage
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${(animatedPurchasedPercentage * 100).toInt()}%",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = SuccessGreen
                    )
                    Text(
                        text = "Complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LegendItem(
                    color = SuccessGreen,
                    label = "Purchased",
                    count = purchasedCount
                )
                LegendItem(
                    color = MaterialTheme.colorScheme.outline,
                    label = "Remaining",
                    count = totalCount - purchasedCount
                )
            }
        }
    }
}

@Composable
private fun LegendItem(
    color: Color,
    label: String,
    count: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .drawWithContent {
                    drawCircle(color = color)
                }
        )
        Text(
            text = "$label ($count)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun DrawScope.drawPieChart(
    purchasedPercentage: Float,
    remainingPercentage: Float
) {
    val strokeWidth = 16.dp.toPx()
    val radius = (size.minDimension - strokeWidth) / 2
    val center = Offset(size.width / 2, size.height / 2)
    
    // Background circle
    drawCircle(
        color = Color.Gray.copy(alpha = 0.2f),
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidth)
    )
    
    // Purchased arc
    if (purchasedPercentage > 0) {
        drawArc(
            color = SuccessGreen,
            startAngle = -90f,
            sweepAngle = purchasedPercentage * 360f,
            useCenter = false,
            topLeft = Offset(
                center.x - radius,
                center.y - radius
            ),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = androidx.compose.ui.graphics.StrokeCap.Round)
        )
    }
}