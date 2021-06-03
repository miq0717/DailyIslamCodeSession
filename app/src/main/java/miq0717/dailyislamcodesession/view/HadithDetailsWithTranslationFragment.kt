package miq0717.dailyislamcodesession.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import miq0717.dailyislamcodesession.databinding.FragmentHadithDetailsWithTranslationBinding

class HadithDetailsWithTranslationFragment : Fragment() {

    private lateinit var binding: FragmentHadithDetailsWithTranslationBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHadithDetailsWithTranslationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        initClickListeners()
    }

    private fun initClickListeners() {

    }
}