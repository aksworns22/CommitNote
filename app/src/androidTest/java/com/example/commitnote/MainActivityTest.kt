package com.example.commitnote

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.commitnote.core.TimeProvider
import com.example.commitnote.core.ZlibCompressor
import com.example.commitnote.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.nio.file.Paths

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity { activity ->
            val filesDir = activity.filesDir
            filesDir.listFiles()?.forEach { it.delete() }
        }
    }

    @Test
    fun shouldDisplayNoteTitleEditText() {
        onView(withId(R.id.noteTitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayNoteContentEditText() {
        onView(withId(R.id.noteContent))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayButton() {
        onView(withId(R.id.commitButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldSaveNoteOnButtonClick() {
        activityRule.scenario.onActivity { activity ->
            activity.timeProvider = object : TimeProvider {
                override fun currentTimeMillis() = 0L
            }
        }
        onView(withId(R.id.noteTitle)).perform(typeText("hello"), closeSoftKeyboard())
        onView(withId(R.id.noteContent)).perform(typeText("world"), closeSoftKeyboard())
        onView(withId(R.id.commitButton)).perform(click())

        activityRule.scenario.onActivity { activity ->
            activity.timeProvider = object : TimeProvider {
                override fun currentTimeMillis() = 0L
            }
            val filesDir = activity.filesDir
            val commitFile = File(
                filesDir,
                Paths.get(
                    "objects",
                    "3a",
                    "39bca4821d6a2c02b64bbb54ebe3b6e032d77c"
                ).toString()
            )
            Log.d("Jang", commitFile.absolutePath)
            val blobFile = File(
                filesDir,
                Paths.get(
                    "objects",
                    "63",
                    "b77d3e36388e6c463f85726a4bb4585479d96e"
                ).toString()
            )
            assertThat(
                ZlibCompressor.decompress(
                    commitFile.readBytes()
                ),
                CoreMatchers.containsString(
                    "blob"
                )
            )
            assertThat(
                ZlibCompressor.decompress(
                    blobFile.readBytes()
                ),
                CoreMatchers.containsString(
                    "hello\\0world"
                )
            )
        }
    }

}