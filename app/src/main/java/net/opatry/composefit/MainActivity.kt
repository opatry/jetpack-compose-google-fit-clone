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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.opatry.composefit.data.ProfileRepository
import net.opatry.composefit.model.UserProfile
import net.opatry.composefit.ui.component.AddActivityFloatingActionButton
import net.opatry.composefit.ui.component.ProfileSwitchDialog
import net.opatry.composefit.ui.home.HomeScreen
import net.opatry.composefit.ui.home.component.HomeToolbar
import net.opatry.composefit.ui.journal.JournalScreen
import net.opatry.composefit.ui.journal.component.JournalToolbar
import net.opatry.composefit.ui.profile.ProfileScreen
import net.opatry.composefit.ui.profile.component.ProfileToolbar
import net.opatry.composefit.ui.theme.MyTheme
import kotlin.random.Random
import kotlin.time.ExperimentalTime

val FakeProfileRepository = ProfileRepository()

class MainActivity : AppCompatActivity() {
    @ExperimentalTime
    private val userProfile = MutableLiveData<UserProfile>()
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = FakeProfileRepository
        lifecycleScope.launch(Dispatchers.Main) {
            userProfile.value = repository.getUserProfile("fake@gmail.com")
        }

        setContent {
            val userProfile by userProfile.observeAsState(null)
            userProfile?.let { profile ->
                MyTheme {
                    ComposeFitApp(profile)
                }
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
fun ComposeFitApp(userProfile: UserProfile) {
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
                    FitTabs.Home -> HomeScreen(userProfile)
                    FitTabs.Journal -> JournalScreen(userProfile, isJournalRefreshing)
                    FitTabs.Profile -> ProfileScreen(userProfile)
                }
            }
        }
    }
}