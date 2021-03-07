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

package net.opatry.composefit.ui.journal.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.opatry.composefit.model.FitActivity
import net.opatry.composefit.ui.theme.typography
import net.opatry.composefit.ui.util.icon
import kotlin.time.ExperimentalTime

@Composable
@ExperimentalTime
fun FitActivityItem(activity: FitActivity, onActivityClick: (FitActivity) -> Unit) {
    // FIXME time formatting
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onActivityClick(activity) }
            .padding(vertical = 16.dp, horizontal = 24.dp)) {
        Row {
            Icon(activity.icon, null,
                Modifier
                    .padding(end = 8.dp)
                    .size(16.dp))
            Text("23:11 • ${activity.source}", style = typography.caption, color = LocalContentColor.current.copy(alpha = ContentAlpha.medium))
        }
        Text(activity.label, style = typography.subtitle1)
        // FIXME depends on activity type
        FitActivityItemSummary(activity)
    }
}

@ExperimentalTime
@Composable
fun FitActivityItemSummary(activity: FitActivity) {
    Text("Activity summary (eg. 0.11km in 16m)", style = typography.body2, color = LocalContentColor.current.copy(alpha = ContentAlpha.medium))
}
