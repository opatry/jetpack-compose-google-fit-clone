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
package net.opatry.composefit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.opatry.composefit.model.FitActivity
import net.opatry.composefit.model.Metric
import net.opatry.composefit.model.UserProfile
import net.opatry.composefit.model.cm
import net.opatry.composefit.model.heartPoints
import net.opatry.composefit.model.kg
import net.opatry.composefit.model.km
import net.opatry.composefit.model.meters
import net.opatry.composefit.ui.component.AddActivityFloatingActionButton
import net.opatry.composefit.ui.component.ProfileSwitchDialog
import net.opatry.composefit.ui.home.HomeScreen
import net.opatry.composefit.ui.home.component.HomeToolbar
import net.opatry.composefit.ui.journal.JournalScreen
import net.opatry.composefit.ui.journal.component.JournalToolbar
import net.opatry.composefit.ui.profile.Gender
import net.opatry.composefit.ui.profile.ProfileParameter
import net.opatry.composefit.ui.profile.ProfileScreen
import net.opatry.composefit.ui.profile.component.ProfileToolbar
import net.opatry.composefit.ui.theme.FitBlue
import net.opatry.composefit.ui.theme.FitDarkBlue
import net.opatry.composefit.ui.theme.FitGreen
import net.opatry.composefit.ui.theme.FitPurple
import net.opatry.composefit.ui.theme.FitRed
import net.opatry.composefit.ui.theme.MyTheme
import java.util.Date
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.minutes
import kotlin.time.seconds

class MainActivity : AppCompatActivity() {
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                ComposeFitApp(
                    userProfile = UserProfile(
                        name = "Random",
                        pictureUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b4/Wikipe-tan_avatar.png"
                    ),
                    steps = Metric.Step(10_884),
                    heartPoints = Metric.HeartPoint(85),
                    secondaryMetrics = listOf(
                        Metric.Calorie(1753),
                        Metric.Distance(9080),
                        Metric.Move(98),
                        Metric.Sleep(8.hours + 41.minutes, 0.seconds)
                    ),
                    otherMetrics = listOf(
                        FitBlue to R.string.home_card_daily_goals_title,
                        FitGreen to R.string.home_card_weekly_target_title,
                        FitPurple to R.string.home_card_sleep_duration_title,
                        FitPurple to R.string.home_card_bedtime_schedule_title,
                        FitGreen to R.string.home_card_heart_points_title,
                        FitDarkBlue to R.string.home_card_steps_title,
                        FitRed to R.string.home_card_heart_rate_title,
                        FitBlue to R.string.home_card_weight_title,
                    ),
                    activities = listOf(
                        FitActivity.Walk(Date(2021, 2, 20), "Walk1", "Mi Fit", 800.meters, 19.minutes),
                        FitActivity.Run(Date(2021, 2, 20), "Run1", "Mi Fit", 5.km + 504.meters, 19.minutes, 20.heartPoints),
                        FitActivity.Sleep(Date(2021, 2, 20), "Sleep", "Mi Fit", Metric.Sleep(7.hours + 52.minutes, 50.minutes)),
                        // ---
                        FitActivity.Walk(Date(2021, 2, 19), "Walk1", "Mi Fit", 420.meters, 9.minutes),
                        FitActivity.Walk(Date(2021, 2, 19), "Walk2", "Mi Fit", 35.meters, 1.minutes),
                        FitActivity.Walk(Date(2021, 2, 19), "Walk3", "Mi Fit", 5.km + 10.meters, 40.minutes),
                        FitActivity.Walk(Date(2021, 2, 19), "Walk4", "Mi Fit", 703.meters, 15.minutes),
                        FitActivity.Sleep(Date(2021, 2, 19), "Sleep", "Mi Fit", Metric.Sleep(8.hours + 2.minutes, 1.hours + 30.minutes)),
                    ) // TODO sort by date
                )
            }
        }
    }
}

