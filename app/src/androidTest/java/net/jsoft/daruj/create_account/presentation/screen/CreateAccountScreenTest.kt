package net.jsoft.daruj.create_account.presentation.screen

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.jsoft.daruj.auth.di.AuthenticatorModule
import net.jsoft.daruj.common.di.DispatcherModule
import net.jsoft.daruj.common.di.LocalSettingsRepositoryModule
import net.jsoft.daruj.common.di.UserRepositoryModule
import net.jsoft.daruj.common.exception.BlankNameException
import net.jsoft.daruj.common.exception.BlankSurameException
import net.jsoft.daruj.common.util.MainTestTags
import net.jsoft.daruj.common.util.TestDispatchers
import net.jsoft.daruj.common.util.onUnmergedDescendantNodeWithTag
import net.jsoft.daruj.common.util.onUnmergedNodeWithTag
import net.jsoft.daruj.create_account.presentation.CreateAccountActivity
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
class CreateAccountScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<CreateAccountActivity>()

    private val context: Context
        get() = composeRule.activity

    private val nameTextBox: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.CreateAccount.NAME_TEXTBOX)

    private val surnameTextBox: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.CreateAccount.SURNAME_TEXTBOX)

    private val createAccountButton: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.CreateAccount.CREATE_ACCOUNT_BUTTON)

    private val errorSnackbarText: SemanticsNodeInteraction
        get() = composeRule.onUnmergedDescendantNodeWithTag(
            parentTag = MainTestTags.Snackbar.ERROR,
            descendantTag = MainTestTags.Snackbar.TEXT
        )

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun blankName_showsSnackbar() {
        createAccountButton
            .performScrollTo()
            .performClick()

        TestDispatchers.IO.scheduler.advanceUntilIdle()

        errorSnackbarText
            .assertExists()
            .assertTextEquals(BlankNameException().uiText.getValue(context))
    }

    @Test
    fun blankSurname_showsSnackbar() {
        nameTextBox.performTextInput("John")

        createAccountButton
            .performScrollTo()
            .performClick()

        TestDispatchers.IO.scheduler.advanceUntilIdle()

        errorSnackbarText
            .assertExists()
            .assertTextEquals(BlankSurameException().uiText.getValue(context))
    }

    @Test
    fun validNameAndSurname_proceeds() {
        nameTextBox.performTextInput("John")

        surnameTextBox
            .performScrollTo()
            .performTextInput("Doe")

        createAccountButton
            .performScrollTo()
            .performClick()

        TestDispatchers.IO.scheduler.advanceUntilIdle()
    }
}