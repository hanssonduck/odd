import KMMViewModelSwiftUI
import Shared
import SwiftUI

struct ContentView: View {
    @StateViewModel var viewModel = SharedModule.shared.appViewModel

    @State private var state: AppState?
    @State private var query = ""

    var body: some View {
        NavigationStack {
            List {
                switch state {
                case let state as AppState.Data:
                    SearchContent(state: state)
                        .environmentViewModel(viewModel)

                case is AppState.Empty:
                    Text("Inga katter hittades för denna sökning. Prova ett annat chipnummer eller tatuering.")
                        .padding()
                        .multilineTextAlignment(.center)

                default:
                    Text("Ingen sökning har gjorts.")
                        .frame(maxWidth: UIScreen.main.bounds.width, alignment: .center)
                        .padding()
                }
            }
            .navigationTitle("Kattregister")
            .background(Color(.secondarySystemBackground))
            .animation(.linear, value: query.hashValue)
        }
        .searchable(text: $query, prompt: "Chipnummer eller tatuering")
        .onChange(of: viewModel.state) { value in
            query = value.query
            withAnimation { state = value }
        }
        .onChange(of: query) { query in
            withAnimation {
                viewModel.updateQuery(query: query)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            ContentView()
        }
    }
}
