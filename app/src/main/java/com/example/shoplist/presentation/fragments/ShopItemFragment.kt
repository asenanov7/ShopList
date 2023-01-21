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
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.viewmodels.ShopItemViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment:Fragment() {
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextCount: TextInputEditText
    private lateinit var buttonSave: Button
    private lateinit var shopItemViewModel: ShopItemViewModel

    private var screedMode:String? = null
    private var shopItemId:Int? = null

    interface ShouldCloseFragmentListener{
        fun shouldCloseFragment()
    }
    private lateinit var shouldCloseFragmentBridge:ShouldCloseFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShouldCloseFragmentListener){
            shouldCloseFragmentBridge = context
        }else {
            throw Exception("If Activity used ShopItemFragment, activity should implement ShouldCloseFragmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screedMode = arguments?.getString(KEY_MODE)
        shopItemId = arguments?.getInt(KEY_ID, ShopItem.UNDEFINED_ID)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shop_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]


        when(screedMode){
            ADDING_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
        }

        textWatcher()
    }

    private fun textWatcher(){
        shopItemViewModel.errorInputNameLD.observe(viewLifecycleOwner){textInputLayoutName.error = it}
        shopItemViewModel.errorInputCountLD.observe(viewLifecycleOwner){textInputLayoutCount.error = it}
        textInputEditTextName.doBeforeTextChanged{text, start, count, after -> textInputLayoutName.error=null }
        textInputEditTextCount.doBeforeTextChanged{text, start, count, after -> textInputLayoutCount.error=null }
    }

    private fun launchAddMode(){
        buttonSave.setOnClickListener {
            shopItemViewModel.addShopItem(
                textInputEditTextName.text.toString(), textInputEditTextCount.text.toString())
        }

        shopItemViewModel.screenShouldBeFinishedLD.observe(viewLifecycleOwner){
            shouldCloseFragmentBridge.shouldCloseFragment()
        }
    }

    private fun launchEditMode(){
        shopItemId?.let {
            shopItemViewModel.getShopItem(it)
            shopItemViewModel.shopItemLD.observe(viewLifecycleOwner) {
                textInputEditTextName.setText(it.name)
                textInputEditTextCount.setText(it.count.toString())
            }

            buttonSave.setOnClickListener {
                shopItemViewModel.editShopItem(
                    textInputEditTextName.text.toString(), textInputEditTextCount.text.toString())
            }

            shopItemViewModel.screenShouldBeFinishedLD.observe(viewLifecycleOwner) {
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