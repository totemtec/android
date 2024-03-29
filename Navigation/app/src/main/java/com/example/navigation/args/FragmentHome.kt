package com.example.navigation.args

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.navigation.R

class FragmentHome : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.gotoDest).setOnClickListener {
            // 不传递参数，使用默认参数
//            findNavController().navigate(R.id.action_fragmentHome_to_fragmentDest)

            // 通过 Bundle 传递参数，传统方式
//            findNavController().navigate(R.id.action_fragmentHome_to_fragmentDest,
//                Bundle().also {
//                    it.putString("productId", "2")
//                })


            // 通过 Safe Args 传递参数
            val navDestination = FragmentHomeDirections.actionFragmentHomeToFragmentDest("3")
            findNavController().navigate(navDestination)

        }
    }
}