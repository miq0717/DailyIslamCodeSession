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
import miq0717.dailyislamcodesession.databinding.FragmentChaptersOfAHadithBookBinding
import miq0717.dailyislamcodesession.ui.adapter.ChaptersOfAHadithBookAdapter
import miq0717.dailyislamcodesession.ui.adapter.HadithBooksAdapter
import miq0717.dailyislamcodesession.ui.viewmodel.ChaptersOfAHadithBookViewModel
import miq0717.dailyislamcodesession.util.AppConstants
import miq0717.dailyislamcodesession.util.Status
import miq0717.dailyislamcodesession.util.ToastUtil.showShortToast
import timber.log.Timber

@AndroidEntryPoint
class ChaptersOfAHadithBookFragment : Fragment() {

    private lateinit var binding: FragmentChaptersOfAHadithBookBinding
    private lateinit var navController: NavController
    private val chaptersOfAHadithBookViewModel by viewModels<ChaptersOfAHadithBookViewModel>()
    private lateinit var chaptersOfAHadithBookAdapter: ChaptersOfAHadithBookAdapter

    private var hasInitializedRootView = false
    private lateinit var collectionName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionName = it.getString(AppConstants.COLLECTION_NAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized)
            binding = FragmentChaptersOfAHadithBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            navController = Navigation.findNavController(view)
            chaptersOfAHadithBookAdapter = ChaptersOfAHadithBookAdapter(arrayListOf()) {
                if (navController.currentDestination?.id == R.id.chaptersOfAHadithBookFragment) {
                    navController.navigate(R.id.action_chaptersOfAHadithBookFragment_to_hadithsOfAChapterFragment)
                }
            }

            initUI()
            setupToolbar()
            setupObserver()
            getHadithChapters()
        }
    }

    private fun initUI() {
        binding.rvHadithChapters.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = chaptersOfAHadithBookAdapter
        }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)
        toolbar.myCustomTitle.text = context?.getString(R.string.hadith_books)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupObserver() {
        chaptersOfAHadithBookViewModel.chaptersOfAHadithBook.observe(viewLifecycleOwner, { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvHadithChapters.visibility = View.GONE
                }

                Status.SUCCESS -> {
                    Timber.i("hadithBooks: ${it.data}")
                    binding.progressBar.visibility = View.GONE
                    it.data?.let {
                        chaptersOfAHadithBookAdapter.addData(hadithBooks = it.chaptersDatum)
                    }
                    binding.rvHadithChapters.visibility = View.VISIBLE
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

    private fun getHadithChapters() {
        chaptersOfAHadithBookViewModel.getChaptersOfAHadithBook(
            collectionName = collectionName,
            limit = 50,
            page = 1
        )
    }
}