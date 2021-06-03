package miq0717.dailyislamcodesession.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import miq0717.dailyislamcodesession.R
import miq0717.dailyislamcodesession.databinding.FragmentHadithBooksBinding

class HadithBooksFragment : Fragment() {

    private lateinit var binding: FragmentHadithBooksBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHadithBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.tvNext.setOnClickListener {
            if (navController.currentDestination?.id == R.id.hadithBooksFragment) {
                navController.navigate(R.id.action_hadithBooksFragment_To_hadithChaptersOfABookFragment)
            }
        }
    }
}