package net.jsoft.daruj.common.presentation.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme

// Must be public, for some reason...
class BottomSheetDialog(
    private val content: @Composable BoxScope.() -> Unit
) : BottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DarujTheme {
                    val shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp
                    )

                    Box(
                        modifier = Modifier
                            .clip(shape)
                            .background(MaterialTheme.colorScheme.background),
                        content = content
                    )
                }
            }
        }
    }
}

class BottomSheetDialogController(
    private val activity: AppCompatActivity
) {
    private val fragment by lazy { BottomSheetDialog(content) }

    private lateinit var content: @Composable BoxScope.() -> Unit

    fun init(content: @Composable BoxScope.() -> Unit) {
        this.content = content
    }

    fun show() = fragment.show(activity.supportFragmentManager, null)
    fun dismiss() = fragment.dismiss()
}

@Composable
fun BottomSheetDialog(
    controller: BottomSheetDialogController,
    content: @Composable BoxScope.() -> Unit
) {
    val context = LocalContext.current
    context as AppCompatActivity

    LaunchedEffect(Unit) { controller.init(content) }
}