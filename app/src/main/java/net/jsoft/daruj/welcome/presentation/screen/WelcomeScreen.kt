package net.jsoft.daruj.welcome.presentation.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.MainTestTags
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.ui.theme.bodySmaller
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.utils.switchActivity
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.introduction.presentation.IntroductionActivity

@Composable
fun WelcomeScreen() {
    val context = LocalContext.current
    context as Activity

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_full_logo),
            contentDescription = null,
            modifier = Modifier
                .width(157.dp)
                .height(68.dp)
                .align(Alignment.Center)
                .offset(y = -maxHeight / 3)
        )

        Image(
            painter = painterResource(R.drawable.ic_welcome_phones),
            contentDescription = null,
            modifier = Modifier
                .size(270.dp)
                .align(Alignment.Center)
        )

        Image(
            painter = painterResource(R.drawable.ic_welcome_upper_part),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopStart)
        )

        Image(
            painter = painterResource(R.drawable.ic_welcome_lower_part),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = MaterialTheme.spacing.huge),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = R.string.tx_continue.value,
                onClick = {
                    context.switchActivity<IntroductionActivity>()
                },
                modifier = Modifier
                    .testTag(MainTestTags.CreateAccount.CREATE_ACCOUNT_BUTTON)
                    .width(300.dp)
            )

            Text(
                text = buildAnnotatedString {
                    append(R.string.tx_terms_of_use_acceptance_1.value + " ")

                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(R.string.tx_terms_of_use_acceptance_2.value)
                    }

                    append(".")
                    append("\n")

                    append(R.string.tx_read_privacy_policy_1.value + " ")

                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(R.string.tx_read_privacy_policy_2.value)
                    }

                    append(".")
                },
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmaller,
                textAlign = TextAlign.Center
            )
        }
    }
}