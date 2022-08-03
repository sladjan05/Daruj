package net.jsoft.daruj.auth.presentation.screen

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.di.AuthenticatorModule
import net.jsoft.daruj.auth.presentation.AuthActivity
import net.jsoft.daruj.common.di.DispatcherModule
import net.jsoft.daruj.common.di.LocalSettingsRepositoryModule
import net.jsoft.daruj.common.di.UserRepositoryModule
import net.jsoft.daruj.common.exception.InvalidRequestException
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
class PhoneScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<AuthActivity>()

    private val context: Context
        get() = composeRule.activity

    private val countryDropdown: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.Auth.COUNTRY_DROPDOWN)

    private val countryDropdownText: SemanticsNodeInteraction
        get() = composeRule.onUnmergedDescendantNodeWithTag(
            parentTag = MainTestTags.Auth.COUNTRY_DROPDOWN,
            descendantTag = MainTestTags.Dropdown.TEXT
        )

    private val countryDropdownList: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(PopupTestTags.Dropdown.LIST)

    private val dialCodeTextBox: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.Auth.DIAL_CODE_TEXTBOX)

    private val phoneNumberTextBox: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.Auth.PHONE_NUMBER_TEXTBOX)

    private val nextButton: SemanticsNodeInteraction
        get() = composeRule.onUnmergedNodeWithTag(MainTestTags.Auth.NEXT_BUTTON)

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
    fun togglingCountryDropdown_changesVisibility() {
        countryDropdownList.assertDoesNotExist()
        countryDropdown.performClick()
        countryDropdownList.assertExists()
        countryDropdown.performClick()
        countryDropdownList.assertDoesNotExist()
    }

    @Test
    fun choosingCountry_updatesDialCode() {
        val country = Country.DZ

        countryDropdown.performClick()
        composeRule
            .onUnmergedDescendantNodeWithText(
                parentTag = PopupTestTags.Dropdown.LIST,
                descendantText = country.resId.getValue(context)
            )
            .performClick()
        countryDropdownList.assertDoesNotExist()
        dialCodeTextBox.assertTextContains(country.dialCode.removePrefix("+"))
    }

    @Test
    fun changingDialCode_updatesCountry() {
        val country = Country.BG

        dialCodeTextBox.performTextReplacement(country.dialCode.removePrefix("+"))
        countryDropdownText.assertTextEquals(country.resId.getValue(context))
    }

    @Test
    fun nonExistentDialCode_setsUnknownCountry() {
        dialCodeTextBox.performTextReplacement("1234")
        countryDropdownText.assertTextEquals(composeRule.activity.getString(R.string.tx_unknown))
    }

    @Test
    fun invalidPhoneNumber_showsSnackbar() {
        nextButton.performClick()

        TestDispatchers.IO.scheduler.advanceUntilIdle()

        errorSnackbarText
            .assertExists()
            .assertTextEquals(InvalidRequestException().uiText.getValue(composeRule.activity))
    }

    @Test
    fun validPhoneNumber_proceeds() {
        dialCodeTextBox.performTextReplacement("1")
        phoneNumberTextBox.performTextInput("6505551234")
        nextButton.performClick()

        TestDispatchers.IO.scheduler.advanceUntilIdle()

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.VERIFICATION_SCREEN)
            .assertExists()

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.PHONE_NUMBER_SCREEN)
            .assertDoesNotExist()
    }
}