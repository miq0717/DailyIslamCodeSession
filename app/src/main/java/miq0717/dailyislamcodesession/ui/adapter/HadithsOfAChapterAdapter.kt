package miq0717.dailyislamcodesession.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import miq0717.dailyislamcodesession.R
import miq0717.dailyislamcodesession.data.model.HadithInfoDatum
import miq0717.dailyislamcodesession.databinding.ListItemHadithBinding

class HadithsOfAChapterAdapter(
    private val hadiths: ArrayList<HadithInfoDatum>,
    private val onClick: (hadithNumber: Int) -> Unit
) : RecyclerView.Adapter<HadithsOfAChapterAdapter.HadithBooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HadithBooksViewHolder(
        ListItemHadithBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun getItemCount(): Int = hadiths.size

    override fun onBindViewHolder(holder: HadithBooksViewHolder, position: Int) =
        holder.bind(aHadith = hadiths[position])

    fun addData(hadiths: List<HadithInfoDatum>) {
        this.hadiths.addAll(hadiths)
        notifyDataSetChanged()
    }

    inner class HadithBooksViewHolder(private val binding: ListItemHadithBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(aHadith: HadithInfoDatum) {
            binding.tvHadithNo.text = binding.root.context.getString(
                R.string.hadith_number,
                aHadith.hadithNumber
            )
            try {
                binding.tvHadithGrade.text = binding.root.context.getString(
                    R.string.hadith_grade,
                    aHadith.hadithDetailDataByLanguage[0].hadithGradeData[0].grade
                )
            } catch (e: Exception) {

            }

            binding.container.setOnClickListener {
                try {
                    onClick.invoke(aHadith.hadithNumber.toInt())
                } catch (e: Exception) {
                    onClick.invoke(aHadith.hadithNumber.substring(0, aHadith.hadithNumber.indexOf(",")).toInt())
                }
            }
        }
    }
}