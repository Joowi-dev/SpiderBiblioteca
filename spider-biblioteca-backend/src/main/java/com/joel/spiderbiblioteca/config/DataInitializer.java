package com.joel.spiderbiblioteca.config;

import com.joel.spiderbiblioteca.model.EventoLinea;
import com.joel.spiderbiblioteca.model.Personaje;
import com.joel.spiderbiblioteca.model.Traje;
import com.joel.spiderbiblioteca.model.Universo;
import com.joel.spiderbiblioteca.repository.EventoLineaRepository;
import com.joel.spiderbiblioteca.repository.PersonajeRepository;
import com.joel.spiderbiblioteca.repository.TrajeRepository;
import com.joel.spiderbiblioteca.repository.UniversoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarDatosIniciales(PersonajeRepository repo,
                                           EventoLineaRepository eventoRepo,
                                           UniversoRepository universoRepo,
                                           TrajeRepository trajeRepo) {
        return args -> {
            if (repo.count() > 0) return; // Solo inserta si la BD está vacía

            repo.saveAll(List.of(

                // ── HÉROES ────────────────────────────────────────────────────────

                new Personaje(null,
                    "Spider-Man", "Peter Parker",
                    "Spidey, El Trepamuros, El Hombre Araña, El Arácnido",
                    "Fuerza sobrehumana, agilidad, sentido de araña, trepar por superficies, lanzar telarañas con lanzatelarañas mecánicos, reflejos mejorados",
                    "Estudiante de Queens mordido por una araña radiactiva. Tras la muerte de su tío Ben aprende que con un gran poder viene una gran responsabilidad. Es el Spider-Man más icónico de todos los universos.",
                    "Amazing Fantasy #15 (1962)",
                    "heroe",
                    "https://dummyimage.com/300x400/CC0000/ffffff.jpg&text=Spider-Man"),

                new Personaje(null,
                    "Miles Morales", "Miles Morales",
                    "Spider-Man (Ultimate), Kid Arachnid",
                    "Fuerza sobrehumana, agilidad, sentido de araña, camuflaje invisible, veneno bioeléctrico (Venom Blast), trepar superficies",
                    "Adolescente afrolatino de Brooklyn que hereda el manto de Spider-Man en su universo. Con poderes únicos adicionales a los clásicos, Miles representa a una nueva generación de héroes arácnidos.",
                    "Ultimate Fallout #4 (2011)",
                    "heroe",
                    "https://dummyimage.com/300x400/CC0000/ffffff.jpg&text=Miles+Morales"),

                new Personaje(null,
                    "Spider-Gwen", "Gwen Stacy",
                    "Ghost-Spider, Spider-Woman de la Tierra-65",
                    "Fuerza sobrehumana, agilidad, sentido de araña, lanzar telarañas orgánicas, reflejos mejorados, acrobacia avanzada",
                    "En su universo alternativo (Tierra-65) fue Gwen quien recibió los poderes de araña. Tras la muerte de su Peter Parker, se convierte en heroína. Su traje blanco y rosa es uno de los más populares del multiverso.",
                    "Edge of Spider-Verse #2 (2014)",
                    "heroe",
                    "https://dummyimage.com/300x400/CC0000/ffffff.jpg&text=Spider-Gwen"),

                new Personaje(null,
                    "Spider-Man 2099", "Miguel O'Hara",
                    "El Spider-Man del Futuro",
                    "Garras retráctiles, fuerza y agilidad sobrehumanas, telarañas orgánicas, visión ultravioleta, toxina en los colmillos, vuelo con capa deslizante",
                    "En el año 2099 el científico Miguel O'Hara recibe poderes de araña accidentalmente. Versión futurista de Spider-Man en una Nueva York corporativa y distópica. Fundador de la Spider-Society.",
                    "The Amazing Spider-Man #365 (1992)",
                    "heroe",
                    "https://dummyimage.com/300x400/003F8A/ffffff.jpg&text=2099"),

                new Personaje(null,
                    "Silk", "Cindy Moon",
                    "Silk, Araña de Seda",
                    "Telarañas orgánicas de sus dedos, sentido de araña aumentado, fuerza y agilidad sobrehumanas, memoria fotográfica",
                    "Cindy Moon fue mordida por la misma araña radiactiva que Peter Parker. Estuvo encerrada en un búnker durante años. Sus telarañas son orgánicas y su sentido de araña es más poderoso que el de Peter.",
                    "The Amazing Spider-Man #1 (2014)",
                    "heroe",
                    "https://dummyimage.com/300x400/003F8A/ffffff.jpg&text=Silk"),

                // ── VILLANOS ──────────────────────────────────────────────────────

                new Personaje(null,
                    "Green Goblin", "Norman Osborn",
                    "El Duende Verde, Goblin, El Padre de Harry",
                    "Fórmula Duende: fuerza, agilidad, velocidad sobrehumanas, regeneración acelerada, inteligencia potenciada. Equipamiento: planeador goblin, calabazas explosivas, rayos fantasma.",
                    "Empresario millonario y genio científico convertido en el villano más peligroso de Spider-Man. Responsable de la muerte de Gwen Stacy. Su obsesión con Peter Parker lo convierte en un enemigo implacable.",
                    "The Amazing Spider-Man #14 (1964)",
                    "villano",
                    "https://dummyimage.com/300x400/1a7a1a/ffffff.jpg&text=Green+Goblin"),

                new Personaje(null,
                    "Doctor Octopus", "Otto Octavius",
                    "Doc Ock, El Superior Spider-Man, El Maestro de las Artes Maestras",
                    "Cuatro tentáculos de adamantio controlados telepáticamente, genio científico mundial, en cuerpo de Peter Parker: todos los poderes de Spider-Man",
                    "Físico nuclear que quedó fusionado con cuatro brazos robóticos tras un accidente de laboratorio. Uno de los mayores genios del mundo y archienemigo de Spider-Man, llegó a ocupar su cuerpo durante meses.",
                    "The Amazing Spider-Man #3 (1963)",
                    "villano",
                    "https://dummyimage.com/300x400/8B0000/ffffff.jpg&text=Doc+Ock"),

                new Personaje(null,
                    "Electro", "Maxwell Dillon",
                    "Electro, El Maestro de la Electricidad",
                    "Generación y control de electricidad, volar a través de líneas eléctricas, disparar rayos eléctricos, absorber electricidad ambiental, pulso electromagnético",
                    "Electricista alcanzado por un rayo mientras reparaba cables de alta tensión, Max Dillon adquirió el poder de generar y controlar la electricidad. Su codicia lo convirtió en uno de los villanos más poderosos del Sexteto Siniestro.",
                    "The Amazing Spider-Man #9 (1964)",
                    "villano",
                    "https://dummyimage.com/300x400/b8a000/ffffff.jpg&text=Electro"),

                new Personaje(null,
                    "Vulture", "Adrian Toomes",
                    "El Buitre, El Viejo del Traje Alado",
                    "Traje de vuelo electromagnético, fuerza mejorada con el traje, absorber la vitalidad de otras personas (algunas versiones), garras cortantes",
                    "Ingeniero electromagnético traicionado por su socio, Adrian Toomes construyó un traje alado para vengarse. A pesar de su edad avanzada, es uno de los villanos más persistentes y letales de Spider-Man.",
                    "The Amazing Spider-Man #2 (1963)",
                    "villano",
                    "https://dummyimage.com/300x400/4A148C/ffffff.jpg&text=Vulture"),

                new Personaje(null,
                    "Mysterio", "Quentin Beck",
                    "El Maestro de las Ilusiones, Mysterio",
                    "Sin poderes reales. Maestro de efectos especiales, ilusiones holográficas, gas alucinógeno, traje con generador de niebla, experto en hipnosis y química",
                    "Ex especialista de efectos especiales de Hollywood que usó sus habilidades para convertirse en supervillano. Maestro del engaño y las ilusiones, capaz de hacer creer a Spider-Man que está loco.",
                    "The Amazing Spider-Man #13 (1964)",
                    "villano",
                    "https://dummyimage.com/300x400/1A0A3A/00ff88.jpg&text=Mysterio"),

                new Personaje(null,
                    "Carnage", "Cletus Kasady",
                    "Carnage, La Matanza, El Simbionte Rojo",
                    "Simbionte Carnage: fuerza sobrehumana extrema, forma su cuerpo en armas, garras, látigos, inmune a los debilitadores de Venom, no tiene punto débil conocido",
                    "Asesino en serie que se fusionó con la cría del simbionte Venom para crear una amenaza aún más letal. Cletus Kasady es puro caos personificado, sin motivo ni objetivo más allá de la destrucción.",
                    "The Amazing Spider-Man #361 (1992)",
                    "villano",
                    "https://dummyimage.com/300x400/8B0000/ffffff.jpg&text=Carnage"),

                new Personaje(null,
                    "Sandman", "Flint Marko",
                    "El Hombre de Arena, Sandman",
                    "Transformar su cuerpo en arena, control total de la arena a distancia, tamaño variable, regeneración total, crear tormentas de arena",
                    "Criminal de poca monta que cayó accidentalmente en un campo de pruebas nucleares. Su estructura molecular se fusionó con arena, dándole poderes extraordinarios. Ocasionalmente ha actuado como antihéroe.",
                    "The Amazing Spider-Man #4 (1963)",
                    "villano",
                    "https://dummyimage.com/300x400/C2A656/000000.jpg&text=Sandman"),

                new Personaje(null,
                    "Rhino", "Aleksei Sytsevich",
                    "El Rinoceronte, Rhino",
                    "Fuerza sobrehumana extrema, velocidad cargando, traje de rinoceronte prácticamente indestructible, resistencia al daño físico",
                    "Inmigrante ruso que aceptó ser sometido a un experimento para obtener poderes. Su traje de rinoceronte le da fuerza y resistencia extraordinarias. A veces ha mostrado su lado humano, luchando por una vida normal.",
                    "The Amazing Spider-Man #41 (1966)",
                    "villano",
                    "https://dummyimage.com/300x400/5C4A1E/ffffff.jpg&text=Rhino"),

                // ── ALIADOS ───────────────────────────────────────────────────────

                new Personaje(null,
                    "Mary Jane Watson", "Mary Jane Watson",
                    "MJ, La Chica de Peter, Tigre",
                    "Sin poderes sobrenaturales. Actriz y modelo profesional, gran valentía, inteligencia emocional excepcional, conoce la identidad de Spider-Man",
                    "El amor de la vida de Peter Parker. Su frase \"Face it, Tiger, you just hit the jackpot!\" es una de las más icónicas del cómic. MJ es el pilar emocional de Peter y conoce los sacrificios reales detrás de la máscara.",
                    "The Amazing Spider-Man #25 (1965)",
                    "aliado",
                    "https://dummyimage.com/300x400/C62828/ffffff.jpg&text=Mary+Jane"),

                new Personaje(null,
                    "Aunt May", "May Parker",
                    "Tía May, La Tía de Peter",
                    "Sin poderes. Fuerza moral excepcional, sabiduría, bondad incondicional, capacidad de sacrificio",
                    "La tía y figura materna de Peter Parker. Cría a Peter sola tras la muerte de su esposo Ben. Representa los valores morales que hacen de Spider-Man un verdadero héroe: responsabilidad, sacrificio y compasión.",
                    "Amazing Fantasy #15 (1962)",
                    "aliado",
                    "https://dummyimage.com/300x400/1B5E20/ffffff.jpg&text=Aunt+May"),

                new Personaje(null,
                    "Harry Osborn", "Harry Osborn",
                    "El Duende Americano, Harry",
                    "Como Duende: fórmula goblin, planeador, calabazas explosivas. Sin traje: ninguno",
                    "Mejor amigo de Peter Parker y hijo de Norman Osborn (Green Goblin). Su lealtad a Peter y a su padre lo convierte en un personaje trágico que oscila entre el bien y el mal. En muchas versiones muere como héroe.",
                    "The Amazing Spider-Man #31 (1965)",
                    "aliado",
                    "https://dummyimage.com/300x400/1B5E20/ffffff.jpg&text=Harry+Osborn"),

                new Personaje(null,
                    "J. Jonah Jameson", "J. Jonah Jameson",
                    "El Jefe, JJJ, El Hombre del Bigote",
                    "Sin poderes. Periodista y editor brillante, gran tenacidad, recursos económicos considerables",
                    "Director del Daily Bugle y mayor crítico público de Spider-Man. A pesar de su obsesión con destruir la reputación del Trepamuros, en el fondo Jameson tiene un código moral férreo y a veces ha sido aliado involuntario de Peter.",
                    "The Amazing Spider-Man #1 (1963)",
                    "aliado",
                    "https://dummyimage.com/300x400/2C3E50/ffffff.jpg&text=J.J.J."),

                // ── ANTIHÉROES ────────────────────────────────────────────────────

                new Personaje(null,
                    "Venom", "Eddie Brock",
                    "El Simbionte, Lethal Protector, We Are Venom",
                    "Fuerza y agilidad sobrehumanas, mimetismo de apariencia, lengua extensible, no activa el sentido de araña de Spider-Man, generar armas del simbionte, sanar heridas",
                    "Reportero caído que se fusionó con el simbionte alienígena. Enemigo acérrimo de Spider-Man reconvertido en protector letal. \"Somos Venom\" es uno de los gritos más icónicos del universo Marvel.",
                    "The Amazing Spider-Man #300 (1988)",
                    "antiheroe",
                    "https://dummyimage.com/300x400/1A1A1A/ffffff.jpg&text=Venom"),

                new Personaje(null,
                    "Black Cat", "Felicia Hardy",
                    "La Gata Negra, El Gato",
                    "Agilidad y reflejos sobrehumanos, habilidades acrobáticas extremas, garras retráctiles, inducir mala suerte en los enemigos (versiones con poderes)",
                    "Ladrona de guante blanco con un complejo código de honor. Su relación romántica con Spider-Man la sitúa permanentemente entre el bien y el mal. Felicia roba por el placer y la emoción, no por maldad.",
                    "The Amazing Spider-Man #194 (1979)",
                    "antiheroe",
                    "https://dummyimage.com/300x400/212121/ffffff.jpg&text=Black+Cat"),

                new Personaje(null,
                    "Morbius", "Michael Morbius",
                    "El Vampiro Viviente, Morbius",
                    "Vuelo, fuerza sobrehumana, ecolocalización, curación acelerada, necesidad de sangre para sobrevivir, hipnosis, resistencia mejorada",
                    "Bioquímico ganador del Nobel que intentó curar su enfermedad rara con un experimento que lo convirtió en vampiro viviente. Lucha constantemente contra sus instintos violentos mientras intenta hacer el bien.",
                    "The Amazing Spider-Man #101 (1971)",
                    "antiheroe",
                    "https://dummyimage.com/300x400/4A148C/ffffff.jpg&text=Morbius"),

                new Personaje(null,
                    "Scorpion", "Mac Gargan",
                    "El Escorpión, Scorpion",
                    "Traje exoesqueleto de escorpión: cola con veneno, fuerza sobrehumana, escalar paredes, cola como arma contundente y proyectil",
                    "Investigador privado que fue sometido a un experimento para crear a alguien capaz de derrotar a Spider-Man. El proceso lo volvió inestable mentalmente. Más tarde se fusionó con el simbionte Venom temporalmente.",
                    "The Amazing Spider-Man #20 (1965)",
                    "antiheroe",
                    "https://dummyimage.com/300x400/1A3A1A/ffff00.jpg&text=Scorpion")
            ));

            System.out.println("✅ Datos iniciales insertados: 20 personajes Spider-Man");

            if (eventoRepo.count() == 0) {
                eventoRepo.saveAll(List.of(
                    new EventoLinea(null, "Mordedura de la araña",
                        "Peter Parker obtiene sus poderes tras ser mordido por una araña radiactiva durante una exposición científica.",
                        "Origen", "Peter Parker", "comic", null),
                    new EventoLinea(null, "Muerte del Tío Ben",
                        "La muerte de Ben Parker marca el nacimiento moral de Spider-Man y da origen a la frase 'un gran poder conlleva una gran responsabilidad'.",
                        "Origen", "Peter Parker,Ben Parker", "comic", null),
                    new EventoLinea(null, "Primer enfrentamiento con Green Goblin",
                        "Norman Osborn se convierte en Green Goblin, uno de los enemigos más peligrosos y personales de Spider-Man.",
                        "Primeros enemigos", "Peter Parker,Norman Osborn,Harry Osborn", "comic", null),
                    new EventoLinea(null, "Muerte de Gwen Stacy",
                        "Gwen Stacy muere durante un enfrentamiento entre Spider-Man y Green Goblin, convirtiéndose en uno de los momentos más trágicos de la historia del héroe.",
                        "Tragedia clásica", "Peter Parker,Gwen Stacy,Norman Osborn", "comic", null),
                    new EventoLinea(null, "Aparición de Mary Jane Watson",
                        "Mary Jane se convierte en una de las figuras más importantes en la vida personal de Peter Parker.",
                        "Vida personal", "Peter Parker,Mary Jane Watson", "comic", null),
                    new EventoLinea(null, "Traje negro simbionte",
                        "Spider-Man obtiene un traje alienígena negro que aumenta sus habilidades, pero también influye en su comportamiento.",
                        "Secret Wars", "Peter Parker,Simbionte,Eddie Brock", "comic", null),
                    new EventoLinea(null, "Nacimiento de Venom",
                        "El simbionte se une a Eddie Brock y nace Venom, uno de los enemigos y antihéroes más famosos del universo Spider-Man.",
                        "Simbiontes", "Eddie Brock,Venom,Peter Parker", "comic", null),
                    new EventoLinea(null, "Aparición de Carnage",
                        "El simbionte Carnage se une a Cletus Kasady, creando una amenaza mucho más violenta e inestable que Venom.",
                        "Simbiontes", "Cletus Kasady,Carnage,Venom,Spider-Man", "comic", null),
                    new EventoLinea(null, "Saga del Clon",
                        "Peter Parker descubre la existencia de clones relacionados con él, entre ellos Ben Reilly, también conocido como Scarlet Spider.",
                        "Clones", "Peter Parker,Ben Reilly,Kaine", "comic", null),
                    new EventoLinea(null, "Civil War e identidad revelada",
                        "Durante Civil War, Peter Parker revela públicamente su identidad como Spider-Man, cambiando drásticamente su vida.",
                        "Civil War", "Peter Parker,Tony Stark,Mary Jane,Tía May", "comic", null),
                    new EventoLinea(null, "One More Day",
                        "Peter Parker realiza un pacto que altera su vida personal y su relación con Mary Jane.",
                        "Cambio de continuidad", "Peter Parker,Mary Jane,Mephisto", "comic", null),
                    new EventoLinea(null, "Aparición de Miles Morales",
                        "Miles Morales hereda el legado de Spider-Man en el universo Ultimate tras la muerte de Peter Parker de ese universo.",
                        "Ultimate Spider-Man", "Miles Morales,Peter Parker,Aaron Davis", "comic", null),
                    new EventoLinea(null, "Spider-Verse",
                        "Diferentes versiones de Spider-Man de múltiples universos se unen para enfrentarse a una amenaza multiversal.",
                        "Multiverso", "Peter Parker,Miles Morales,Gwen Stacy,Miguel O'Hara", "multiverso", null),
                    new EventoLinea(null, "Superior Spider-Man",
                        "Otto Octavius intercambia su mente con la de Peter Parker y decide convertirse en una versión más agresiva y metódica de Spider-Man.",
                        "Superior Spider-Man", "Otto Octavius,Peter Parker", "comic", null),
                    new EventoLinea(null, "Spider-Man PS4",
                        "Peter Parker aparece como un héroe experimentado en el videojuego de Insomniac Games.",
                        "Videojuegos", "Peter Parker,Miles Morales,Mary Jane,Doctor Octopus", "juego", null),
                    new EventoLinea(null, "Spider-Man: Into the Spider-Verse",
                        "Miles Morales protagoniza una historia animada donde conoce a diferentes versiones de Spider-Man de otros universos.",
                        "Cine animado", "Miles Morales,Peter B. Parker,Gwen Stacy,Spider-Man Noir,Spider-Ham", "pelicula", null),
                    new EventoLinea(null, "Spider-Man: No Way Home",
                        "Peter Parker se enfrenta a villanos de otros universos y conoce a otras versiones de sí mismo.",
                        "Multiverso cinematográfico", "Peter Parker,Doctor Strange,Green Goblin,Doctor Octopus,Electro", "pelicula", null),
                    new EventoLinea(null, "Spider-Man 2 de Insomniac",
                        "Peter Parker y Miles Morales se enfrentan a nuevas amenazas, incluyendo Kraven y Venom.",
                        "Videojuegos", "Peter Parker,Miles Morales,Venom,Kraven", "juego", null)
                ));
                System.out.println("✅ Datos iniciales insertados: 18 eventos línea temporal");
            }

            if (universoRepo.count() == 0) {
                universoRepo.saveAll(List.of(
                    new Universo(null, "Tierra-616", "Earth-616", "Peter Parker",
                        "Universo principal de Marvel Comics. Es la versión clásica donde Peter Parker se convierte en Spider-Man.",
                        "Peter Parker,Mary Jane Watson,Tía May,Green Goblin,Doctor Octopus,Venom", "Clásico", null),
                    new Universo(null, "Ultimate Universe", "Earth-1610", "Miles Morales",
                        "Universo moderno donde Miles Morales toma el legado de Spider-Man después de la muerte de Peter Parker.",
                        "Miles Morales,Peter Parker Ultimate,Aaron Davis,Gwen Stacy,Ganke Lee", "Moderno", null),
                    new Universo(null, "Spider-Gwen", "Earth-65", "Gwen Stacy",
                        "Universo alternativo donde Gwen Stacy recibe los poderes arácnidos en lugar de Peter Parker.",
                        "Gwen Stacy,Peter Parker,George Stacy,Matt Murdock", "Alternativo", null),
                    new Universo(null, "Spider-Man Noir", "Earth-90214", "Peter Parker Noir",
                        "Versión oscura y detectivesca ambientada en los años 30, con una estética de cine negro.",
                        "Peter Parker Noir,Kingpin,Tía May,Felicia Hardy", "Noir", null),
                    new Universo(null, "Spider-Man 2099", "Earth-928", "Miguel O'Hara",
                        "Futuro cyberpunk donde Miguel O'Hara obtiene poderes arácnidos mediante alteraciones genéticas.",
                        "Miguel O'Hara,Lyla,Tyler Stone,Gabriel O'Hara", "Futurista", null),
                    new Universo(null, "Spider-Punk", "Earth-138", "Hobie Brown",
                        "Universo rebelde donde Spider-Man es un símbolo de resistencia contra sistemas autoritarios.",
                        "Hobie Brown,Captain Anarchy,Kang,Osborn", "Punk", null),
                    new Universo(null, "Spider-Ham", "Earth-8311", "Peter Porker",
                        "Universo paródico habitado por animales antropomórficos, donde Peter Porker se convierte en Spider-Ham.",
                        "Peter Porker,Mary Jane Waterbuffalo,Ducktor Doom", "Humorístico", null),
                    new Universo(null, "Peni Parker", "Earth-14512", "Peni Parker",
                        "Universo futurista de estilo anime donde Peni pilota el robot SP//dr junto a una araña radiactiva.",
                        "Peni Parker,SP//dr,Tía May,Addy Brock", "Anime / Mecha", null),
                    new Universo(null, "Superior Spider-Man", "Earth-616 (etapa Superior)", "Otto Octavius",
                        "Etapa donde Otto Octavius ocupa el cuerpo de Peter Parker y actúa como Spider-Man con métodos más extremos.",
                        "Otto Octavius,Peter Parker,Anna Maria Marconi,J. Jonah Jameson", "Oscuro / Estratégico", null),
                    new Universo(null, "Scarlet Spider", "Earth-616 (clones)", "Ben Reilly / Kaine Parker",
                        "Historia relacionada con los clones de Peter Parker, especialmente Ben Reilly y Kaine.",
                        "Ben Reilly,Kaine Parker,Peter Parker,Chacal", "Clones", null),
                    new Universo(null, "Marvel Cinematic Universe", "Earth-199999", "Peter Parker",
                        "Universo cinematográfico donde Peter Parker es interpretado como un joven héroe relacionado con los Vengadores.",
                        "Peter Parker,Tony Stark,Ned Leeds,MJ,Doctor Strange", "Cinematográfico", null),
                    new Universo(null, "Insomniac Universe", "Earth-1048", "Peter Parker y Miles Morales",
                        "Universo de los videojuegos de Insomniac Games donde Peter es un Spider-Man experimentado.",
                        "Peter Parker,Miles Morales,Mary Jane,Rio Morales,Venom,Kraven", "Videojuego moderno", null)
                ));
                System.out.println("✅ Datos iniciales insertados: 12 universos alternativos");
            }

            if (trajeRepo.count() == 0) {
                trajeRepo.saveAll(List.of(
                    new Traje(null, "Traje clásico", "Peter Parker", "Amazing Fantasy #15",
                        "El traje rojo y azul original de Spider-Man, uno de los diseños más reconocibles de los cómics.",
                        "No tiene habilidades propias, pero permite libertad de movimiento y oculta la identidad de Peter.",
                        10, null),
                    new Traje(null, "Traje negro simbionte", "Peter Parker / Eddie Brock", "Secret Wars",
                        "Traje alienígena que aumenta la fuerza de su portador y más adelante da origen a Venom.",
                        "Fuerza aumentada, cambio de forma, camuflaje, creación de telarañas orgánicas.",
                        10, null),
                    new Traje(null, "Iron Spider", "Peter Parker", "Civil War",
                        "Traje tecnológico diseñado por Tony Stark con mejoras defensivas y ofensivas.",
                        "Brazos mecánicos, resistencia mejorada, sensores, tecnología avanzada.",
                        9, null),
                    new Traje(null, "Advanced Suit", "Peter Parker", "Marvel's Spider-Man PS4",
                        "Traje blanco, rojo y azul creado por Insomniac Games para representar a un Spider-Man más experimentado.",
                        "Tecnología avanzada, resistencia mejorada, gadgets integrados.",
                        9, null),
                    new Traje(null, "Traje de Miles Morales", "Miles Morales", "Ultimate Comics",
                        "Traje negro y rojo usado por Miles Morales como símbolo de su propia identidad como Spider-Man.",
                        "Camuflaje, bioelectricidad, agilidad aumentada.",
                        10, null),
                    new Traje(null, "Spider-Man 2099", "Miguel O'Hara", "Spider-Man 2099 #1",
                        "Traje futurista azul y rojo usado por Miguel O'Hara en el año 2099.",
                        "Planeo, garras, resistencia, visión mejorada.",
                        8, null),
                    new Traje(null, "Spider-Gwen Suit", "Gwen Stacy", "Edge of Spider-Verse #2",
                        "Traje blanco, negro y rosa usado por Gwen Stacy en su universo alternativo.",
                        "Agilidad arácnida, sentido arácnido, diseño ligero.",
                        9, null),
                    new Traje(null, "Spider-Punk Suit", "Hobie Brown", "Spider-Verse",
                        "Traje punk con chaqueta, pinchos y estética rebelde.",
                        "Agilidad arácnida, estilo intimidante, resistencia urbana.",
                        8, null),
                    new Traje(null, "Scarlet Spider Suit", "Ben Reilly", "Saga del Clon",
                        "Traje azul y rojo con sudadera sin mangas, usado por Ben Reilly como Scarlet Spider.",
                        "Agilidad, fuerza arácnida, lanzaredes.",
                        8, null),
                    new Traje(null, "Traje Noir", "Peter Parker Noir", "Spider-Man Noir #1",
                        "Traje oscuro con gabardina, máscara negra y estética detectivesca.",
                        "Sigilo, combate cuerpo a cuerpo, uso de entorno oscuro.",
                        8, null),
                    new Traje(null, "Homemade Suit", "Peter Parker", "Spider-Man: Homecoming",
                        "Traje casero creado por Peter antes de recibir tecnología avanzada.",
                        "Lanzatelarañas básicos, diseño ligero, movilidad.",
                        7, null),
                    new Traje(null, "Integrated Suit", "Peter Parker", "Spider-Man: No Way Home",
                        "Traje que combina tecnología Stark con elementos mágicos y diseño moderno.",
                        "Nanotecnología, resistencia mejorada, compatibilidad con tecnología Stark.",
                        8, null),
                    new Traje(null, "Anti-Ock Suit", "Peter Parker", "Marvel's Spider-Man PS4",
                        "Traje creado para enfrentarse directamente a Doctor Octopus.",
                        "Protección avanzada, recarga de gadgets, resistencia tecnológica.",
                        8, null),
                    new Traje(null, "Velocity Suit", "Peter Parker", "Marvel's Spider-Man PS4",
                        "Traje diseñado para aumentar la velocidad y movilidad de Spider-Man.",
                        "Velocidad aumentada, reflejos mejorados, diseño aerodinámico.",
                        7, null),
                    new Traje(null, "Stealth Suit", "Peter Parker", "Spider-Man: Far From Home",
                        "Traje negro de sigilo usado en misiones encubiertas.",
                        "Sigilo, movilidad, ocultación visual.",
                        7, null),
                    new Traje(null, "Cosmic Spider-Man Suit", "Peter Parker", "Acts of Vengeance",
                        "Traje asociado al poder cósmico conocido como Captain Universe.",
                        "Poder cósmico, fuerza extrema, energía cósmica, vuelo.",
                        9, null),
                    new Traje(null, "Future Foundation Suit", "Peter Parker", "Fantastic Four / Future Foundation",
                        "Traje blanco y negro usado cuando Spider-Man se une a la Future Foundation.",
                        "Material inestable, cambio de apariencia, tecnología avanzada.",
                        8, null),
                    new Traje(null, "Big Time Suit", "Peter Parker", "Amazing Spider-Man #648",
                        "Traje negro y verde creado por Peter con tecnología avanzada.",
                        "Modo sigilo, luces integradas, resistencia mejorada.",
                        8, null)
                ));
                System.out.println("✅ Datos iniciales insertados: 18 trajes");
            }
        };
    }
}
