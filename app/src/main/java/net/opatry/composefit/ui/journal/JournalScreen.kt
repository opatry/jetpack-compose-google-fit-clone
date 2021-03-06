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

package net.opatry.composefit.ui.journal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.opatry.composefit.model.UserProfile
import net.opatry.composefit.ui.JournalViewModel
import net.opatry.composefit.ui.JournalViewModelFactory
import net.opatry.composefit.ui.component.FitListHeader
import net.opatry.composefit.ui.component.TweakableProgressIndicator
import net.opatry.composefit.ui.journal.component.FitActivityItem
import java.text.SimpleDateFormat
import kotlin.time.ExperimentalTime

@Composable
@ExperimentalTime
fun JournalScreen(userProfile: UserProfile, isRefreshing: Boolean) {
    val viewModel = viewModel<JournalViewModel>(factory = JournalViewModelFactory(userProfile))
    val activities by viewModel.activities.observeAsState(listOf())

    Column {
        if (isRefreshing) {
            FitJournalRefreshIndicator(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        } else {
            // avoids content moving when progress indicator appears/disappears
            Spacer(Modifier.height(1.dp))
        }

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            val groups = activities.groupBy { it.date /*FIXME date's day*/ }
            groups.forEach { (header, activities) ->
                item {
                    // TODO retrieve total steps & heart points for such group
                    // TODO display MetricProgressIndicator
                    // FIXME idiomatic kotlin date formatting
                    val dateFormat = SimpleDateFormat("E, LLL d")
                    FitListHeader(dateFormat.format(header))
                }
                items(activities) { activity ->
                    FitActivityItem(activity) {
                        // TODO only activity click
                    }
                }
            }
        }
    }
}

@Composable
fun FitJournalRefreshIndicator(modifier: Modifier) {
    // TODO tweak what needs to be tweaked
    //  - height
    //  - colors
    TweakableProgressIndicator(modifier)
}
