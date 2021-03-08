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

package net.opatry.composefit.ui.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.opatry.composefit.R
import net.opatry.composefit.model.Metric
import net.opatry.composefit.ui.component.FitListHeader
import java.util.Date

enum class Gender {
    Male,
    Female,
}

sealed class ProfileParameter {
    data class ActivityGoals(val step: Metric.Step, val heartPoint: Metric.HeartPoint) : ProfileParameter()
    data class BedtimeSchedule(val begin: Int/*FIXME Time()*/, val end: Int/*FIXME Time()*/) : ProfileParameter()
    data class About(val gender: Gender, val birthday: Date, val weight: Metric.Weight, val height: Metric.Distance) : ProfileParameter()
}

@get:StringRes
val ProfileParameter.labelRes
    get() = when (this) {
        is ProfileParameter.ActivityGoals -> R.string.profile_parameters_activity_goals
        is ProfileParameter.BedtimeSchedule -> R.string.profile_parameters_bedtime_schedule
        is ProfileParameter.About -> R.string.profile_parameters_about
    }

@Composable
fun ProfileScreen(profileParameters: List<ProfileParameter>) {
    // profileParameters.groupBy {}
    val groups = mutableMapOf<String, List<ProfileParameter>>().apply {
        profileParameters.forEach { profileParameter ->
            this += stringResource(profileParameter.labelRes) to listOf(profileParameter)
        }
    }
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        groups.forEach { (header, parameters) ->
            item {
                FitListHeader(header)
            }
            items(parameters) { param ->
                // when (param) {
                //     is ProfileParameter.ActivityGoals
                //     is ProfileParameter.BedtimeSchedule
                //     is ProfileParameter.ActivityGoals
                // }
                Row(Modifier.padding(vertical = 16.dp, horizontal = 24.dp)) {
                    TextField(value = "TODO", onValueChange = { /*TODO*/ })
                }
            }
        }
    }
}