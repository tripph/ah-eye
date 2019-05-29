package usa.tripph.aheyespringboot.blizzapi.models


data class AuctionJsonBase(val realms: List<Realm>, val auctions: List<AuctionItem>)
data class Realm(val name: String, val slug: String)
data class AuctionItem(val auc: Long, val item: Int, val owner: String, val ownerRealm: String,
                       val bid: Long, val buyout: Long, val quantity: Int, val timeLeft: String,
                       val rand: Int, val seed: Int, val context: Int, val bonusLists: List<Bonus>?,
                       val petSpeciesId: Int?, val petBreedId: Int?, val petLevel: Int?, val petQualityId: Int?,
                       val modifiers: List<Modifier>?)
data class Bonus(val bonusListId: Int)
data class Modifier(val type: Int, val value: Int)