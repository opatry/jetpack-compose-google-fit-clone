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

package net.opatry.composefit.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import net.opatry.composefit.model.Metric
import net.opatry.composefit.model.UserProfile
import net.opatry.composefit.ui.home.component.HomeMainMetricsCircleIndicators
import net.opatry.composefit.ui.home.component.HomeSecondaryMetricsSummary
import net.opatry.composefit.ui.home.component.HomeToolbar
import net.opatry.composefit.ui.home.component.MetricSummaryCard
import kotlin.time.ExperimentalTime

@Composable
@ExperimentalTime
fun HomeScreen(
    userProfile: UserProfile,
    steps: Metric.Step,
    heartPoints: Metric.HeartPoint,
    secondaryMetrics: List<Metric>,
    otherMetrics: List<Pair<Color, Int>>
) {
    Column {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            item {
                HomeHeader(userProfile, steps, heartPoints, secondaryMetrics)
            }
            items(otherMetrics) { (color, titleRes) ->
                MetricSummaryCard(color = color, stringResource(titleRes)) { /* TODO on card click */ }
            }
        }
    }
}

@Composable
@ExperimentalTime
fun HomeHeader(
    userProfile: UserProfile,
    steps: Metric.Step,
    heartPoints: Metric.HeartPoint,
    secondaryMetrics: List<Metric>
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colors.surface,
                        MaterialTheme.colors.background
                    )
                )
            ),
        // FIXME should only need to center the HomeIntro
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeToolbar(userProfile.pictureUrl, userProfile.name) { /* TODO user profile switch dialog */ }
        HomeMainMetricsCircleIndicators(steps, heartPoints)
        HomeSecondaryMetricsSummary(secondaryMetrics)
    }
}
