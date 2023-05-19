import KMMViewModelSwiftUI
import Shared
import SwiftUI

private struct InnerHeightPreferenceKey: PreferenceKey {
    static var defaultValue: CGFloat = .zero
    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) { value = nextValue() }
}

struct ContactOwner: View {
    @EnvironmentViewModel var viewModel: AppViewModel
    let owner: Owner

    @Environment(\.dismiss)
    private var dismiss
    @State private var sheetHeight: CGFloat = .zero

    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            VStack(alignment: .leading) {
                Text(owner.fullName)
                    .font(.title)
                Text("Ägare")
                    .font(.footnote)
            }

            if let location = owner.location {
                VStack(alignment: .leading) {
                    Text("\(location.address)")
                    Text("\(location.zip) \(location.city)")
                }
            }

            if let phoneNumber = owner.phoneNumber {
                Button {
                    viewModel.contactActions.call(phoneNumber: phoneNumber)
                } label: {
                    Text("Ring \(phoneNumber)")
                        .frame(maxWidth: UIScreen.main.bounds.width)
                        .padding(.horizontal, 10)
                        .padding(.vertical, 5)
                        .font(.title3)
                }
                .buttonStyle(.bordered)
            }

            if let email = owner.email {
                Button {
                    viewModel.contactActions.email(to: email)
                } label: {
                    Text("Meddela \(email)")
                        .frame(maxWidth: UIScreen.main.bounds.width)
                        .padding(.horizontal, 10)
                        .padding(.vertical, 5)
                        .font(.title3)
                }
                .buttonStyle(.bordered)
            }

            if owner.noContacts {
                Text("Den här personen har inte lagt till några kontaktsätt.")
                    .frame(maxWidth: UIScreen.main.bounds.width)
                    .fixedSize(horizontal: false, vertical: true)
                    .multilineTextAlignment(.center)
            }
        }
        .padding(.vertical, 50)
        .padding(.horizontal)
        .overlay {
            GeometryReader {
                Color.clear.preference(key: InnerHeightPreferenceKey.self, value: $0.size.height)
            }
        }
        .onPreferenceChange(InnerHeightPreferenceKey.self) { sheetHeight = $0 }
        .presentationDetents([.height(sheetHeight)])
        .presentationDragIndicator(.visible)
    }
}
