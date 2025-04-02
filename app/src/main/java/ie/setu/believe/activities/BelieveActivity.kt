package ie.setu.believe.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.believe.R
import ie.setu.believe.databinding.ActivityBelieveBinding
import ie.setu.believe.helpers.showImagePicker
import ie.setu.believe.main.MainApp
import ie.setu.believe.models.Location
import ie.setu.believe.models.BelieveModel
import timber.log.Timber.i

class BelieveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBelieveBinding
    var believe = BelieveModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBelieveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.title = title
        setSupportActionBar(binding.topAppBar)

        app = application as MainApp
        i(getString(R.string.believe_activity_started))

        var edit = false //tracks if we arrived here via an existing placemark

        if (intent.hasExtra("believe_edit")) {
            edit = true
            binding.btnAdd.setText(R.string.save_believe)
            believe = intent.extras?.getParcelable("believe_edit")!!
            binding.believeTitle.setText(believe.title)
            binding.believeDescription.setText(believe.description)
            Picasso.get()
                .load(believe.image)
                .resize(800, 600)
                .into(binding.placemarkImage)
            // When editing, if an image is selected, update button text
            if (believe.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_believe_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            believe.title = binding.believeTitle.text.toString()
            believe.description = binding.believeDescription.text.toString()
            if (believe.title.isNotEmpty()) {
                if (edit) {
                    app.believe.update(believe.copy())
                }
                else {
                    app.believe.create(believe.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it, getString(R.string.please_enter_a_title), Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()

        binding.placemarkLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerMapCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_believe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            believe.image = result.data!!.data!!
                            Picasso.get()
                                .load(believe.image)
                                .resize(800, 600)
                                .into(binding.placemarkImage)
                            binding.chooseImage.setText(R.string.change_believe_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}
