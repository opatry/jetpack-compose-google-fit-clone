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

package net.opatry.composefit.model

import androidx.compose.ui.graphics.Color
import net.opatry.composefit.R
import net.opatry.composefit.ui.theme.FitBlue
import net.opatry.composefit.ui.theme.FitDarkBlue
import net.opatry.composefit.ui.theme.FitGreen
import net.opatry.composefit.ui.theme.FitPurple
import net.opatry.composefit.ui.theme.FitRed
import java.util.Date
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.minutes
import kotlin.time.seconds

@ExperimentalTime
data class UserProfile(val name: String, val pictureUrl: String) {

    val steps: Metric.Step
    val heartPoints: Metric.HeartPoint
    val secondaryMetrics: List<Metric>
    // FIXME data model
    val otherMetrics: List<Pair<Color, Int>>

    val activities: List<FitActivity>

    val parameters: List<ProfileParameter>

    init {
        steps = Metric.Step(10_884)
        heartPoints = Metric.HeartPoint(85)
        secondaryMetrics = listOf(
            Metric.Calorie(1753),
            Metric.Distance(9080),
            Metric.Move(98),
            Metric.Sleep(8.hours + 41.minutes, 0.seconds)
        )
        otherMetrics = listOf(
            FitBlue to R.string.home_card_daily_goals_title,
            FitGreen to R.string.home_card_weekly_target_title,
            FitPurple to R.string.home_card_sleep_duration_title,
            FitPurple to R.string.home_card_bedtime_schedule_title,
            FitGreen to R.string.home_card_heart_points_title,
            FitDarkBlue to R.string.home_card_steps_title,
            FitRed to R.string.home_card_heart_rate_title,
            FitBlue to R.string.home_card_weight_title,
        )

        activities = listOf(
            FitActivity.Walk(
                Date(2021, 2, 20),
                "Walk1",
                "Mi Fit",
                800.meters,
                19.minutes
            ),
            FitActivity.Run(
                Date(2021, 2, 20),
                "Run1",
                "Mi Fit",
                5.km + 504.meters,
                19.minutes,
                20.heartPoints
            ),
            FitActivity.Sleep(
                Date(2021, 2, 20),
                "Sleep",
                "Mi Fit",
                Metric.Sleep(7.hours + 52.minutes, 50.minutes)
            ),
            // ---
            FitActivity.Walk(
                Date(2021, 2, 19),
                "Walk1",
                "Mi Fit",
                420.meters,
                9.minutes
            ),
            FitActivity.Walk(
                Date(2021, 2, 19),
                "Walk2",
                "Mi Fit",
                35.meters,
                1.minutes
            ),
            FitActivity.Walk(
                Date(2021, 2, 19),
                "Walk3",
                "Mi Fit",
                5.km + 10.meters,
                40.minutes
            ),
            FitActivity.Walk(
                Date(2021, 2, 19),
                "Walk4",
                "Mi Fit",
                703.meters,
                15.minutes
            ),
            FitActivity.Sleep(
                Date(2021, 2, 19),
                "Sleep",
                "Mi Fit",
                Metric.Sleep(8.hours + 2.minutes, 1.hours + 30.minutes)
            ),
        )

        parameters = listOf(
            ProfileParameter.ActivityGoals(Metric.Step(7000), Metric.HeartPoint(10)),
            ProfileParameter.BedtimeSchedule(23, 8),
            ProfileParameter.About(Gender.Male, Date(1985, 0, 31), 82.2f.kg, 185.cm),
        )
    }
}