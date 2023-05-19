import KMMViewModelSwiftUI
import Shared
import SwiftUI

struct ContentView: View {
    @StateViewModel var viewModel = SharedModule.shared.appViewModel
    @State private var query = ""

    var body: some View {
        NavigationStack {
            switch viewModel.state {
            case let state as AppState.Data:
                SearchContent(state: state)
                    .environmentViewModel(viewModel)

            case is AppState.Empty:
                Text("Inga katter hittades för denna sökning. Prova ett annat chipnummer eller tatuering.")
                    .padding()
                    .multilineTextAlignment(.center)

            default:
                Text("Ingen sökning har gjorts.")
            }
        }
        .searchable(text: $query, prompt: "Chipnummer eller tatuering")
        .onChange(of: viewModel.state) { query = $0.query }
        .onChange(of: query) { viewModel.updateQuery(query: $0) }
        .animation(.easeInOut, value: viewModel.state)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            ContentView()
        }
    }
}
