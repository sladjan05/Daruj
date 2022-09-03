package net.jsoft.daruj.common.misc

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.yalantis.ucrop.UCrop
import net.jsoft.daruj.R
import java.io.File
import java.util.*

class UCropActivityResultContract : ActivityResultContract<Uri, Uri?>() {
    override fun createIntent(context: Context, input: Uri): Intent {
        val output = "${UUID.randomUUID()}.png"
        val outputFile = File(context.cacheDir, output)

        val uCropOptions = UCrop.Options().apply {
            withAspectRatio(1f, 1f)

            setLogoColor(context.getColor(R.color.blue_13))
            setToolbarColor(context.getColor(R.color.white))
            setRootViewBackgroundColor(context.getColor(R.color.white))

            setToolbarTitle(context.getString(R.string.tx_edit_picture))
        }

        val uCrop = UCrop.of(
            input,
            Uri.fromFile(outputFile)
        ).withOptions(uCropOptions)

        return uCrop.getIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (intent == null) return null
        return UCrop.getOutput(intent)
    }
}