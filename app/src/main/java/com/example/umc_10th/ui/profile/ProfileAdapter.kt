package com.example.umc_10th.ui.profile

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_10th.data.model.UserData
import com.example.umc_10th.databinding.ItemFollowingBinding

//1. 클래스 선언 및 생성자
class ProfileAdapter(private var userList: List<UserData>) :
      /*화면에 표시할 유저 데이터 리스트
       이후 updateData 함수를 통해 리스트 자체를 교체해야 하므로 val이 아닌 var로 선언*/

    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {
        //RecyclerView 어댑터 기능을 상속받으며, 사용할 ViewHolder를 지정

    inner class ProfileViewHolder(private val binding: ItemFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //2. ViewHolder와 Glide 이미지 로딩 (bind 함수)
        fun bind(user: UserData) {
            // 이미지 로딩 라이브러리인 Glide 시작, 이미지뷰의 context를 넘겨주어 생명주기에 맞게 이미지를 관리
            Glide.with(binding.ivFollowingUser.context)
                .load(user.avatar) //로드 대상
                .placeholder(R.color.darker_gray) // 로딩 중 보일 임시 색상
                .into(binding.ivFollowingUser) //최종적으로 이미지를 넣을 대상
        }
    }

    //3. ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemFollowingBinding.inflate(
            //팔로잉 목록 아이템 레이아웃(item_following.xml)을 메모리에 올림
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileViewHolder(binding) //생성된 바인딩 객체를 뷰홀더에 담아 반환
    }

    //4. 연결 및 개수 확인
    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        //스크롤 시마다 호출되며, 리스트의 position에 해당하는 UserData를 뷰홀더의 bind 함수에 전달
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
    //전체 유저 리스트의 개수를 RecyclerView에 알려줌


    // 5. 외부 데이터 업데이트 (updateData)
    fun updateData(newList: List<UserData>) {
        userList = newList //서버(Retrofit) 등에서 새로 받아온 리스트로 기존 리스트를 완전히 교체
        notifyDataSetChanged() //어댑터에게 데이터 변경을 알림
    }
}