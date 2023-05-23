import Shared
import SwiftUI

struct SearchContent: View {
    var state: AppState.Data

    var body: some View {
        ForEach(state.results, id: \.animal.id) { result in
            ResultItem(animal: result.animal, owner: result.owner)
                .listRowInsets(EdgeInsets())
                .listRowSeparator(.hidden)
                .listRowBackground(
                    RoundedRectangle(cornerRadius: 10)
                        .padding(.bottom)
                        .foregroundColor(Color(.systemBackground))
                )
        }
    }
}

struct ResultItem: View {
    let animal: Animal
    let owner: Owner

    @State private var showContactSheet = false

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            VStack(alignment: .leading) {
                Text(animal.name)
                    .font(.title)
                Text(animal.gender)
            }
            .padding()

            if let chip = animal.chip {
                VStack(alignment: .leading) {
                    Text(chip)
                    Text("Chipnummer")
                        .font(.footnote)
                }
                .padding(.horizontal)
            }

            if let tattoo = animal.tattoo {
                VStack(alignment: .leading) {
                    Text(tattoo)
                    Text("Tatuering")
                        .font(.footnote)
                }
                .padding(.horizontal)
            }

            VStack(alignment: .leading) {
                Text(animal.color)
                Text(animal.breed)
                    .font(.footnote)
            }
            .padding()

            VStack(alignment: .leading) {
                Text("\(animal.age) år gammal")
                Text("Född \(animal.birthday.toNSDate().formatted(.dateTime.day().month(.wide).year()))")
                    .font(.footnote)
            }
            .padding(.horizontal)

            if let overview = animal.overview {
                ZStack {
                    Text(overview)
                        .padding()
                        .frame(maxWidth: UIScreen.main.bounds.width, alignment: .leading)
                        .background(Color(.secondarySystemBackground))
                        .cornerRadius(10)
                }
                .padding([.horizontal, .top])
            }

            HStack {
                Button {
                    showContactSheet.toggle()
                } label: {
                    Text("Kontakta \(owner.name.first)")
                        .frame(maxWidth: UIScreen.main.bounds.width)
                }
                .padding()
                .buttonStyle(.borderedProminent)
                .controlSize(.large)
            }
        }
        .padding(.bottom)
        .sheet(isPresented: $showContactSheet) {
            ContactOwner(owner: owner)
        }
    }
}
