package net.jsoft.daruj.auth.presentation.screen

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.data.FakeAuthenticator
import net.jsoft.daruj.auth.di.AuthenticatorModule
import net.jsoft.daruj.auth.presentation.AuthActivity
import net.jsoft.daruj.common.di.DispatcherModule
import net.jsoft.daruj.common.di.LocalSettingsRepositoryModule
import net.jsoft.daruj.common.di.UserRepositoryModule
import net.jsoft.daruj.common.exception.WrongCodeException
import net.jsoft.daruj.common.util.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(
    DispatcherModule::class,
    AuthenticatorModule::class,
    UserRepositoryModule::class,
    LocalSettingsRepositoryModule::class
)
@OptIn(ExperimentalCoroutinesApi::class)
class VerificationScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<AuthActivity>()

    private val context: Context
        get() = composeRule.activity

    private val waitTimeProgressBar: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.Auth.WAIT_TIME_PROGRESS_BAR)

    private val errorSnackbarText: SemanticsNodeInteraction
        get() = composeRule.onUnmergedDescendantNodeWithTag(
            parentTag = MainTestTags.Snackbar.ERROR,
            descendantTag = MainTestTags.Snackbar.TEXT
        )

    private val numberKeyboard: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.NUMBER_KEYBOARD)

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.apply {
            onUnmergedNodeWithTag(MainTestTags.Auth.DIAL_CODE_TEXTBOX)
                .performTextReplacement("1")

            onUnmergedNodeWithTag(MainTestTags.Auth.PHONE_NUMBER_TEXTBOX)
                .performTextInput("6505551234")

            onUnmergedNodeWithTag(MainTestTags.Auth.NEXT_BUTTON)
                .performClick()

            TestDispatchers.IO.scheduler.advanceUntilIdle()
        }
    }

    @Test
    fun enteringCode_changesContentOfVerificationCodeBox() {
        performCodeInput("123")
        assertVerificationCodeIs("123")
    }

    @Test
    fun entering6Digits_closesKeyboard() {
        numberKeyboard.assertExists()
        performCodeInput("123455")
        numberKeyboard.assertDoesNotExist()
    }

    @Test
    fun clickingDelete_removesDigit() {
        performCodeInput("12345")
        performDelete(2)
        assertVerificationCodeIs("123")
    }

    @Test
    fun wrongCode_showsSnackbar() {
        performCodeInput("123333")
        TestDispatchers.IO.scheduler.advanceUntilIdle()
        errorSnackbarText
            .assertExists()
            .assertTextEquals(WrongCodeException().uiText.getValue(context))
    }

    @Test
    fun afterSMSWaitTimePasses_progressBarDisappears() {
        waitTimeProgressBar.assertExists()
        TestDispatchers.Main.scheduler.advanceUntilIdle()
        waitTimeProgressBar.assertDoesNotExist()
    }

    @Test
    fun correctCode_proceeds() {
        performCodeInput(FakeAuthenticator.VERIFICATION_CODE)
        TestDispatchers.IO.scheduler.advanceUntilIdle()

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.VERIFICATION_SCREEN)
            .assertDoesNotExist()

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.CreateAccount.SCREEN)
            .assertExists()
    }

    private fun performCodeInput(code: String) {
        code.forEach { digit ->
            composeRule
                .onUnmergedDescendantNodeWithText(
                    parentTag = MainTestTags.NUMBER_KEYBOARD,
                    descendantText = digit.toString()
                )
                .performClick()
        }
    }

    private fun performDelete(times: Int = 1) {
        repeat(times) {
            composeRule
                .onUnmergedDescendantNodeWithContentDescription(
                    parentTag = MainTestTags.NUMBER_KEYBOARD,
                    descendantContentDescription = R.string.tx_delete.getValue(context)
                )
                .performClick()
        }
    }

    private fun assertVerificationCodeIs(code: String) {
        val verificationCodeBox = composeRule.onUnmergedNodeWithTag(MainTestTags.Auth.VERIFICATION_CODE_BOX)

        for (i in code.indices) {
            verificationCodeBox
                .onChildAt(i)
                .assertTextEquals(code[i].toString())
        }

        for (i in code.length until 6) {
            verificationCodeBox
                .onChildAt(i)
                .assertTextEquals("")
        }
    }
}