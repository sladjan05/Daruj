package net.jsoft.daruj.welcome.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.AuthActivity
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.util.switchActivity
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.introduction.presentation.IntroductionActivity

@Composable
fun WelcomeScreen() {
    val context = LocalContext.current

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
                .offset(y = (-24).dp - maxHeight / 4)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(R.string.tx_welcome_to.value)
                    }

                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(R.string.app_name.value)
                    }

                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append("!")
                    }
                },
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = R.string.tx_join_the_community_and_save_lives.value,
                modifier = Modifier.widthIn(max = 210.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-32).dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = R.string.tx_continue.value,
                modifier = Modifier.widthIn(300.dp),
                onClick = {
                    context.switchActivity<IntroductionActivity>()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = buildAnnotatedString {
                    append(R.string.tx_terms_of_use_acceptance.value)

                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(R.string.tx_terms_of_use.value)
                    }

                    append(".")
                },
                color = MaterialTheme.colorScheme.onBackgroundDim,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = buildAnnotatedString {
                    append(R.string.tx_read_privacy_policy.value)

                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(R.string.tx_privacy_policy.value)
                    }

                    append(".")
                },
                color = MaterialTheme.colorScheme.onBackgroundDim,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}