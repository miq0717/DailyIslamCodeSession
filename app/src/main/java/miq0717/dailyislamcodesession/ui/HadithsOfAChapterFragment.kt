package miq0717.dailyislamcodesession.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import miq0717.dailyislamcodesession.R
import miq0717.dailyislamcodesession.databinding.FragmentHadithsOfAChapterBinding

class HadithsOfAChapterFragment : Fragment() {
    private lateinit var binding: FragmentHadithsOfAChapterBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHadithsOfAChapterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.tvNext.setOnClickListener {
            if (navController.currentDestination?.id == R.id.hadithsOfAChapterFragment) {
                navController.navigate(R.id.action_hadithsOfAChapterFragment_to_hadithDetailsWithTranslationFragment)
            }
        }
    }
}