package com.example.umc_10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter
    private val wishlist = mutableListOf<ProductData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(
            productList = wishlist,
            onLikeClicked = { product ->
                product.isLiked = !product.isLiked
                refreshWishlist()
            }
        )

        binding.rvWishlist.adapter = productAdapter
        binding.rvWishlist.layoutManager = GridLayoutManager(requireContext(), 2)

        refreshWishlist()
    }

    override fun onResume() {
        super.onResume()
        refreshWishlist()
    }

    private fun refreshWishlist() {
        wishlist.clear()
        wishlist.addAll(ProductSampleRepository.productList.filter { it.isLiked })
        productAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}