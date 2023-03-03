package com.example.shoplist.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.component
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.viewmodels.ShopItemViewModel
import com.example.shoplist.presentation.viewmodels.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject

class ShopItemFragment:Fragment() {
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextCount: TextInputEditText
    private lateinit var buttonSave: Button

    private val fragmentSubcomponent by lazy {
        requireActivity().component.getFragmentSubComponent().create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val shopItemVM by lazy {
        ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
    }

    private val screedMode by lazy { arguments?.getString(KEY_MODE) }

    private val shopItemId by lazy { arguments?.getInt(KEY_ID, ShopItem.UNDEFINED_ID) }

    interface ShouldCloseFragmentListener{
        fun shouldCloseFragment()
    }
    private lateinit var shouldCloseFragmentBridge : ShouldCloseFragmentListener

    override fun onAttach(context: Context) {
        fragmentSubcomponent.inject(this)
        super.onAttach(context)

        if (context is ShouldCloseFragmentListener){
            shouldCloseFragmentBridge = context
        }else {
            throw Exception("If Activity used ShopItemFragment, activity should implement ShouldCloseFragmentListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shop_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

        when(screedMode){
            ADDING_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
        }

        textWatcher()
    }

    private fun textWatcher(){
        shopItemVM.errorInputNameLD.observe(viewLifecycleOwner){textInputLayoutName.error = it}
        shopItemVM.errorInputCountLD.observe(viewLifecycleOwner){textInputLayoutCount.error = it}
        textInputEditTextName.doBeforeTextChanged{text, start, count, after -> textInputLayoutName.error=null }
        textInputEditTextCount.doBeforeTextChanged{text, start, count, after -> textInputLayoutCount.error=null }
    }

    private fun launchAddMode(){
        buttonSave.setOnClickListener {
            shopItemVM.addShopItem(
                textInputEditTextName.text.toString(), textInputEditTextCount.text.toString())
        }

        shopItemVM.screenShouldBeFinishedLD.observe(viewLifecycleOwner){
            shouldCloseFragmentBridge.shouldCloseFragment()
        }
    }

    private fun launchEditMode(){
        shopItemId?.let {
            shopItemVM.getShopItem(it)
            shopItemVM.shopItemLD.observe(viewLifecycleOwner) {
                textInputEditTextName.setText(it.name)
                textInputEditTextCount.setText(it.count.toString())
            }

            buttonSave.setOnClickListener {
                shopItemVM.editShopItem(
                    textInputEditTextName.text.toString(), textInputEditTextCount.text.toString())
            }

            shopItemVM.screenShouldBeFinishedLD.observe(viewLifecycleOwner) {
                shouldCloseFragmentBridge.shouldCloseFragment()
            }
        }
        if (shopItemId==null){
            throw Exception("ShopItemID in Fragment - null")
        }
    }

    private fun initViews(view: View) {
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        textInputLayoutCount =view.findViewById(R.id.textInputLayoutCount)
        textInputEditTextName = view.findViewById(R.id.textInputEditTextName)
        textInputEditTextCount = view.findViewById(R.id.textInputEditTextCount)
        buttonSave = view.findViewById(R.id.buttonSave)
    }

    companion object{
        private const val KEY_MODE = "key_mode"
        private const val ADDING_MODE = "adding"
        private const val EDIT_MODE = "edit"
        private const val KEY_ID = "id_key"

        fun newInstanceAdd(): ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_MODE, ADDING_MODE)
                }
            }
        }

        fun newInstanceEdit(shopItemID:Int): ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_MODE, EDIT_MODE)
                    putInt(KEY_ID, shopItemID)
                }
            }
        }
    }
}