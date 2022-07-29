package net.jsoft.daruj.auth.presentation.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.di.AuthenticatorModule
import net.jsoft.daruj.auth.presentation.AuthActivity
import net.jsoft.daruj.common.di.DispatcherModule
import net.jsoft.daruj.common.di.LocalSettingsRepositoryModule
import net.jsoft.daruj.common.exception.InvalidRequestException
import net.jsoft.daruj.common.util.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(
    DispatcherModule::class,
    AuthenticatorModule::class,
    LocalSettingsRepositoryModule::class
)
class AuthScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<AuthActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setScreenContent {
            AuthScreen()
        }
    }

    // ======================================== PHONE NUMBER SCREEN ========================================
    @Test
    fun togglingCountryDropdown_changesVisibility() {
        val dropdown = composeRule.onUnmergedNodeWithTag(MainTestTags.Auth.COUNTRY_DROPDOWN)
        val dropdownList = composeRule.onUnmergedNodeWithTag(PopupTestTags.Dropdown.LIST)

        dropdownList.assertDoesNotExist()
        dropdown.performClick()
        dropdownList.assertExists()
        dropdown.performClick()
        dropdownList.assertDoesNotExist()
    }

    @Test
    fun choosingCountry_updatesDialCode() {
        val country = Country.DZ

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.COUNTRY_DROPDOWN)
            .performClick()

        composeRule
            .onUnmergedDescendantNodeWithText(
                parentTag = PopupTestTags.Dropdown.LIST,
                descendantText = composeRule.activity.getString(country.resId)
            )
            .performClick()

        composeRule
            .onUnmergedNodeWithTag(PopupTestTags.Dropdown.LIST)
            .assertDoesNotExist()

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.DIAL_CODE_TEXTBOX)
            .assertTextContains(country.dialCode.removePrefix("+"))
    }

    @Test
    fun changingDialCode_updatesCountry() {
        val country = Country.BG

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.DIAL_CODE_TEXTBOX)
            .performTextReplacement(country.dialCode.removePrefix("+"))

        composeRule
            .onUnmergedDescendantNodeWithTag(
                parentTag = MainTestTags.Auth.COUNTRY_DROPDOWN,
                descendantTag = MainTestTags.Dropdown.TEXT
            )
            .assertTextEquals(composeRule.activity.getString(country.resId))
    }

    @Test
    fun nonExistentDialCode_setsUnknownCountry() {
        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.DIAL_CODE_TEXTBOX)
            .performTextReplacement("1234")

        composeRule
            .onUnmergedDescendantNodeWithTag(
                parentTag = MainTestTags.Auth.COUNTRY_DROPDOWN,
                descendantTag = MainTestTags.Dropdown.TEXT
            )
            .assertTextEquals(composeRule.activity.getString(R.string.tx_unknown))
    }

    @Test
    fun invalidPhoneNumber_showsSnackbar() {
        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.NEXT_BUTTON)
            .performClick()

        composeRule
            .onUnmergedDescendantNodeWithTag(
                parentTag = MainTestTags.Snackbar.ERROR,
                descendantTag = MainTestTags.Snackbar.TEXT
            )
            .assertExists()
            .assertTextEquals(InvalidRequestException().uiText.getValue(composeRule.activity))
    }

    @Test
    fun validPhoneNumber_proceeds() {
        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.DIAL_CODE_TEXTBOX)
            .performTextReplacement("1")

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.PHONE_NUMBER_TEXTBOX)
            .performTextInput("6505551234")

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.NEXT_BUTTON)
            .performClick()

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.VERIFICATION_SCREEN)
            .assertExists()

        composeRule
            .onUnmergedNodeWithTag(MainTestTags.Auth.PHONE_NUMBER_SCREEN)
            .assertDoesNotExist()
    }

    // ======================================== VERIFICATION CODE SCREEN ========================================
    // TODO
}