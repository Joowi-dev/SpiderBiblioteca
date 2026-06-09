package com.joel.spiderbiblioteca.data.seed

import com.joel.spiderbiblioteca.data.model.TimelineEvent

object TimelineSeed {
    val eventos: List<TimelineEvent> = listOf(
        TimelineEvent(
            id = 1,
            titulo = "Mordedura de la araña",
            descripcion = "Peter Parker obtiene sus poderes tras ser mordido por una araña radiactiva durante una exposición científica.",
            etapa = "Origen",
            personajes = listOf("Peter Parker"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 2,
            titulo = "Muerte del Tío Ben",
            descripcion = "La muerte de Ben Parker marca el nacimiento moral de Spider-Man y da origen a la frase \"un gran poder conlleva una gran responsabilidad\".",
            etapa = "Origen",
            personajes = listOf("Peter Parker", "Ben Parker"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 3,
            titulo = "Primer enfrentamiento con Green Goblin",
            descripcion = "Norman Osborn se convierte en Green Goblin, uno de los enemigos más peligrosos y personales de Spider-Man.",
            etapa = "Primeros enemigos",
            personajes = listOf("Peter Parker", "Norman Osborn", "Harry Osborn"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 4,
            titulo = "Muerte de Gwen Stacy",
            descripcion = "Gwen Stacy muere durante un enfrentamiento entre Spider-Man y Green Goblin, convirtiéndose en uno de los momentos más trágicos de la historia del héroe.",
            etapa = "Tragedia clásica",
            personajes = listOf("Peter Parker", "Gwen Stacy", "Norman Osborn"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 5,
            titulo = "Aparición de Mary Jane Watson",
            descripcion = "Mary Jane se convierte en una de las figuras más importantes en la vida personal de Peter Parker.",
            etapa = "Vida personal",
            personajes = listOf("Peter Parker", "Mary Jane Watson"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 6,
            titulo = "Traje negro simbionte",
            descripcion = "Spider-Man obtiene un traje alienígena negro que aumenta sus habilidades, pero también influye en su comportamiento.",
            etapa = "Secret Wars",
            personajes = listOf("Peter Parker", "Simbionte", "Eddie Brock"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 7,
            titulo = "Nacimiento de Venom",
            descripcion = "El simbionte se une a Eddie Brock y nace Venom, uno de los enemigos y antihéroes más famosos del universo Spider-Man.",
            etapa = "Simbiontes",
            personajes = listOf("Eddie Brock", "Venom", "Peter Parker"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 8,
            titulo = "Aparición de Carnage",
            descripcion = "El simbionte Carnage se une a Cletus Kasady, creando una amenaza mucho más violenta e inestable que Venom.",
            etapa = "Simbiontes",
            personajes = listOf("Cletus Kasady", "Carnage", "Venom", "Spider-Man"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 9,
            titulo = "Saga del Clon",
            descripcion = "Peter Parker descubre la existencia de clones relacionados con él, entre ellos Ben Reilly, también conocido como Scarlet Spider.",
            etapa = "Clones",
            personajes = listOf("Peter Parker", "Ben Reilly", "Kaine"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 10,
            titulo = "Civil War e identidad revelada",
            descripcion = "Durante Civil War, Peter Parker revela públicamente su identidad como Spider-Man, cambiando drásticamente su vida.",
            etapa = "Civil War",
            personajes = listOf("Peter Parker", "Tony Stark", "Mary Jane", "Tía May"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 11,
            titulo = "One More Day",
            descripcion = "Peter Parker realiza un pacto que altera su vida personal y su relación con Mary Jane.",
            etapa = "Cambio de continuidad",
            personajes = listOf("Peter Parker", "Mary Jane", "Mephisto"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 12,
            titulo = "Aparición de Miles Morales",
            descripcion = "Miles Morales hereda el legado de Spider-Man en el universo Ultimate tras la muerte de Peter Parker de ese universo.",
            etapa = "Ultimate Spider-Man",
            personajes = listOf("Miles Morales", "Peter Parker", "Aaron Davis"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 13,
            titulo = "Spider-Verse",
            descripcion = "Diferentes versiones de Spider-Man de múltiples universos se unen para enfrentarse a una amenaza multiversal.",
            etapa = "Multiverso",
            personajes = listOf("Peter Parker", "Miles Morales", "Gwen Stacy", "Miguel O'Hara"),
            tipo = "multiverso"
        ),
        TimelineEvent(
            id = 14,
            titulo = "Superior Spider-Man",
            descripcion = "Otto Octavius intercambia su mente con la de Peter Parker y decide convertirse en una versión más agresiva y metódica de Spider-Man.",
            etapa = "Superior Spider-Man",
            personajes = listOf("Otto Octavius", "Peter Parker"),
            tipo = "comic"
        ),
        TimelineEvent(
            id = 15,
            titulo = "Spider-Man PS4",
            descripcion = "Peter Parker aparece como un héroe experimentado en el videojuego de Insomniac Games.",
            etapa = "Videojuegos",
            personajes = listOf("Peter Parker", "Miles Morales", "Mary Jane", "Doctor Octopus"),
            tipo = "juego"
        ),
        TimelineEvent(
            id = 16,
            titulo = "Spider-Man: Into the Spider-Verse",
            descripcion = "Miles Morales protagoniza una historia animada donde conoce a diferentes versiones de Spider-Man de otros universos.",
            etapa = "Cine animado",
            personajes = listOf("Miles Morales", "Peter B. Parker", "Gwen Stacy", "Spider-Man Noir", "Spider-Ham"),
            tipo = "pelicula"
        ),
        TimelineEvent(
            id = 17,
            titulo = "Spider-Man: No Way Home",
            descripcion = "Peter Parker se enfrenta a villanos de otros universos y conoce a otras versiones de sí mismo.",
            etapa = "Multiverso cinematográfico",
            personajes = listOf("Peter Parker", "Doctor Strange", "Green Goblin", "Doctor Octopus", "Electro"),
            tipo = "pelicula"
        ),
        TimelineEvent(
            id = 18,
            titulo = "Spider-Man 2 de Insomniac",
            descripcion = "Peter Parker y Miles Morales se enfrentan a nuevas amenazas, incluyendo Kraven y Venom.",
            etapa = "Videojuegos",
            personajes = listOf("Peter Parker", "Miles Morales", "Venom", "Kraven"),
            tipo = "juego"
        )
    )
}
