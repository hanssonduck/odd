package duck.hansson.odd.shared.module

import duck.hansson.odd.shared.viewmodel.AppViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal actual val platformModule = module {
    singleOf(::AppViewModel)
}

fun initKoin() = initKoin { }

object SharedModule : KoinComponent {
    val appViewModel by inject<AppViewModel>()
}
