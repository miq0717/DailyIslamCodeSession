package miq0717.dailyislamcodesession.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_with_custom_back.view.*
import miq0717.dailyislamcodesession.R
import miq0717.dailyislamcodesession.data.model.HadithInfoDatum
import miq0717.dailyislamcodesession.databinding.FragmentHadithDetailsWithTranslationBinding
import miq0717.dailyislamcodesession.ui.viewmodel.DetailsOfAHadithViewModel
import miq0717.dailyislamcodesession.util.AppConstants
import miq0717.dailyislamcodesession.util.Status
import miq0717.dailyislamcodesession.util.ToastUtil.showShortToast
import timber.log.Timber
import kotlin.properties.Delegates

@AndroidEntryPoint
class HadithDetailsWithTranslationFragment : Fragment() {

    private lateinit var binding: FragmentHadithDetailsWithTranslationBinding
    private lateinit var navController: NavController
    private val detailsOfAHadithViewModel by viewModels<DetailsOfAHadithViewModel>()

    private var hasInitializedRootView = false
    private lateinit var collectionName: String
    private var hadithNumber by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionName = it.getString(AppConstants.COLLECTION_NAME).toString()
            hadithNumber = it.getInt(AppConstants.HADITH_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized)
            binding =
                FragmentHadithDetailsWithTranslationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            navController = Navigation.findNavController(view)

            setupToolbar()
            setupObserver()
            getDetailOfAHadith()
        }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupObserver() {
        detailsOfAHadithViewModel.detailsOfAHadith.observe(viewLifecycleOwner, { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvHadithBodyArabic.visibility = View.GONE
                    binding.tvHadithBodyEnglish.visibility = View.GONE
                }

                Status.SUCCESS -> {
                    Timber.i("hadithBooks: ${it.data}")
                    binding.progressBar.visibility = View.GONE
                    it.data?.let {
                        setData(it)
                    }
                    binding.tvHadithBodyArabic.visibility = View.VISIBLE
                    binding.tvHadithBodyEnglish.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    it.message?.let { it1 ->
                        context?.showShortToast(message = it1)
                    }
                }
            }
        })
    }

    private fun setData(hadithInfo: HadithInfoDatum) {
        try {
            binding.tvHadithBodyArabic.text =
                Html.fromHtml(hadithInfo.hadithDetailDataByLanguage[1].hadithBody)
        } catch (e: Exception) {

        }

        try {
            binding.tvHadithBodyEnglish.text =
                Html.fromHtml(hadithInfo.hadithDetailDataByLanguage[0].hadithBody)
        } catch (e: Exception) {

        }

        val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)
        toolbar.myCustomTitle.text = hadithInfo.hadithDetailDataByLanguage[0].chapterTitle
    }

    private fun getDetailOfAHadith() {
        detailsOfAHadithViewModel.getDetailsOfAHadith(
            collectionName, hadithNumber
        )
    }
}