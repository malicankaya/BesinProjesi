package com.malicankaya.besinprojesi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.malicankaya.besinprojesi.databinding.FragmentBesinListeBinding
import com.malicankaya.besinprojesi.service.BesinAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BesinListeFragment : Fragment() {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/9b55086ad4096dbee7bc2b506d4787fbb805b0ca/besinler.json

    private var _binding : FragmentBesinListeBinding? = null
    private val binding get() = _binding!!

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
        binding.hataTextView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.INVISIBLE


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}