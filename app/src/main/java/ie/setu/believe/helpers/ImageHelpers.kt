package ie.setu.believe.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.setu.believe.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_believe_image.toString())
    intentLauncher.launch(chooseFile)
}