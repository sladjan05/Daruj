package net.jsoft.daruj.main.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.*
import net.jsoft.daruj.common.utils.formatPostTimestamp
import net.jsoft.daruj.common.utils.thenIf
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.main.presentation.component.DonateButton
import net.jsoft.daruj.main.presentation.component.PostHeader
import net.jsoft.daruj.main.presentation.component.RobotoLight18
import net.jsoft.daruj.main.presentation.screen.viewmodel.post.DetailedPostViewModel

@Composable
fun DetailedPostScreen(
    viewModel: DetailedPostViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val post = viewModel.post
    val mutable = post?.mutable
    val immutable = post?.immutable
    val data = post?.data

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            PostHeader(
                user = immutable?.user,
                timestamp = if (immutable?.timestamp != null) {
                    formatPostTimestamp(immutable.timestamp).value
                } else "",
                modifier = Modifier.fillMaxWidth()
            )

            var isLoaded by rememberSaveable {
                mutableStateOf(false)
            }

            AsyncImage(
                model = data?.pictureUri,
                contentDescription = mutable?.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .thenIf(
                        statement = !isLoaded,
                        ifModifier = Modifier.padding(120.dp),
                    ),
                contentScale = if (isLoaded) {
                    ContentScale.Crop
                } else {
                    ContentScale.Fit
                },
                error = painterResource(R.drawable.ic_full_logo),
                placeholder = painterResource(R.drawable.ic_full_logo),
                onSuccess = {
                    isLoaded = true
                },
                colorFilter = if (isLoaded) {
                    null
                } else {
                    ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface)
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.huge * 2,
                        start = MaterialTheme.spacing.large,
                        end = MaterialTheme.spacing.large
                    ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )
                }

                if (mutable != null && immutable != null) {
                    Row {
                        Text(
                            text = "${mutable.name} ${mutable.surname} ",
                            modifier = Modifier.alignByBaseline(),
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = R.string.tx_needs_blood.value,
                            modifier = Modifier.alignByBaseline(),
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = RobotoLight18
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.mShapes.medium
                            )
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append(R.string.tx_donors_required_1.value + ":  ")
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(mutable.donorsRequired.toString())
                                }
                            },
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = buildAnnotatedString {
                                append(R.string.tx_blood_type.value + ":  ")
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(mutable.blood.toString())
                                }
                            },
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = MaterialTheme.typography.bodySmall
                        )

                        LinearProgressIndicator(
                            progress = immutable.donorCount.toFloat() / mutable.donorsRequired,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.mShapes.full),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.onSurfaceLight
                        )
                    }

                    Text(
                        text = mutable.description,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackgroundDim,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        DonateButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.large,
                    end = MaterialTheme.spacing.large,
                    bottom = MaterialTheme.spacing.huge
                )
                .align(Alignment.BottomCenter)
        )
    }
}