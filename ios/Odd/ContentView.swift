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
                    GoodInfo()
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden)
                        .listRowBackground(RoundedRectangle(cornerRadius: 10)
                            .padding(.bottom)
                            .foregroundColor(Color(.systemBackground))
                        )
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

private struct GoodInfo: View {
    @Environment(\.openURL) var openURL

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("Sök och leta efter katter inom Jordbruksverkets nya kattregister.")
            Text("Endast katter från Jordbruksverkets nya kattregister visas.")
                .padding(.bottom)
        }
        .padding()

        VStack(alignment: .leading, spacing: 10) {
            Text("Har du hittat en bortsprungen katt men är osäker på vad som bör göras?")
            Button {
                if let url = URL(string: "https://polisen.se/tjanster-tillstand/hittegods/upphittat-djur") {
                    openURL(url)
                }
            } label: {
                HStack {
                    Text("Information från polisen")
                    Spacer()
                    Image(systemName: "chevron.right")
                }
            }
            .padding(.bottom)
            .buttonStyle(.borderless)
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            ContentView()
        }
    }
}