private enum class FitTabs(
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    Home(R.string.nav_home, Icons.Outlined.Home),
    Journal(R.string.nav_journal, Icons.Outlined.Article),
    Profile(R.string.nav_profile, Icons.Outlined.Person)
}

@Composable
@ExperimentalTime
fun ComposeFitApp(
    userProfile: UserProfile,
    steps: Metric.Step,
    heartPoints: Metric.HeartPoint,
    secondaryMetrics: List<Metric>,
    otherMetrics: List<Pair<Color, Int>>,
    activities: List<FitActivity>
) {
    val coroutineScope = rememberCoroutineScope()

    val (selectedTab, setSelectedTab) = remember { mutableStateOf(FitTabs.Home) }
    val tabs = FitTabs.values()

    val (profileName, profilePictureUrl) = userProfile
    val (isJournalRefreshing, setJournalRefreshing) = remember { mutableStateOf(false) }

    val (showProfileSwitchDialog, toggleProfileSwitchDialog) = remember { mutableStateOf(false) }
    ProfileSwitchDialog(showProfileSwitchDialog, toggleProfileSwitchDialog)

    val onUserProfileClick = { toggleProfileSwitchDialog(true) }

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                when (selectedTab) {
                    // FIXME make HomeToolbar disappear on scroll
                    FitTabs.Home -> HomeToolbar(profilePictureUrl, profileName, onUserProfileClick)
                    // FIXME make JournalToolbar lift on scroll
                    FitTabs.Journal -> JournalToolbar(
                        profilePictureUrl,
                        profileName,
                        !isJournalRefreshing,
                        onRefreshClick = {
                            coroutineScope.launch {
                            setJournalRefreshing(true)
                                // Fake remote call to mimic refresh
                                withContext(Dispatchers.IO) {
                                    delay(Random.nextLong(200, 5000))
                                }
                                setJournalRefreshing(false)
                            }
                        },
                        onUserProfileClick
                    )
                    // FIXME make ProfileToolbar lift on scroll
                    FitTabs.Profile -> ProfileToolbar(profilePictureUrl, profileName, onUserProfileClick)
                }
            },
            bottomBar = {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = .98f),
                    elevation = 0.dp
                ) {
                    tabs.forEach { navItem ->
                        BottomNavigationItem(
                            icon = { Icon(navItem.icon, null) },
                            label = { Text(stringResource(navItem.titleRes)) },
                            selected = selectedTab == navItem,
                            selectedContentColor = MaterialTheme.colors.primary,
                            unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                            onClick = { setSelectedTab(navItem) }
                        )
                    }
                }
            },
            floatingActionButton = {
                if (selectedTab == FitTabs.Home || selectedTab == FitTabs.Journal) {
                    AddActivityFloatingActionButton(
                        listOf(
                            Icons.Outlined.DirectionsRun to R.string.home_fab_speed_dial_track_workout,
                            Icons.Outlined.Create to R.string.home_fab_speed_dial_add_activity,
                            Icons.Outlined.MonitorWeight to R.string.home_fab_speed_dial_add_weight,
                            Icons.Outlined.Straighten to R.string.home_fab_speed_dial_add_blood_pressure,
                        )
                    ) { /* TODO on action clicked */ }
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                when (selectedTab) {
                    FitTabs.Home -> HomeScreen(
                        userProfile,
                        steps,
                        heartPoints,
                        secondaryMetrics,
                        otherMetrics
                    )
                    FitTabs.Journal -> JournalScreen(userProfile, activities, isJournalRefreshing)
                    FitTabs.Profile -> {
                        // userProfile.parameters
                        val parameters = listOf(
                            ProfileParameter.ActivityGoals(Metric.Step(7000), Metric.HeartPoint(10)),
                            ProfileParameter.BedtimeSchedule(23, 8),
                            ProfileParameter.About(Gender.Male, Date(1985, 0, 31), 82.2f.kg, 185.cm),
                        )
                        ProfileScreen(parameters)
                    }
                }
            }
        }
    }
}