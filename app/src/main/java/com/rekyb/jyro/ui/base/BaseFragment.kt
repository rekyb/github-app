package com.rekyb.jyro.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import timber.log.Timber

abstract class BaseFragment<VB : ViewBinding>(
    @LayoutRes val layoutResId: Int,
) : Fragment() {

    private var _binding: VB? = null

    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        return _binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (_binding as? ViewDataBinding?)?.unbind()
        _binding = null

        Timber.d("$_binding destroyed")
    }

}