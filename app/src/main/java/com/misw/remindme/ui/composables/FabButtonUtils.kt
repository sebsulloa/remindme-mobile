package com.misw.remindme.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// FabButtonItem.kt
data class FabButtonItem(val iconRes: ImageVector, val label: String, val route: String)

// FabButtonMain.kt
interface FabButtonMain {
    val iconRes: ImageVector
    val iconRotate: Float?
}

private class FabButtonMainImpl(
    override val iconRes: ImageVector,
    override val iconRotate: Float?
) : FabButtonMain

fun FabButtonMain(iconRes: ImageVector = Icons.Filled.Add, iconRotate: Float = 45f): FabButtonMain =
    FabButtonMainImpl(iconRes, iconRotate)

// FabButtonSub.kt
interface FabButtonSub {
    val iconTint: Color
    val backgroundTint: Color
}

private class FabButtonSubImpl(
    override val iconTint: Color,
    override val backgroundTint: Color
) : FabButtonSub

@Composable
fun FabButtonSub(
    backgroundTint: Color = MaterialTheme.colorScheme.surfaceContainer,
    iconTint: Color = MaterialTheme.colorScheme.primary
): FabButtonSub = FabButtonSubImpl(iconTint, backgroundTint)

// FabButtonState.kt
sealed class FabButtonState {
    object Collapsed : FabButtonState()
    object Expand : FabButtonState()

    fun isExpanded() = this == Expand

    fun toggleValue() = if (isExpanded()) {
        Collapsed
    } else {
        Expand
    }
}
