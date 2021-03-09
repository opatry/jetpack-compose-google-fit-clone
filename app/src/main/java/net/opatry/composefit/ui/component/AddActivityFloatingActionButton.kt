/*
 * Copyright (c) 2021 Olivier Patry
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.opatry.composefit.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.opatry.composefit.R

@Composable
fun AddActivityFloatingActionButton(
    actions: List<Pair<ImageVector, Int>>,
    onActionClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 45f else 0f)
    // TODO animate color transition
    val fabBackgroundColor = MaterialTheme.colors.surface
    val inactiveColor =
        MaterialTheme.colors.contentColorFor(fabBackgroundColor).copy(alpha = ContentAlpha.medium)
    val primaryColor = MaterialTheme.colors.primary
    val fabIconTint = if (expanded) inactiveColor else primaryColor

    // FIXME not the right anim, this makes label moves from right to left
    val miniFabSize by animateDpAsState(if (expanded) 40.dp else 0.dp)

    val scrimBackgroundAlpha by animateFloatAsState(if (expanded) .98f else 0f)
    val scrimBackgroundColor = with(LocalContext.current) {
        MaterialTheme.colors.surface
    }

    // TODO scrim color full screen, it seems state should be hoisted so that the background layer is managed by parent Scaffold
    // TODO dismiss on scrim tap
    Surface(
        color = scrimBackgroundColor.copy(alpha = scrimBackgroundAlpha)
    ) {
        Column(
            Modifier.padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            if (expanded) {
                // reverse action to have most important one close to the main FAB
                actions.reversed().forEach { action ->
                    // TODO it's the row which is clickable (/!\ w/o ripple)
                    Row(
                        Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(action.second), fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.width(24.dp))
                        FloatingActionButton(
                            onClick = onActionClick,
                            Modifier
                                .padding(4.dp)
                                .size(miniFabSize),
                            backgroundColor = fabBackgroundColor,
                            contentColor = primaryColor
                        ) {
                            Icon(
                                action.first,
                                null,
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
            FloatingActionButton(
                onClick = { expanded = !expanded },
                Modifier.rotate(rotation),
                backgroundColor = fabBackgroundColor,
                contentColor = fabIconTint
            ) {
                Icon(Icons.TwoTone.Add, stringResource(R.string.home_fab_content_description))
            }
        }
    }
}
