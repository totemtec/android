package com.example.navigation.args

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.navigation.R

private const val ARG_PRODUCT_ID = "productId"

class FragmentDest : Fragment() {
    private var productId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 从 Bundle 获取参数值，传统方式
//        arguments?.let {
//            productId = it.getString(ARG_PRODUCT_ID)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 从 Safe Args 获取参数值
        arguments?.let {
            productId = FragmentDestArgs.fromBundle(it).productId
        }
        view.findViewById<TextView>(R.id.textView).text = "productId = $productId"
    }
}