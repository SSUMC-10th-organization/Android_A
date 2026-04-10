package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.MainActivity
import com.example.umc_10th.R
import com.example.umc_10th.adapter.PurchaseAdapter
import com.example.umc_10th.data.PurchaseProduct
import com.example.umc_10th.data.SharedPreferenceManager
import com.example.umc_10th.databinding .FragmentPurchaseBinding
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AllFragment : Fragment() {

    //1. 바인딩과 데이터 선언
    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!
    //_binding, get(): fragment는 view보다 오래 살아남기 때문에 메모리 누수를 방지하기 위해 Nullable로 선언
    //사용할 때는 !!를 통해 접근
    private var purchaseList = mutableListOf<PurchaseProduct>()
    //화면에 그려질 실제 데이터들을 담아두는 리스트


    //2. 초기화 로직 (onCreateView ~ onViewCreated)
    override fun onCreateView(
        //XML 레이아웃을 실제 뷰 객체로 만드는 단계
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //view가 완전히 생성된 후, 데이터 로드, recyclerview 초기화 같은 로직 실행
        super.onViewCreated(view, savedInstanceState)

        initDummyData()
        initRecyclerView()
    }

    //3. 데이터 로드 및 조건부 저장 (initDummyData)
    private fun initDummyData() {
        val type = object : TypeToken<List<PurchaseProduct>>() {}.type

        viewLifecycleOwner.lifecycleScope.launch {
            //비동기 데이터 로딩을 위해 코루틴을 실행, fragment의 생명주기에 맞춰 안전하게 작동


            val savedList = MainActivity.prefManager.getObjectList<PurchaseProduct>(
                //전역적으로 선언된 Manager를 통해 로컬 데이터 읽고 씀
                SharedPreferenceManager.KEY_PURCHASE_PRODUCTS, type
            ).first()

            if (savedList.isEmpty()) {
                //저장된 게 없다면 새로 만들기
                val dummy = mutableListOf(
                    PurchaseProduct(R.drawable.socks1, "Nike Everyday Plus\nCushioned", "Training Ankle Socks (6 Pairs)\n5 Colours", "US$20"),
                    PurchaseProduct(R.drawable.socks2, "Nike Elite Crew", "Basketball Crew Socks\n3 Colours", "US$160"),
                    PurchaseProduct(R.drawable.women_shoes, "Nike Air Force 1 '07", "Classic Design", "US$115"),
                    PurchaseProduct(R.drawable.men_shoes, "Jordan Essentials", "Comfortable Fit", "US$35"),
                    PurchaseProduct(R.drawable.socks1, "Nike Spark Lightweight", "Breathable Fabric", "US$18"),
                    PurchaseProduct(R.drawable.socks2, "Nike Multiplier", "Performance Socks", "US$22"),
                    PurchaseProduct(R.drawable.women_shoes, "Nike Air Max Pro", "Air Cushioning", "US$180"),
                    PurchaseProduct(R.drawable.men_shoes, "Nike Pegasus 40", "Running Shoes", "US$130")
                )

                //현재 wishlistStorage에 담긴 상태를 대조하여 하트 불을 켤지 말지 결정
                dummy.forEach { product ->
                    product.isFavorite = MainActivity.wishlistStorage.isFavorite(product.name!!)
                }

                purchaseList.clear()
                purchaseList.addAll(dummy)

                // DataStore에 첫 저장
                MainActivity.prefManager.saveObjectList(
                    SharedPreferenceManager.KEY_PURCHASE_PRODUCTS,
                    dummy
                )
            } else {
                //이미 저장된 데이터가 있다면 하트 상태만 업데이트해서 불러오기
                savedList.forEach { product ->
                    product.isFavorite = MainActivity.wishlistStorage.isFavorite(product.name!!)
                }
                purchaseList.clear()
                purchaseList.addAll(savedList)
            }

            //데이터 로딩 후 화면 갱신
            binding.purchaseRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    //4. 리사이클러뷰 설정 (initRecyclerView)
    private fun initRecyclerView() {
        val purchaseAdapter = PurchaseAdapter(
            itemList = purchaseList,
            onItemClicked = { product ->
                // 상품 클릭 시 동작
            },
            onHeartClicked = { product ->
                if (product.isFavorite) {
                    MainActivity.wishlistStorage.addProduct(product)
                } else {
                    MainActivity.wishlistStorage.removeProduct(product)
                }
            }
        )

        binding.purchaseRecyclerView.apply {
            adapter = purchaseAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    //5. 화면 복귀 시 상태 업데이트 (onResume)
    override fun onResume() {
        super.onResume()
        // 위시리스트 저장소와 현재 리스트를 대조해서 하트 상태 최신화
        purchaseList.forEach { product ->
            product.isFavorite = MainActivity.wishlistStorage.isFavorite(product.name!!)
        }
        // 리사이클러뷰 새로고침
        binding.purchaseRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}