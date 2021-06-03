package miq0717.dailyislamcodesession.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import miq0717.dailyislamcodesession.R
import miq0717.dailyislamcodesession.data.model.HadithBookDatum
import miq0717.dailyislamcodesession.databinding.ListItemHadithBookBinding

class HadithBooksAdapter(
    private val hadithBooks: ArrayList<HadithBookDatum>,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<HadithBooksAdapter.HadithBooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HadithBooksViewHolder(
        ListItemHadithBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount(): Int = hadithBooks.size

    override fun onBindViewHolder(holder: HadithBooksViewHolder, position: Int) =
        holder.bind(aHadithBook = hadithBooks[position])

    fun addData(hadithBooks: List<HadithBookDatum>) {
        this.hadithBooks.addAll(hadithBooks)
        notifyDataSetChanged()
    }

    inner class HadithBooksViewHolder(private val binding: ListItemHadithBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(aHadithBook: HadithBookDatum) {
            binding.tvHadithBookName.text = aHadithBook.hadithBookCollectionDataByLanguage[0].title
            binding.tvNumberOfHadiths.text = binding.root.context.getString(R.string.total_hadiths, aHadithBook.totalHadith.toString())
            binding.container.setOnClickListener {
                onClick.invoke()
            }
        }
    }
}