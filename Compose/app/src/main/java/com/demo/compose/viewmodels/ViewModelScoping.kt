package com.demo.compose.viewmodels

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MyViewModel2: ViewModel() {}
class SharedViewModel: ViewModel() {}


/**
 * `viewModel()` 扩展方法可以从最近的 ViewModelStoreOwner 获取一个实例
 * 在 Activity 和 Fragment 里面使用 viewModels() 方法
 */
@Composable
fun MyScreen2(
    modifier: Modifier = Modifier,
    // ViewModel API available in lifecycle.lifecycle-viewmodel-compose
    // The ViewModel is scoped to the closest ViewModelStoreOwner provided
    // via the LocalViewModelStoreOwner CompositionLocal. In order of proximity,
    // this could be the destination of a Navigation graph, the host Fragment,
    // or the host Activity.
    viewModel: MyViewModel2 = viewModel(),

    // using the Hilt-generated ViewModel factory
    viewModel2: MyViewModel2 = hiltViewModel()
) {
}


/**
 * 通过 viewModelStoreOwner 指定作用域
 */
@Composable
fun MyScreen3(
    context: Context = LocalContext.current,
    // ViewModel API available in lifecycle.lifecycle-viewmodel-compose
    // The ViewModel is scoped to the parent of the host Fragment
    // where this composable function is called
    viewModel: SharedViewModel = viewModel(
        viewModelStoreOwner = (context as Fragment).requireParentFragment()
//        viewModelStoreOwner = (context as Fragment).requireActivity()
    )
) { /* ... */ }


/**
 * 通过 Navigation graph 获得 BackStackEntry 作为作用域
 */
@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "profile") {

        composable("profile") {
        }

        composable("edit") { backStackEntry ->
            // Retrieve the NavBackStackEntry of "profile"
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("profile")
            }
            // Get the ViewModel scoped to the `profile` Nav graph
            val parentViewModel: SharedViewModel = viewModel(parentEntry)
            // ...

            // ViewModel API available in hilt.hilt-navigation-compose
            // and is provided using the Hilt-generated ViewModel factory
            val parentViewModel2: SharedViewModel = hiltViewModel(parentEntry)
        }
    }
}
