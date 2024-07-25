package com.malicankaya.besinprojesi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.malicankaya.besinprojesi.adapter.BesinRecyclerAdapter
import com.malicankaya.besinprojesi.databinding.FragmentBesinListeBinding
import com.malicankaya.besinprojesi.service.BesinAPI
import com.malicankaya.besinprojesi.viewmodel.BesinListeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BesinListeFragment : Fragment() {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/9b55086ad4096dbee7bc2b506d4787fbb805b0ca/besinler.json

    private var _binding : FragmentBesinListeBinding? = null
    private val binding get() = _binding!!

    private var adapter = BesinRecyclerAdapter(arrayListOf())

    private lateinit var viewModel: BesinListeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinListeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BesinListeViewModel::class.java]
        viewModel.refreshData()

        binding.besinRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.besinRecyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.besinRecyclerView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.hataTextView.visibility = View.GONE
            viewModel.refrestDataFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.besinListesi.observe(viewLifecycleOwner){
            //adapter
            adapter.besinListesiniGuncelle(it)
            binding.besinRecyclerView.visibility = View.VISIBLE
        }
        viewModel.yukleniyorMu.observe(viewLifecycleOwner){
            if(it){
                binding.progressBar.visibility = View.VISIBLE
                binding.hataTextView.visibility = View.GONE
                binding.besinRecyclerView.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.hataMesaji.observe(viewLifecycleOwner){
            if (it){
                binding.hataTextView.visibility = View.VISIBLE
                binding.besinRecyclerView.visibility = View.GONE
            }else{
                binding.hataTextView.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}