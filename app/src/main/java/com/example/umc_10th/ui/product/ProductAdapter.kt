package com.example.umc_10th.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.data.model.Product
import com.example.umc_10th.databinding.ItemHomeBinding

//1. 클래스 선언 및 생성자
class ProductAdapter( //RecyclerView에 데이터 공급 및 View를 관리할 Adapter 클래스
    private val productList: MutableList<Product>,
    //화면에 그릴 데이터 리스트, MutableList : 수정 가능한 리스트, 데이터 추가/삭제 가능
    private val onItemClicked: (Product) -> Unit
    //람다식 함수, 아이템 클릭 시 실행할 동작을 외부(Fragment)에서 받아와서 저장
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    //상속 부분, ProductViewHolder를 사용하는 RecyclerView의 Adapter임을 명시

    //2. ViewHolder 클래스 (내부 클래스)
    inner class ProductViewHolder(val binding: ItemHomeBinding) :
    //개별 아이템 뷰를 보관하는 클래스, inner 선언으로 바깥 어댑터 속성에 접근
    //ViewBinding 객체를 인자로 받아 XML 내 ID들에 접근 (val binding: ItemHomeBinding)

        RecyclerView.ViewHolder(binding.root) {
        //부모 클래스 ViewHolder에게 실제 루트 뷰를 전달


        //3. 데이터 바인딩 함수
            fun bind(product: Product) {
            // 특정 순서의 데이터를 실제 UI에 입히는 함수

            binding.productImage.setImageResource(product.imageRes) //이미지 리소스 ID를 사용해 이미지뷰에 사진을 세팅
            binding.productName.text = product.name //텍스트뷰에 상품 이름 추가
            binding.productPrice.text = product.price //텍스트뷰에 상품 가격 추가

            binding.root.setOnClickListener {
                onItemClicked(product)
            //아이템 전체 레이아웃(root)이 눌렸을 때, 아까 생성자에서 받은 onItemClicked 함수에 현재 상품 정보를 담아 호출
            }
        }
    }

    //4. ViewHolder 생성 (최초 1회만 작동)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        //ViewHolder가 새로 만들어져야 할 때 호출 (화면이 처음 뜰 때나 새로운 칸이 필요할 때)
        val binding = ItemHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        //레이아웃을 실제 코틀린 객체(View)로 변환(inflate)
        )
        return ProductViewHolder(binding)
        //생성된 바인딩 객체를 들고 있는 뷰홀더를 반환
    }

    fun updateList(newList: List<Product>) {
        // 1. 기존에 들고 있던 리스트의 내용을 싹 비웁니다.
        productList.clear()

        // 2. 새로운 리스트의 내용을 모두 추가합니다.
        productList.addAll(newList)

        // 3. 어댑터에게 데이터가 바뀌었으니 화면을 다시 그리라고 명령합니다. (핵심!)
        notifyDataSetChanged()
    }

    //5. 데이터 연결 및 개수 확인
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        //생성된 ViewHolder에 데이터를 실제로 그려주는 단계, 스크롤을 내릴 때마다 계속 호출됨
        holder.bind(productList[position])
        //현재 순서(position)에 맞는 데이터를 리스트에서 꺼내 뷰홀더의 bind 함수로 전달
    }



    override fun getItemCount(): Int = productList.size
    //리사이클러뷰에게 그려야 할 아이템이 총 몇 개인지 리스트의 크기(size)를 반환


}