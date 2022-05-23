package com.althaafridha.kisahnabiapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.althaafridha.kisahnabiapp.data.KisahResponse
import com.althaafridha.kisahnabiapp.databinding.ActivityMainBinding
import com.althaafridha.kisahnabiapp.ui.detail.DetailActivity
import com.althaafridha.kisahnabiapp.utils.OnItemClickCallback

class MainActivity : AppCompatActivity() {

	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding as ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		 val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

		viewModel.getKisahNabi()
		viewModel.isLoading.observe(this) { showLoading(it) }
		viewModel.isError.observe(this) { showError(it) }
		viewModel.kisahResponse.observe(this) { showData(it) }
	}

	private fun showData(data: List<KisahResponse>?) {
		binding.recyclerMain.apply {
			val mAdapter = KisahAdapter()
			mAdapter.setData(data)
			layoutManager = GridLayoutManager(this@MainActivity, 2)
			adapter = mAdapter
			mAdapter.setOnItemClickCallback(object : OnItemClickCallback {
				override fun onItemClicked(item: KisahResponse) {
					startActivity(
						Intent(this@MainActivity, DetailActivity::class.java)
							.putExtra(DetailActivity.EXTRA_DATA, item)
					)
				}
			})
		}
	}

	private fun showError(isError: Throwable?) {
		Log.e("MainActivity", "Error get data $isError", )
	}

	private fun showLoading(isLoading: Boolean?) {
		if (isLoading == true) {
			binding.progressMain.visibility = View.VISIBLE
		} else {
			binding.progressMain.visibility = View.INVISIBLE
		}
	}
}