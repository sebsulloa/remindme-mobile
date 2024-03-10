package com.misw.remindme.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun MultiFloatingActionButton(
    modifier: Modifier = Modifier,
    items: List<FabButtonItem>,
    fabState: MutableState<FabButtonState> = rememberMultiFabState(),
    fabIcon: FabButtonMain,
    fabOption: FabButtonSub = FabButtonSub(),
    onFabItemClicked: (fabItem: FabButtonItem) -> Unit,
    stateChanged: (fabState: FabButtonState) -> Unit = {}
) {
    // Animation for rotating the main FAB icon based on its state (expanded or collapsed)
    val rotation by animateFloatAsState(
        if (fabState.value == FabButtonState.Expand) {
            fabIcon.iconRotate ?: 0f
        } else {
            0f
        }, label = "rotate"
    )

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        // AnimatedVisibility to show or hide the sub-items when the Multi-FAB is expanded or collapsed
        AnimatedVisibility(
            visible = fabState.value.isExpanded(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            // LazyColumn to display the sub-items in a vertical list
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(items.size) { index ->
                    // Composable to display each individual sub-item
                    MiniFabItem(
                        item = items[index],
                        fabOption = fabOption,
                        onFabItemClicked = onFabItemClicked
                    )
                }
                item {} // Empty item to provide spacing at the end of the list
            }
        }

        // Main FloatingActionButton representing the Multi-FAB
        FloatingActionButton(
            onClick = {
                fabState.value = fabState.value.toggleValue()
                stateChanged(fabState.value)
            },
            containerColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            // Icon for the main FAB with optional rotation based on its state (expanded or collapsed)
            Icon(
                imageVector = fabIcon.iconRes,
                contentDescription = "main",
                modifier = Modifier.rotate(rotation),
                tint = fabOption.iconTint
            )
        }
    }
}


@Composable
fun rememberMultiFabState() =
    remember { mutableStateOf<FabButtonState>(FabButtonState.Collapsed) }