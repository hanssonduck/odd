package duck.hansson.odd.shared.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.yearsUntil
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

private object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: Instant) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder) = Instant.fromEpochMilliseconds(decoder.decodeLong())
}

@Serializable
data class RemoteAnimal(
    @SerialName("djurid")
    val id: Int,
    @SerialName("tatuering")
    val tattoo: String,
    @SerialName("chipnummer")
    val chip: String,
    @SerialName("tilltalsnamn")
    val name: String,
    @SerialName("kannetecken")
    val overview: String,
    @SerialName("ras")
    val breed: String,
    @SerialName("farg")
    val color: String,
    @SerialName("kon")
    val gender: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("fodelsedatum")
    val birthday: Instant,
) {
    fun toAnimal() = Animal(
        id = this.id,
        tattoo = this.tattoo.ifBlank { null },
        chip = this.chip.ifBlank { null },
        name = this.name,
        overview = this.overview.ifBlank { null },
        breed = this.breed,
        color = this.color,
        gender = this.gender,
        birthday = this.birthday
    )
}

data class Animal(
    val id: Int,
    val tattoo: String?,
    val chip: String?,
    val name: String,
    val overview: String?,
    val breed: String,
    val color: String,
    val gender: String,
    val birthday: Instant,
    val age: Int = birthday.yearsUntil(
        other = Clock.System.now(),
        timeZone = TimeZone.currentSystemDefault()
    )
)
