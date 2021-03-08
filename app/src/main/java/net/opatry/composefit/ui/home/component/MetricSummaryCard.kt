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

package net.opatry.composefit.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.opatry.composefit.R
import net.opatry.composefit.ui.theme.typography

@Composable
fun MetricSummaryCard(color: Color, title: String, onCardClick: () -> Unit) {
    Card(
        Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        elevation = 1.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            Modifier
                .clickable { onCardClick() }
                .padding(16.dp)
        ) {
            Row {
                // FIXME fill remaining space once icon is laid out
                Text(title,
                    Modifier.weight(1f),
                    style = typography.subtitle2
                )
                // TODO right aligned
                Icon(
                    Icons.TwoTone.ChevronRight,
                    stringResource(R.string.metric_summary_more_details)
                )
            }
            Text(
                "Last 7 days",
                Modifier.padding(bottom = 16.dp),
                style = typography.caption,
                color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
            )
            Row {
                Text(
                    "Abc\nDef",
                    Modifier.fillMaxWidth(.33f),
                    color = color,
                    style = typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
                Box(Modifier.background(Color.LightGray).fillMaxHeight().fillMaxWidth(.67f)) {
                    Text("TODO")
                }
            }
        }
    }
}