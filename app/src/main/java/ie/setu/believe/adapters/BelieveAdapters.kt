package ie.setu.believe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.believe.databinding.CardBelieveBinding
import ie.setu.believe.models.BelieveModel

interface BelieveListener {
    fun onBelieveClick(believe: BelieveModel)
}

class BelieveAdapter constructor(private var believe: List<BelieveModel>,
                                   private val listener: BelieveListener) :
    RecyclerView.Adapter<BelieveAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBelieveBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val believe = believe[holder.adapterPosition]
        holder.bind(believe, listener)
    }

    override fun getItemCount(): Int = believe.size

    class MainHolder(private val binding : CardBelieveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(believe: BelieveModel, listener: BelieveListener) {
            binding.believeTitle.text = believe.title
            binding.believeDescription.text = believe.description
            Picasso.get().load(believe.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onBelieveClick(believe)}
        }
    }
}
