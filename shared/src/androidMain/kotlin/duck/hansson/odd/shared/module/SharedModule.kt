@file:JvmName("SharedModuleJvm")

package duck.hansson.odd.shared.module

import duck.hansson.odd.shared.viewmodel.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal actual val platformModule = module {
    viewModelOf(::AppViewModel)
}
