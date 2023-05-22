import Shared
import SwiftUI

struct SearchContent: View {
    var state: AppState.Data

    var body: some View {
        ForEach(state.results, id: \.animal.id) { result in
            ResultItem(animal: result.animal, owner: result.owner)
                .listRowInsets(.init(top: 0, leading: 0, bottom: 20, trailing: 0))
                .listRowSeparator(.hidden)
                .listRowBackground(
                    RoundedRectangle(cornerRadius: 10)
                        .background(Color(.clear))
                        .foregroundColor(Color(.systemBackground))
                        .padding(.bottom)
                )
        }
    }
}

struct ResultItem: View {
    let animal: Animal
    let owner: Owner

    @State private var showContactSheet = false

    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            VStack(alignment: .leading) {
                Text(animal.name)
                    .font(.title)
                Text(animal.gender)
            }

            if let chip = animal.chip {
                VStack(alignment: .leading) {
                    Text(chip)
                    Text("Chipnummer")
                        .font(.footnote)
                }
            }

            if let tattoo = animal.tattoo {
                VStack(alignment: .leading) {
                    Text(tattoo)
                    Text("Tatuering")
                        .font(.footnote)
                }
            }

            VStack(alignment: .leading) {
                Text(animal.color)
                Text(animal.breed)
                    .font(.footnote)
            }

            VStack(alignment: .leading) {
                Text("\(animal.age) år gammal")
                Text("Född \(animal.birthday.toNSDate().formatted(.dateTime.day().month(.wide).year()))")
                    .font(.footnote)
            }

            if let overview = animal.overview {
                Text(overview)
                    .padding()
                    .frame(maxWidth: UIScreen.main.bounds.width, alignment: .leading)
                    .background(Color(.secondarySystemBackground))
                    .cornerRadius(10)
            }

            HStack {
                Button {
                    showContactSheet.toggle()
                } label: {
                    Text("Kontakta \(owner.name.first)")
                        .frame(maxWidth: UIScreen.main.bounds.width)
                }
                .buttonStyle(.borderedProminent)
                .controlSize(.large)
            }
        }
        .padding()
        .sheet(isPresented: $showContactSheet) {
            ContactOwner(owner: owner)
        }
    }
}
