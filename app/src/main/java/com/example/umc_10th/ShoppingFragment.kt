    package com.example.umc_10th

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.activity.enableEdgeToEdge
    import androidx.constraintlayout.widget.ConstraintLayout
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowCompat.enableEdgeToEdge
    import androidx.core.view.WindowInsetsCompat
    import androidx.fragment.app.Fragment
    import com.example.umc_10th.databinding.ShoppingFragmentBinding


    import androidx.core.content.ContextCompat
    class ShoppingFragment: Fragment(R.layout.shopping_fragment) {
        private lateinit var binding: ShoppingFragmentBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
            ): View {
            binding = ShoppingFragmentBinding.inflate(inflater, container, false);
            return binding.root;
        }
        private lateinit var contentView: ConstraintLayout
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.btnPurchase.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragmentContainer, PurchaseFragment())
                    .commit()
            }
        }

    }