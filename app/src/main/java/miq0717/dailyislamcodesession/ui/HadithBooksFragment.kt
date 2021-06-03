package miq0717.dailyislamcodesession.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import miq0717.dailyislamcodesession.databinding.FragmentHadithBooksBinding
import miq0717.dailyislamcodesession.ui.adapter.HadithBooksAdapter
import miq0717.dailyislamcodesession.ui.viewmodel.HadithBooksViewModel
import miq0717.dailyislamcodesession.util.AppConstants
import miq0717.dailyislamcodesession.util.Status
import miq0717.dailyislamcodesession.util.ToastUtil.showShortToast
import timber.log.Timber

@AndroidEntryPoint
class HadithBooksFragment : Fragment() {

    private lateinit var binding: FragmentHadithBooksBinding
    private lateinit var navController: NavController
    private val hadithBooksViewModel by viewModels<HadithBooksViewModel>()
    private lateinit var hadithBooksAdapter: HadithBooksAdapter

    private var hasInitializedRootView = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized)
            binding = FragmentHadithBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            navController = Navigation.findNavController(view)
            hadithBooksAdapter = HadithBooksAdapter(arrayListOf()) { collectionName ->
                if (navController.currentDestination?.id == R.id.hadithBooksFragment) {
                    val bundle = Bundle()
                    bundle.putString(AppConstants.COLLECTION_NAME, collectionName)
                    navController.navigate(
                        R.id.action_hadithBooksFragment_To_hadithChaptersOfABookFragment,
                        bundle
                    )
                }
            }

            initUI()
            setupToolbar()
            setupObserver()
            getHadithBooks()
        }
    }

    private fun initUI() {
        binding.rvHadithBooks.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = hadithBooksAdapter
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
        hadithBooksViewModel.hadithBooks.observe(viewLifecycleOwner, { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = VISIBLE
                    binding.rvHadithBooks.visibility = GONE
                }

                Status.SUCCESS -> {
                    Timber.i("hadithBooks: ${it.data}")
                    binding.progressBar.visibility = GONE
                    it.data?.let {
                        hadithBooksAdapter.addData(hadithBooks = it.hadithBookDatum)
                    }
                    binding.rvHadithBooks.visibility = VISIBLE
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = GONE
                    it.message?.let { it1 ->
                        context?.showShortToast(message = it1)
                    }
                }
            }
        })
    }

    private fun getHadithBooks() {
        hadithBooksViewModel.getHadithBooks()
    }
}