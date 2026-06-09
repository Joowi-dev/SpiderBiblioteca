package com.joel.spiderbiblioteca.data.seed

import com.joel.spiderbiblioteca.data.model.SpiderUniverse

object UniversosSeed {
    val universos: List<SpiderUniverse> = listOf(
        SpiderUniverse(
            id = 1,
            nombre = "Tierra-616",
            codigo = "Earth-616",
            spiderManPrincipal = "Peter Parker",
            descripcion = "Universo principal de Marvel Comics. Es la versión clásica donde Peter Parker se convierte en Spider-Man tras la mordedura de una araña radiactiva.",
            personajesDestacados = listOf("Peter Parker", "Mary Jane Watson", "Tía May", "Green Goblin", "Doctor Octopus", "Venom"),
            estilo = "Clásico"
        ),
        SpiderUniverse(
            id = 2,
            nombre = "Ultimate Universe",
            codigo = "Earth-1610",
            spiderManPrincipal = "Miles Morales",
            descripcion = "Universo moderno donde Miles Morales toma el legado de Spider-Man después de la muerte de Peter Parker en su realidad.",
            personajesDestacados = listOf("Miles Morales", "Peter Parker Ultimate", "Aaron Davis", "Gwen Stacy", "Ganke Lee"),
            estilo = "Moderno"
        ),
        SpiderUniverse(
            id = 3,
            nombre = "Spider-Gwen",
            codigo = "Earth-65",
            spiderManPrincipal = "Gwen Stacy",
            descripcion = "Universo alternativo donde Gwen Stacy recibe los poderes arácnidos en lugar de Peter Parker.",
            personajesDestacados = listOf("Gwen Stacy", "Peter Parker", "George Stacy", "Matt Murdock"),
            estilo = "Alternativo"
        ),
        SpiderUniverse(
            id = 4,
            nombre = "Spider-Man Noir",
            codigo = "Earth-90214",
            spiderManPrincipal = "Peter Parker Noir",
            descripcion = "Versión oscura y detectivesca ambientada en los años 30, con una estética de cine negro.",
            personajesDestacados = listOf("Peter Parker Noir", "Kingpin", "Tía May", "Felicia Hardy"),
            estilo = "Noir"
        ),
        SpiderUniverse(
            id = 5,
            nombre = "Spider-Man 2099",
            codigo = "Earth-928",
            spiderManPrincipal = "Miguel O'Hara",
            descripcion = "Futuro cyberpunk donde Miguel O'Hara obtiene poderes arácnidos mediante alteraciones genéticas.",
            personajesDestacados = listOf("Miguel O'Hara", "Lyla", "Tyler Stone", "Gabriel O'Hara"),
            estilo = "Futurista"
        ),
        SpiderUniverse(
            id = 6,
            nombre = "Spider-Punk",
            codigo = "Earth-138",
            spiderManPrincipal = "Hobie Brown",
            descripcion = "Universo rebelde donde Spider-Man es un símbolo de resistencia contra sistemas autoritarios.",
            personajesDestacados = listOf("Hobie Brown", "Captain Anarchy", "Kang", "Osborn"),
            estilo = "Punk"
        ),
        SpiderUniverse(
            id = 7,
            nombre = "Spider-Ham",
            codigo = "Earth-8311",
            spiderManPrincipal = "Peter Porker",
            descripcion = "Universo paródico habitado por animales antropomórficos, donde Peter Porker se convierte en Spider-Ham.",
            personajesDestacados = listOf("Peter Porker", "Mary Jane Waterbuffalo", "Ducktor Doom"),
            estilo = "Humorístico"
        ),
        SpiderUniverse(
            id = 8,
            nombre = "Peni Parker",
            codigo = "Earth-14512",
            spiderManPrincipal = "Peni Parker",
            descripcion = "Universo futurista de estilo anime donde Peni pilota el robot SP//dr junto a una araña radiactiva.",
            personajesDestacados = listOf("Peni Parker", "SP//dr", "Tía May", "Addy Brock"),
            estilo = "Anime / Mecha"
        ),
        SpiderUniverse(
            id = 9,
            nombre = "Superior Spider-Man",
            codigo = "Earth-616",
            spiderManPrincipal = "Otto Octavius",
            descripcion = "Etapa dentro del universo principal donde Otto Octavius ocupa el cuerpo de Peter Parker y actúa como Spider-Man con métodos más extremos.",
            personajesDestacados = listOf("Otto Octavius", "Peter Parker", "Anna Maria Marconi", "J. Jonah Jameson"),
            estilo = "Oscuro / Estratégico"
        ),
        SpiderUniverse(
            id = 10,
            nombre = "Scarlet Spider",
            codigo = "Earth-616",
            spiderManPrincipal = "Ben Reilly / Kaine Parker",
            descripcion = "Historia relacionada con los clones de Peter Parker, especialmente Ben Reilly y Kaine, quienes adoptan identidades arácnidas propias.",
            personajesDestacados = listOf("Ben Reilly", "Kaine Parker", "Peter Parker", "Chacal"),
            estilo = "Clones"
        ),
        SpiderUniverse(
            id = 11,
            nombre = "Marvel Cinematic Universe",
            codigo = "Earth-199999 / MCU",
            spiderManPrincipal = "Peter Parker",
            descripcion = "Universo cinematográfico donde Peter Parker es interpretado como un joven héroe relacionado con los Vengadores.",
            personajesDestacados = listOf("Peter Parker", "Tony Stark", "Ned Leeds", "MJ", "Doctor Strange"),
            estilo = "Cinematográfico"
        ),
        SpiderUniverse(
            id = 12,
            nombre = "Insomniac Universe",
            codigo = "Earth-1048",
            spiderManPrincipal = "Peter Parker y Miles Morales",
            descripcion = "Universo de los videojuegos de Insomniac Games donde Peter es un Spider-Man experimentado y Miles comienza su camino como héroe.",
            personajesDestacados = listOf("Peter Parker", "Miles Morales", "Mary Jane", "Rio Morales", "Venom", "Kraven"),
            estilo = "Videojuego moderno"
        )
    )
}
