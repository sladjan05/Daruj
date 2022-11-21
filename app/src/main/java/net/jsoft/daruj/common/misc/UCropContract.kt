package net.jsoft.daruj.common.misc

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.yalantis.ucrop.UCrop
import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.createUniqueCacheFile

sealed class UCropContract private constructor(
    private val x: Float,
    private val y: Float
) : ActivityResultContract<Uri, Uri?>() {

    val ratio = x / y

    override fun createIntent(context: Context, input: Uri): Intent {
        val outputFile = context.createUniqueCacheFile("png")

        val uCropOptions = UCrop.Options().apply {
            withAspectRatio(x, y)

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

    class ProfilePictureRatio : UCropContract(1f, 1f)
    class ReceiptRatio : UCropContract(2f, 1.4f)
}