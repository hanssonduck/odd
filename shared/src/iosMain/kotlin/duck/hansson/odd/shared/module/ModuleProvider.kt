package duck.hansson.odd.shared.module

import org.koin.core.scope.Scope
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual fun Scope.provideContactActions() = object : ContactActions {
    override fun call(phoneNumber: String) {
        val url = NSURL(string = "tel://$phoneNumber")
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }
    }

    override fun email(to: String) {
        val url = NSURL(string = "mailto:$to")
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }
    }
}
