package com.example.shoplist.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.component
import com.example.shoplist.presentation.fragments.ShopItemFragment
import com.example.shoplist.presentation.recycler.ShopItemAdapter
import com.example.shoplist.presentation.viewmodels.MainViewModel
import com.example.shoplist.presentation.viewmodels.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.ShouldCloseFragmentListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab:FloatingActionButton
    private var fragmentContainerViewMain: FragmentContainerView? = null

    private val subComponent by lazy {
        component.getActivitySubComponent().create()
    }

    @Inject
    lateinit var adapter: ShopItemAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainVM: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        subComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecycler()
        setupSwipeListener()
        setupLongClickListener()

        mainVM.shopList.observe(this) {
            adapter.submitList(it)
        }

        fragmentContainerViewMain = findViewById(R.id.fragmentContainerViewMain)
        fab = findViewById(R.id.fab)

        if (isLand()){
            adapter.onItemClickListener = { launchEditFragment(it.id) }
            fab.setOnClickListener { launchAddFragment() }

        }else{
            adapter.onItemClickListener = { startActivity(ShopItemActivity.newIntentEditMode(this,it.id)) }
            fab.setOnClickListener{ startActivity(ShopItemActivity.newIntentAddingMode(this)) }
        }

    }
    private fun launchAddFragment(){
        val fragment = ShopItemFragment.newInstanceAdd()

        supportFragmentManager.popBackStack()
        //Без параметров метод popBackStack удаляет последний фрагмент из бэкстека, а с параматрои имени и флага
        //Удаляет все фрагменты до фрагменты с указанным именем(можно включительно удалить через флаги)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewMain, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun launchEditFragment(shopItemId:Int){
        val fragment = ShopItemFragment.newInstanceEdit(shopItemId)

        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewMain, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isLand():Boolean{
        return fragmentContainerViewMain!=null
    }

    override fun shouldCloseFragment() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun setupRecycler() {
        recyclerView = findViewById(R.id.rvShopItems)
        adapter = ShopItemAdapter()
        recyclerView.adapter = adapter
        with(recyclerView) {
            recycledViewPool.setMaxRecycledViews(R.layout.shop_item_disabled, ShopItemAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(R.layout.shop_item_enabled, ShopItemAdapter.MAX_POOL_SIZE)
        }
    }

    private fun setupLongClickListener(){
        adapter.onItemLongClickListener = {
            mainVM.editEnableState(it)
        }
    }

    private fun setupSwipeListener() {

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = mainVM.shopList.value?.get(viewHolder.adapterPosition)      //currentList можно использовать
                item?.let { mainVM.removeItem(it) }
            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}
