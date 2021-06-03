package miq0717.dailyislamcodesession.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_with_custom_back.view.*
import miq0717.dailyislamcodesession.R
import miq0717.dailyislamcodesession.databinding.FragmentHadithsOfAChapterBinding
import miq0717.dailyislamcodesession.ui.adapter.HadithsOfAChapterAdapter
import miq0717.dailyislamcodesession.ui.viewmodel.HadithsOfAChapterViewModel
import miq0717.dailyislamcodesession.util.AppConstants
import miq0717.dailyislamcodesession.util.Status
import miq0717.dailyislamcodesession.util.ToastUtil.showShortToast
import timber.log.Timber
import kotlin.properties.Delegates

@AndroidEntryPoint
class HadithsOfAChapterFragment : Fragment() {
    private lateinit var binding: FragmentHadithsOfAChapterBinding
    private lateinit var navController: NavController
    private val hadithsOfAChapterViewModel by viewModels<HadithsOfAChapterViewModel>()
    private lateinit var hadithsOfAChapterAdapter: HadithsOfAChapterAdapter

    private var hasInitializedRootView = false
    private lateinit var collectionName: String
    private var bookNumber by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionName = it.getString(AppConstants.COLLECTION_NAME).toString()
            bookNumber = it.getInt(AppConstants.BOOK_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized)
            binding = FragmentHadithsOfAChapterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            navController = Navigation.findNavController(view)
            hadithsOfAChapterAdapter = HadithsOfAChapterAdapter(arrayListOf()) { hadithNumber ->
                Timber.i("hadithNumber: $hadithNumber")
                if (navController.currentDestination?.id == R.id.hadithsOfAChapterFragment) {
                    val bundle = Bundle()
                    bundle.putString(AppConstants.COLLECTION_NAME, collectionName)
                    bundle.putInt(AppConstants.HADITH_NUMBER, hadithNumber)
                    navController.navigate(
                        R.id.action_hadithsOfAChapterFragment_to_hadithDetailsWithTranslationFragment,
                        bundle
                    )
                }
            }

            initUI()
            setupToolbar()
            setupObserver()
            getHadiths()
        }
    }

    private fun initUI() {
        binding.rvHadiths.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = hadithsOfAChapterAdapter
        }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)
        toolbar.myCustomTitle.text = context?.getString(R.string.hadiths)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupObserver() {
        hadithsOfAChapterViewModel.hadithsOfAChapter.observe(viewLifecycleOwner, { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvHadiths.visibility = View.GONE
                }

                Status.SUCCESS -> {
                    Timber.i("hadithBooks: ${it.data}")
                    binding.progressBar.visibility = View.GONE
                    it.data?.let {
                        hadithsOfAChapterAdapter.addData(hadiths = it.hadithDatum)
                    }
                    binding.rvHadiths.visibility = View.VISIBLE
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

    private fun getHadiths() {
        hadithsOfAChapterViewModel.getHadithsOfAChapter(
            collectionName = collectionName,
            bookNumber = bookNumber,
            limit = 50,
            page = 1
        )
    }
}