import Shared
import SwiftUI

@main
struct OddApp: App {
    init() {
        SharedModuleKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
