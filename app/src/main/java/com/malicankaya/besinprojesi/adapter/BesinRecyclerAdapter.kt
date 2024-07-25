package com.malicankaya.besinprojesi.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.malicankaya.besinprojesi.databinding.RecycleRowBinding
import com.malicankaya.besinprojesi.model.Besin
import com.malicankaya.besinprojesi.view.BesinListeFragmentDirections

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinHolder>() {
    class BesinHolder(val binding: RecycleRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinHolder {
        return BesinHolder(RecycleRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiniGuncelle(newList: List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BesinHolder, position: Int) {
        holder.binding.besinAdiTextView.text = besinListesi[position].besinIsim
        holder.binding.besinKaloriTextView.text = besinListesi[position].besinKalori
        holder.itemView.setOnClickListener {
            val action = BesinListeFragmentDirections.actionBesinListeFragmentToBesinDetayFragment(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }
}