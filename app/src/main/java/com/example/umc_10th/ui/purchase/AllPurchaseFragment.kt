package com.example.umc_10th.ui.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_10th.data.model.ProductData
import com.example.umc_10th.databinding.FragmentAllPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllPurchaseFragment : Fragment() {

    private var _binding: FragmentAllPurchaseBinding? = null
    private val binding get() = _binding!!

    private val purchaseViewModel: PurchaseViewModel by viewModels()

    private var products by mutableStateOf<List<ProductData>>(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (binding.cvProduct as ComposeView).setContent {
            PurchaseProductGrid(
                products = products,
                onLikeClick = { product ->
                    purchaseViewModel.toggleLike(product.id)
                }
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                purchaseViewModel.uiState.collect { state ->
                    products = state.products
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}