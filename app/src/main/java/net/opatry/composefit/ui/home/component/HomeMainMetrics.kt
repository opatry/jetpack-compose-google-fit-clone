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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.opatry.composefit.model.Metric
import net.opatry.composefit.ui.component.MetricProgressIndicator
import net.opatry.composefit.ui.theme.typography
import net.opatry.composefit.ui.util.color

@Composable
fun HomeMainMetricsCircleIndicators(steps: Metric.Step, heartPoints: Metric.HeartPoint) {
    Box(
        Modifier
            .clickable(
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false)
            ) { /* go to my activity screen */ }
            .width(192.dp)
            .padding(4.dp),
        contentAlignment = Alignment.Center,
    ) {
        // main metrics circles
        MetricProgressIndicator(
            // TODO from metric to goal ratio (can be > 1f)
            progress = .5f,
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            color = heartPoints.color,
            strokeWidth = 8.dp
        )
        MetricProgressIndicator(
            // TODO from metric to goal ratio (can be > 1f)
            progress = .3f,
            Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            color = steps.color,
            strokeWidth = 8.dp
        )
        // main metrics figures within circles
        Column {
            // TODO data class Metrics + val Metrics.color ext
            Text(
                heartPoints.score.toString(),
                Modifier.fillMaxWidth(),
                style = typography.h3,
                color = heartPoints.color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                steps.count.toString(),
                Modifier.fillMaxWidth(),
                style = typography.h5,
                color = steps.color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    HomeMainMetricsLegend(steps.color, heartPoints.color)
}
