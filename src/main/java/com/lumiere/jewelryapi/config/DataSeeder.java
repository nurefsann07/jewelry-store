package com.lumiere.jewelryapi.config;

import com.lumiere.jewelryapi.entity.Product;
import com.lumiere.jewelryapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    private static String img(String id) {
        return "https://images.unsplash.com/" + id + "?w=900&h=1200&fit=crop&auto=format&q=85";
    }

    private static String premium(String id) {
        return "https://plus.unsplash.com/" + id + "?w=900&h=1200&fit=crop&auto=format&q=85";
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) return;

        List<Product> products = List.of(
                // 💍 RINGS — 6 ürün, hepsi farklı yüzük görseli
                Product.builder().name("Lunar Halo Ring").category("Rings").price(2850.0).tag("New").stock(8)
                        .imageUrl(img("photo-1605100804763-247f67b3557e"))
                        .description("Oval moonstone set in 18k yellow gold with a halo of brilliant diamonds.").build(),
                Product.builder().name("Solitaire Diamond").category("Rings").price(5200.0).tag(null).stock(5)
                        .imageUrl(img("photo-1763256614634-7feb3ff79ff3"))
                        .description("Classic round-cut diamond solitaire on a platinum band.").build(),
                Product.builder().name("Eternity Band").category("Rings").price(3400.0).tag("Exclusive").stock(12)
                        .imageUrl(img("photo-1588444650700-fd887f15a9e7"))
                        .description("Channel-set diamonds forming a continuous circle of brilliance.").build(),
                Product.builder().name("Sapphire Empress").category("Rings").price(4750.0).tag(null).stock(6)
                        .imageUrl(img("photo-1606623546924-a4f3ae5ea3e8"))
                        .description("Deep blue Ceylon sapphire surrounded by pavé diamonds.").build(),
                Product.builder().name("Crystal Halo").category("Rings").price(3850.0).tag("New").stock(7)
                        .imageUrl(img("photo-1583937443325-97becde478a8"))
                        .description("Brilliant centerstone embraced by a halo of pavé diamonds on platinum.").build(),
                Product.builder().name("Rose Gold Band").category("Rings").price(1950.0).tag("Sale").stock(15)
                        .imageUrl(img("photo-1689367436629-1d288f1e23b6"))
                        .description("Hand-finished 18k rose gold band with subtle diamond accents.").build(),

                // 📿 NECKLACES — 6 ürün, hepsi farklı kolye görseli
                Product.builder().name("Celestial Necklace").category("Necklaces").price(6400.0).tag(null).stock(4)
                        .imageUrl(img("photo-1599643478518-a784e5dc4c8f"))
                        .description("Deep-sea blue sapphires in hand-linked gold form this cascading necklace.").build(),
                Product.builder().name("Diamond Cascade").category("Necklaces").price(8900.0).tag("Exclusive").stock(3)
                        .imageUrl(premium("premium_photo-1681276169450-4504a2442173"))
                        .description("A cascading flow of brilliant-cut diamonds in white gold.").build(),
                Product.builder().name("Pearl Strand").category("Necklaces").price(2200.0).tag(null).stock(15)
                        .imageUrl(img("photo-1515562141207-7a88fb7ce338"))
                        .description("Lustrous Akoya pearls strung on silk with a diamond clasp.").build(),
                Product.builder().name("Emerald Drop Chain").category("Necklaces").price(5600.0).tag("New").stock(7)
                        .imageUrl(img("photo-1616837874254-8d5aaa63e273"))
                        .description("Colombian emerald pendant on a delicate gold chain.").build(),
                Product.builder().name("Heart of Ocean").category("Necklaces").price(4500.0).tag("Exclusive").stock(4)
                        .imageUrl(premium("premium_photo-1681276170092-446cd1b5b32d"))
                        .description("A heart-shaped sapphire suspended on a hand-knotted silk cord.").build(),
                Product.builder().name("Snake Chain").category("Necklaces").price(1850.0).tag("Sale").stock(20)
                        .imageUrl(img("photo-1611652022419-a9419f74343d"))
                        .description("Sleek herringbone silver chain that catches every angle of light.").build(),

                // 💎 EARRINGS — 6 ürün, hepsi farklı küpe görseli
                Product.builder().name("Aurora Drop Earrings").category("Earrings").price(3200.0).tag("Exclusive").stock(10)
                        .imageUrl(img("photo-1617038220319-276d3cfab638"))
                        .description("Vivid tsavorite garnets with violet tanzanite in an organic motif.").build(),
                Product.builder().name("Diamond Studs").category("Earrings").price(1850.0).tag(null).stock(20)
                        .imageUrl(img("photo-1769151591224-2eee6793b885"))
                        .description("Delicate hexagonal diamond stud earrings in white gold setting.").build(),
                Product.builder().name("Pearl Drops").category("Earrings").price(980.0).tag("Sale").stock(18)
                        .imageUrl(img("photo-1671642883395-0ab89c3ac890"))
                        .description("South Sea pearl drops with diamond accents.").build(),
                Product.builder().name("Sapphire Hoops").category("Earrings").price(2700.0).tag(null).stock(8)
                        .imageUrl(premium("premium_photo-1681276170291-27698ccc0a8e"))
                        .description("Gold hoops set with channel-cut blue sapphires.").build(),
                Product.builder().name("Crystal Drops").category("Earrings").price(1450.0).tag("New").stock(11)
                        .imageUrl(img("photo-1693212793204-bcea856c75fe"))
                        .description("Faceted crystal drops with a brilliant-cut center stone.").build(),
                Product.builder().name("Sapphire Buds").category("Earrings").price(2350.0).tag(null).stock(9)
                        .imageUrl(img("photo-1774504347388-3d01f7cac097"))
                        .description("Petite sapphire and diamond cluster earrings inspired by spring blooms.").build(),

                // 🔗 BRACELETS — 6 ürün, hepsi farklı bilezik görseli
                Product.builder().name("Midnight Bracelet").category("Bracelets").price(4750.0).tag(null).stock(6)
                        .imageUrl(img("photo-1619119069152-a2b331eb392a"))
                        .description("Brilliant-cut diamonds in blackened gold create striking contrast.").build(),
                Product.builder().name("Tennis Bracelet").category("Bracelets").price(8200.0).tag("Exclusive").stock(4)
                        .imageUrl(img("photo-1602173574767-37ac01994b2a"))
                        .description("Continuous line of carefully matched diamonds in platinum.").build(),
                Product.builder().name("Pearl Bangle").category("Bracelets").price(1450.0).tag("Sale").stock(14)
                        .imageUrl(img("photo-1774978239401-7bfaff4207ee"))
                        .description("Hinged gold bangle adorned with freshwater pearls.").build(),
                Product.builder().name("Gold Chain").category("Bracelets").price(890.0).tag(null).stock(25)
                        .imageUrl(img("photo-1573446238824-c28afa0cd312"))
                        .description("Hand-woven 18k yellow gold chain bracelet.").build(),
                Product.builder().name("Charm Bracelet").category("Bracelets").price(2150.0).tag("New").stock(11)
                        .imageUrl(img("photo-1717605383946-96c6884c36b4"))
                        .description("Gold bracelet with hand-set sapphires, emeralds, and rubies.").build(),
                Product.builder().name("Silver Heart").category("Bracelets").price(750.0).tag("Sale").stock(22)
                        .imageUrl(img("photo-1676120963306-8969fa6a810e"))
                        .description("Sterling silver bracelet with a sculpted heart charm.").build()
        );

        productRepository.saveAll(products);
        System.out.println("✅ " + products.size() + " ürün eklendi (4 kategori × 6 ürün)!");
    }
}