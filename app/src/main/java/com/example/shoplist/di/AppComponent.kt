package com.example.shoplist.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.shoplist.presentation.activities.MainActivity
import com.example.shoplist.presentation.fragments.ShopItemFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

@ApplicationScope
@Component(modules = [DataModule::class])
interface AppComponent {

    fun getFragmentSubComponent(): FragmentSubComponent.Factory
    fun getActivitySubComponent(): ActivitySubComponent.Factory

    @Component.Factory
    interface AppComponentFactory{
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

}

@Subcomponent(modules = [ViewModelModule::class])
interface FragmentSubComponent{

    fun inject(fragment: ShopItemFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create():FragmentSubComponent
    }
}

@Subcomponent(modules = [ViewModelModule::class])
interface ActivitySubComponent {

    fun inject(mainActivity: MainActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivitySubComponent
    }
}
