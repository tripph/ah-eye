package usa.tripph.aheyespringboot.blizzapi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.Test
import usa.tripph.aheyespringboot.blizzapi.models.AuctionJsonBase

class LoadAuctionFile {
    val auctionFileStream = LoadAuctionFile::class.java.classLoader.getResourceAsStream("auctions.json")
    val objectMapper = ObjectMapper().registerKotlinModule()
    @Test
    fun testIt() {
        val auctions = objectMapper.readValue(auctionFileStream, AuctionJsonBase::class.java)
        println("${auctions.auctions.size} Auctions")
        val petCount = auctions.auctions.count { i -> i.petSpeciesId != null }
        println("${petCount} Pets")
        val maxBid = auctions.auctions.maxBy { i -> i.bid }
        println("${maxBid}: Max Bid")
    }

}