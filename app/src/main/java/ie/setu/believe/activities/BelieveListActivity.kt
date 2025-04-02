package ie.setu.believe.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ie.setu.believe.R
import ie.setu.believe.adapters.BelieveAdapter
import ie.setu.believe.adapters.BelieveListener
import ie.setu.believe.databinding.ActivityBelieveListBinding
import ie.setu.believe.main.MainApp
import ie.setu.believe.models.BelieveModel

class BelieveListActivity : AppCompatActivity(), BelieveListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBelieveListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelieveListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topAppBar.title = title
        setSupportActionBar(binding.topAppBar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BelieveAdapter(app.believe.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, BelieveActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBelieveClick(placemark: BelieveModel) {
        val launcherIntent = Intent(this, BelieveActivity::class.java)
        launcherIntent.putExtra("believe_edit", believe)
        getResult.launch(launcherIntent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.believe.findAll().size)
            }
            if (it.resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(binding.root, "Believe Add Cancelled", Snackbar.LENGTH_LONG).show()
            }
        }

}