package duck.hansson.odd.shared.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteOwner(
    @SerialName("fornamn")
    val firstName: String,
    @SerialName("efternamn")
    val lastName: String,
    @SerialName("mobil")
    val phoneNumber: String,
    @SerialName("epost")
    val email: String,
    @SerialName("utdelningsadress")
    val address: String,
    @SerialName("postnummer")
    val zip: String,
    @SerialName("postort")
    val city: String
) {
    fun toOwner() = Owner(
        name = Owner.Name(
            first = this.firstName,
            last = this.lastName
        ),
        phoneNumber = this.phoneNumber.ifBlank { null },
        email = this.email.ifBlank { null },
        location = Owner.Location(
            address = this.address,
            zip = this.zip
                .chunked(size = 3)
                .joinToString(separator = " "),
            city = this.city
        )
    )
}

data class Owner(
    val name: Name,
    val fullName: String = "${name.first} ${name.last}",
    val phoneNumber: String?,
    val email: String?,
    val noContacts: Boolean = phoneNumber == null || email == null,
    val location: Location?
) {
    data class Name(
        val first: String,
        val last: String
    )

    data class Location(
        val address: String,
        val zip: String,
        val city: String
    )
}
