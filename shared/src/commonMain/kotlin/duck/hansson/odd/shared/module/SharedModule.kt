package duck.hansson.odd.shared.module

import duck.hansson.odd.shared.repository.SearchRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

internal expect val platformModule: Module

private val sharedModule = module {
    single { provideHttpClient() }
    single { provideContactActions() }
    singleOf(::SearchRepository)
}

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(sharedModule, platformModule)
}
