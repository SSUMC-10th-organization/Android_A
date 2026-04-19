package com.example.umc_10th.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.R
import com.example.umc_10th.data.local.SharedPreferenceManager
import com.example.umc_10th.data.model.PurchaseProduct
import com.example.umc_10th.databinding.FragmentPurchaseBinding
import com.example.umc_10th.domain.repository.ProductRepository
import com.example.umc_10th.domain.repository.UserRepository
import com.example.umc_10th.ui.main.MainActivity
import com.example.umc_10th.ui.purchase.PurchaseAdapter
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class AllFragment : Fragment() {
    private val viewModel: AllViewModel by viewModels()
    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var purchaseAdapter: PurchaseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
        viewModel.fetchProducts()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.purchaseList.collect { list ->
                purchaseAdapter.updateList(list)
            }
        }
    }

    private fun initRecyclerView() {
        purchaseAdapter = PurchaseAdapter(
            itemList = mutableListOf(),
            onItemClicked = { /* 클릭 동작 */ },
            onHeartClicked = { product ->
                if (product.isFavorite) {
                    MainActivity.Companion.wishlistStorage.addProduct(product)
                } else {
                    MainActivity.Companion.wishlistStorage.removeProduct(product)
                }
            }
        )
        binding.purchaseRecyclerView.apply {
            adapter = purchaseAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshFavoriteStatus() // 화면 돌아올 때 하트 상태만 갱신
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}