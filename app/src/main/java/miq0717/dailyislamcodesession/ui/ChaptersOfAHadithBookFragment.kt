package miq0717.dailyislamcodesession.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import miq0717.dailyislamcodesession.R
import miq0717.dailyislamcodesession.databinding.FragmentChaptersOfAHadithBookBinding
import miq0717.dailyislamcodesession.ui.viewmodel.MainViewModel

@AndroidEntryPoint
class ChaptersOfAHadithBookFragment : Fragment() {

    private lateinit var binding: FragmentChaptersOfAHadithBookBinding
    private lateinit var navController: NavController
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChaptersOfAHadithBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.tvNext.setOnClickListener {
            if (navController.currentDestination?.id == R.id.chaptersOfAHadithBookFragment) {
                navController.navigate(R.id.action_chaptersOfAHadithBookFragment_to_hadithsOfAChapterFragment)
            }
        }
    }
}