package miq0717.dailyislamcodesession.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import miq0717.dailyislamcodesession.R
import miq0717.dailyislamcodesession.data.model.ChapterOfAHadithBookDatum
import miq0717.dailyislamcodesession.databinding.ListItemHadithChapterOfABookBinding

class ChaptersOfAHadithBookAdapter(
    private val hadithChapters: ArrayList<ChapterOfAHadithBookDatum>,
    private val onClick: (bookNumber: Int) -> Unit
) : RecyclerView.Adapter<ChaptersOfAHadithBookAdapter.HadithBooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HadithBooksViewHolder(
        ListItemHadithChapterOfABookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun getItemCount(): Int = hadithChapters.size

    override fun onBindViewHolder(holder: HadithBooksViewHolder, position: Int) =
        holder.bind(aHadithChapter = hadithChapters[position])

    fun addData(hadithChapters: List<ChapterOfAHadithBookDatum>) {
        this.hadithChapters.addAll(hadithChapters)
        notifyDataSetChanged()
    }

    inner class HadithBooksViewHolder(private val binding: ListItemHadithChapterOfABookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(aHadithChapter: ChapterOfAHadithBookDatum) {
            binding.tvHadithBookName.text = aHadithChapter.chapterInfoByLanguage[0].name
            binding.tvNumberOfHadiths.text = binding.root.context.getString(
                R.string.total_hadiths_with_details,
                aHadithChapter.hadithStartNumber.toString(),
                aHadithChapter.hadithEndNumber.toString(),
                aHadithChapter.numberOfHadith.toString()
            )
            binding.container.setOnClickListener {
                try {
                    onClick.invoke(aHadithChapter.bookNumber.toInt())
                } catch (e: Exception) {
                    onClick.invoke(aHadithChapter.bookNumber.substring(0, aHadithChapter.bookNumber.indexOf(",")).toInt())
                }
            }
        }
    }
}