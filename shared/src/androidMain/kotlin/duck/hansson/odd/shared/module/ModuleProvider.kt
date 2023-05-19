@file:JvmName("ModuleProviderJvm")

package duck.hansson.odd.shared.module

import android.content.Intent
import android.net.Uri
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

internal actual fun Scope.provideContactActions() = object : ContactActions {
    override fun call(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        androidContext().startActivity(intent)
    }

    override fun email(to: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        androidContext().startActivity(intent)
    }
}
