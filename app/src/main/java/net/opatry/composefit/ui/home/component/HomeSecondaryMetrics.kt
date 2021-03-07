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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.opatry.composefit.model.Metric
import net.opatry.composefit.ui.theme.typography
import net.opatry.composefit.ui.util.color
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime

@Composable
@ExperimentalTime
fun HomeSecondaryMetricsSummary(metrics: List<Metric>) {
    LazyRow {
        items(metrics) { metric ->
            // TODO space around
            Column(
                Modifier
                    .clickable { /* TODO */ }
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val (value, label) = when (metric) {
                    is Metric.Calorie -> metric.value.toString() to "Cal"
                    is Metric.Distance -> {
                        // TODO adjust unit to value
                        // TODO 2 digits
                        (metric.meter / 1000f).toString() to "km"
                    }
                    is Metric.Move -> metric.moveMin.toString() to "Move Min"
                    is Metric.Sleep -> {
                        // TODO human readable duration "8h 12m"
                        "${metric.totalSleepDuration.inHours.roundToInt()}h" to "Sleep"
                    }
                    else -> return@items
                }
                Text(
                    value,
                    color = metric.color,
                    style = typography.h6,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    label,
                    style = typography.body2,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
