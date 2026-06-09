package com.joel.spiderbiblioteca.data.seed

import com.joel.spiderbiblioteca.data.model.PreguntaQuiz

val PREGUNTAS_QUIZ = listOf(
    // ─── PERSONAJES ───
    PreguntaQuiz(
        enunciado = "¿Cuál es el nombre real de Peter Parker?",
        opciones = listOf("Miles Morales", "Ben Reilly", "Peter Parker", "Kaine Parker"),
        correcta = 2, categoria = "personajes", dificultad = "facil",
        explicacion = "Peter Benjamin Parker es el nombre completo del Spider-Man original, creado por Stan Lee y Steve Ditko en 1962."
    ),
    PreguntaQuiz(
        enunciado = "¿En qué ciudad protege Spider-Man a los ciudadanos?",
        opciones = listOf("Chicago", "Gotham City", "Metrópolis", "Nueva York"),
        correcta = 3, categoria = "personajes", dificultad = "facil",
        explicacion = "Spider-Man protege principalmente el barrio de Queens en Nueva York, donde creció Peter Parker."
    ),
    PreguntaQuiz(
        enunciado = "¿Cómo se llama la tía de Peter Parker?",
        opciones = listOf("Tía Anna", "Tía Martha", "Tía May", "Tía Sarah"),
        correcta = 2, categoria = "personajes", dificultad = "facil",
        explicacion = "May Parker es la tía y figura materna de Peter tras la muerte de sus padres. Su bienestar es uno de los motivos por los que Peter sigue siendo Spider-Man."
    ),
    PreguntaQuiz(
        enunciado = "¿Cuál es el nombre del periódico donde trabaja Peter Parker?",
        opciones = listOf("The Daily Planet", "The New York Times", "The Daily Globe", "The Daily Bugle"),
        correcta = 3, categoria = "personajes", dificultad = "facil",
        explicacion = "El Daily Bugle, dirigido por J. Jonah Jameson, es famoso por su campaña mediática contra Spider-Man a pesar de que Peter Parker les vende sus propias fotos."
    ),
    PreguntaQuiz(
        enunciado = "¿Quién es el director del Daily Bugle?",
        opciones = listOf("J. Jonah Jameson", "Norman Osborn", "Harry Osborn", "Otto Octavius"),
        correcta = 0, categoria = "personajes", dificultad = "facil",
        explicacion = "J. Jonah Jameson es el director del Daily Bugle y el mayor crítico mediático de Spider-Man, a quien considera un peligro para la ciudad."
    ),
    PreguntaQuiz(
        enunciado = "¿En qué barrio de Nueva York creció Peter Parker?",
        opciones = listOf("Manhattan", "Brooklyn", "Queens", "El Bronx"),
        correcta = 2, categoria = "personajes", dificultad = "facil",
        explicacion = "Peter Parker creció en Forest Hills, Queens, con sus tíos Ben y May Parker."
    ),
    PreguntaQuiz(
        enunciado = "¿Cuál es el lema asociado a Spider-Man sobre el poder y la responsabilidad?",
        opciones = listOf("El poder lo es todo", "De quien más tiene, más se puede tomar", "Un gran poder conlleva una gran responsabilidad", "Con grandes poderes vienen grandes peligros"),
        correcta = 2, categoria = "personajes", dificultad = "facil",
        explicacion = "Esta frase, asociada al tío Ben, es el principio moral central de Spider-Man. Le recuerda que sus poderes exigen que los use para proteger a los demás."
    ),
    // ─── PODERES ───
    PreguntaQuiz(
        enunciado = "¿Qué tipo de criatura mordió a Peter Parker y le dio sus poderes?",
        opciones = listOf("Una araña radiactiva", "Una araña mutante", "Una araña alienígena", "Una araña modificada genéticamente"),
        correcta = 0, categoria = "poderes", dificultad = "facil",
        explicacion = "Una araña radiactiva mordió a Peter Parker durante una visita escolar a un laboratorio, transfiriéndole poderes sobrehumanos proporcionales a los de una araña."
    ),
    PreguntaQuiz(
        enunciado = "¿Cuál es el poder exclusivo de Miles Morales que Peter Parker no posee?",
        opciones = listOf("Telarañas orgánicas", "Sentido de araña", "Venom Blast bioeléctrico y camuflaje", "Fuerza sobrehumana"),
        correcta = 2, categoria = "poderes", dificultad = "media",
        explicacion = "Miles puede generar descargas bioeléctricas llamadas Venom Blast y volverse invisible. Son poderes únicos que provienen de la araña diferente que lo mordió."
    ),
    PreguntaQuiz(
        enunciado = "¿Cuál es la gran diferencia del traje simbionte frente a los trajes normales de Spider-Man?",
        opciones = listOf("Es más resistente a balas", "Es un ser vivo que amplifica poderes pero intenta dominar al portador", "Permite volar sin telarañas", "Tiene IA incorporada"),
        correcta = 1, categoria = "poderes", dificultad = "media",
        explicacion = "El traje simbionte (Venom) es un ser alienígena vivo que aumenta los poderes del portador pero gradualmente intenta controlarlo, como le ocurrió a Peter Parker."
    ),
    PreguntaQuiz(
        enunciado = "¿Qué tecnología usan la mayoría de los trajes de Spider-Man en el MCU?",
        opciones = listOf("Nanotecnología de Stark", "Symbiote sintético", "Tejido de araña genéticamente modificado", "Armadura vibranium"),
        correcta = 0, categoria = "poderes", dificultad = "media",
        explicacion = "Tony Stark creó el traje Iron Spider y varios trajes para Peter usando nanotecnología, que se adapta y puede generar herramientas en tiempo real."
    ),
    // ─── ENEMIGOS ───
    PreguntaQuiz(
        enunciado = "¿Cuál es el nombre real de Green Goblin (el original)?",
        opciones = listOf("Harry Osborn", "Roderick Kingsley", "Phil Urich", "Norman Osborn"),
        correcta = 3, categoria = "enemigos", dificultad = "facil",
        explicacion = "Norman Osborn, empresario y científico, se convirtió en el Green Goblin original tras exponerse a su propio Suero Osborn que aumentó su fuerza pero también su locura."
    ),
    PreguntaQuiz(
        enunciado = "¿Qué es Venom?",
        opciones = listOf("Un robot alienígena", "Un clon de Spider-Man", "Un simbionte alienígena fusionado con Eddie Brock", "Un humano con traje tecnológico"),
        correcta = 2, categoria = "enemigos", dificultad = "media",
        explicacion = "El simbionte alienígena de Klyntar se fusionó con Eddie Brock tras ser rechazado por Peter Parker, creando a Venom, que comparte los poderes y recuerdos de Spider-Man."
    ),
    PreguntaQuiz(
        enunciado = "¿Cuál es el nombre real de Doctor Octopus?",
        opciones = listOf("Maxwell Dillon", "Flint Marko", "Adrian Toomes", "Otto Octavius"),
        correcta = 3, categoria = "enemigos", dificultad = "facil",
        explicacion = "Otto Octavius es un físico nuclear brillante cuyo accidente en el laboratorio fusionó cuatro brazos mecánicos a su cuerpo, convirtiéndolo en Doctor Octopus."
    ),
    PreguntaQuiz(
        enunciado = "¿Cuál es el nombre real de Mysterio?",
        opciones = listOf("Quentin Beck", "Mac Gargan", "Dmitri Smerdyakov", "Jackson Brice"),
        correcta = 0, categoria = "enemigos", dificultad = "media",
        explicacion = "Quentin Beck era un especialista de efectos especiales y actor frustrado en Hollywood que usó su conocimiento técnico para crear ilusiones y convertirse en el villano Mysterio."
    ),
    PreguntaQuiz(
        enunciado = "¿Cuál es el nombre real de Carnage?",
        opciones = listOf("Eddie Brock", "Mac Gargan", "Dylan Brock", "Cletus Kasady"),
        correcta = 3, categoria = "enemigos", dificultad = "facil",
        explicacion = "Cletus Kasady es un asesino en serie que se fusionó con la cría del simbionte de Venom, creando a Carnage, uno de los villanos más violentos del universo Spider-Man."
    ),
    PreguntaQuiz(
        enunciado = "¿Qué relación tiene el simbionte de Carnage con el de Venom?",
        opciones = listOf("Son el mismo simbionte dividido", "No tienen relación", "Son simbiontes hermanos", "Carnage es la cría del simbionte de Venom"),
        correcta = 3, categoria = "enemigos", dificultad = "media",
        explicacion = "El simbionte de Carnage es literalmente la cría (hijo) del simbionte de Venom, lo que los hace biológicamente familia aunque se odien."
    ),
    PreguntaQuiz(
        enunciado = "¿Cuál es el nombre real de Vulture?",
        opciones = listOf("Herman Schultz", "Adrian Toomes", "Spencer Smythe", "Aleksei Sytsevich"),
        correcta = 1, categoria = "enemigos", dificultad = "facil",
        explicacion = "Adrian Toomes es un ingeniero que creó un traje con alas mecánicas y tecnología electromagnética para convertirse en el Vulture, uno de los primeros villanos de Spider-Man."
    ),
    PreguntaQuiz(
        enunciado = "¿Cómo se llama el grupo de supervillanos que se unió para destruir a Spider-Man?",
        opciones = listOf("Los Cuatro Fantásticos", "Los Seis Siniestros", "La Hermandad", "Los Maestros del Mal"),
        correcta = 1, categoria = "enemigos", dificultad = "media",
        explicacion = "Los Seis Siniestros fue un grupo formado originalmente por Doctor Octopus e incluyó a villanos como Vulture, Electro, Mysterio, Sandman y el Buitre."
    ),
    // ─── ALIADOS ───
    PreguntaQuiz(
        enunciado = "¿Qué superhéroe fue mentor de Peter Parker en el MCU?",
        opciones = listOf("Captain America", "Thor", "Iron Man", "Hulk"),
        correcta = 2, categoria = "aliados", dificultad = "facil",
        explicacion = "Tony Stark reclutó a Spider-Man para el bando de los Vengadores y se convirtió en su mentor, proporcionándole tecnología avanzada y guía heroica."
    ),
    PreguntaQuiz(
        enunciado = "¿Quién es el mejor amigo de Miles Morales que conoce su identidad secreta?",
        opciones = listOf("Peter Parker", "Ganke Lee", "Jefferson Davis", "Aaron Davis"),
        correcta = 1, categoria = "aliados", dificultad = "media",
        explicacion = "Ganke Lee es el confidente y mejor amigo de Miles Morales desde antes de convertirse en Spider-Man. Su apoyo emocional es fundamental para Miles."
    ),
    PreguntaQuiz(
        enunciado = "¿Con qué superhéroe de Marvel comparte más aventuras Spider-Man en Nueva York?",
        opciones = listOf("Thor", "Daredevil", "Captain America", "Wolverine"),
        correcta = 1, categoria = "aliados", dificultad = "media",
        explicacion = "Daredevil y Spider-Man comparten el territorio de Nueva York y frecuentemente colaboran para proteger la ciudad, aunque con metodologías distintas."
    ),
    // ─── TRAJES ───
    PreguntaQuiz(
        enunciado = "¿Cómo se llama el traje negro y plateado de Spider-Man creado por Tony Stark?",
        opciones = listOf("Traje Iron Spider", "Traje Simbionte", "Traje Stealth", "Traje Oscuro"),
        correcta = 0, categoria = "trajes", dificultad = "media",
        explicacion = "El traje Iron Spider fue diseñado por Tony Stark para Peter Parker. Tiene brazos mecánicos adicionales y tecnología avanzada de Stark Industries."
    ),
    PreguntaQuiz(
        enunciado = "¿Qué color es el traje original de Spider-Man?",
        opciones = listOf("Azul y negro", "Rojo y azul", "Rojo y negro", "Negro y dorado"),
        correcta = 1, categoria = "trajes", dificultad = "facil",
        explicacion = "El traje clásico de Spider-Man es rojo y azul, con diseño de telaraña en la parte roja y el símbolo de araña en el pecho."
    ),
    PreguntaQuiz(
        enunciado = "¿Cómo obtuvo Peter Parker su primer traje simbionte negro?",
        opciones = listOf("Lo encontró en el espacio durante Secret Wars", "Se lo dio Venom", "Lo fabricó él mismo", "Se lo regaló Iron Man"),
        correcta = 0, categoria = "trajes", dificultad = "dificil",
        explicacion = "En Secret Wars, Spider-Man encontró el simbionte en el Beyonder's World. Creyendo que era un traje tecnológico avanzado, lo usó sin saber que era un ser vivo."
    ),
    PreguntaQuiz(
        enunciado = "¿Qué traje usa el Spider-Man del universo noir?",
        opciones = listOf("Rojo y azul clásico", "Completamente negro con gabardina y fedora", "Plateado futurista", "Transparente invisible"),
        correcta = 1, categoria = "trajes", dificultad = "media",
        explicacion = "El Spider-Man Noir usa un traje completamente negro con gabardina y sombrero fedora, acorde con la estética de los años 30 en que vive."
    ),
    // ─── UNIVERSOS ───
    PreguntaQuiz(
        enunciado = "¿Qué número de universo identifica al universo principal de Marvel (el de Peter Parker original)?",
        opciones = listOf("Earth-1610", "Earth-616", "Earth-1048", "Earth-928"),
        correcta = 1, categoria = "universos", dificultad = "media",
        explicacion = "Earth-616 es la designación oficial del universo principal de Marvel Comics donde vive Peter Parker, los Vengadores y la mayoría de personajes conocidos."
    ),
    PreguntaQuiz(
        enunciado = "¿En qué universo vive Miles Morales originalmente?",
        opciones = listOf("Earth-616", "Earth-928", "Earth-1610", "Earth-1048"),
        correcta = 2, categoria = "universos", dificultad = "media",
        explicacion = "Miles Morales fue creado en Earth-1610, el universo Ultimate Marvel. Tras las Secret Wars, fue trasladado al Earth-616 principal."
    ),
    PreguntaQuiz(
        enunciado = "¿Qué caracteriza al universo Earth-65 de Spider-Gwen?",
        opciones = listOf("Spider-Man es un villano", "Gwen Stacy es la Spider-Woman y Peter Parker murió", "No existe ningún Spider-Man", "Es un universo medieval"),
        correcta = 1, categoria = "universos", dificultad = "dificil",
        explicacion = "En Earth-65, Gwen Stacy fue mordida por la araña y se convirtió en Ghost-Spider, mientras que Peter Parker murió intentando imitar sus poderes sin ellos."
    ),
    PreguntaQuiz(
        enunciado = "¿Cómo se llama el evento de Marvel que reunió a todos los Spider-Men del multiverso?",
        opciones = listOf("Crisis Infinita", "Spider-Verse", "Infinity War", "House of M"),
        correcta = 1, categoria = "universos", dificultad = "media",
        explicacion = "Spider-Verse fue el evento de 2014 donde Morlun y los Herederos cazaron a cada Spider-Man del multiverso, obligando a todos a unirse para sobrevivir."
    ),
    // ─── CÓMICS ───
    PreguntaQuiz(
        enunciado = "¿En qué cómic apareció Spider-Man por primera vez?",
        opciones = listOf("The Amazing Spider-Man #1", "Amazing Fantasy #15", "Marvel Tales #1", "Spider-Man #1"),
        correcta = 1, categoria = "comics", dificultad = "facil",
        explicacion = "Spider-Man debutó en Amazing Fantasy #15 en agosto de 1962. Era el último número de la revista y su éxito fue tan grande que le dieron su propia serie."
    ),
    PreguntaQuiz(
        enunciado = "¿En qué año debutó Spider-Man en los cómics?",
        opciones = listOf("1958", "1960", "1962", "1965"),
        correcta = 2, categoria = "comics", dificultad = "media",
        explicacion = "Spider-Man debutó en agosto de 1962, en plena era de oro de Marvel Comics bajo la dirección editorial de Stan Lee y las ilustraciones de Steve Ditko."
    ),
    PreguntaQuiz(
        enunciado = "¿Quiénes crearon al personaje de Spider-Man?",
        opciones = listOf("Jack Kirby y Bob Kane", "Roy Thomas y John Romita", "Stan Lee y Jack Kirby", "Stan Lee y Steve Ditko"),
        correcta = 3, categoria = "comics", dificultad = "media",
        explicacion = "Stan Lee y Steve Ditko son los creadores oficiales de Spider-Man. Lee desarrolló la historia y Ditko diseñó el icónico traje y la identidad visual del personaje."
    ),
    PreguntaQuiz(
        enunciado = "¿En qué famosa saga de cómics Peter Parker descubrió que el simbionte era un ser vivo?",
        opciones = listOf("Civil War", "Secret Wars", "Maximum Carnage", "Clone Saga"),
        correcta = 1, categoria = "comics", dificultad = "dificil",
        explicacion = "En la saga original de Secret Wars (1984-1985), Spider-Man encontró el simbionte creyendo que era tecnología avanzada. Fue más tarde, con la ayuda del Señor Fantástico, que descubrió que era un ser vivo."
    ),
    // ─── PELÍCULAS ───
    PreguntaQuiz(
        enunciado = "¿Qué película protagonizada por Spider-Man ganó el Óscar a mejor película animada?",
        opciones = listOf("Spider-Man 2 (2004)", "Spider-Man: Homecoming (2017)", "Spider-Man: Un nuevo universo (2018)", "Spider-Man: Cruzando el Multiverso (2023)"),
        correcta = 2, categoria = "peliculas", dificultad = "media",
        explicacion = "Spider-Man: Un nuevo universo ganó el Óscar a mejor película animada en 2019, siendo la primera película de animación sobre Spider-Man en lograrlo."
    ),
    PreguntaQuiz(
        enunciado = "¿Qué actor interpretó a Spider-Man en la trilogía de Sam Raimi?",
        opciones = listOf("Andrew Garfield", "Tom Holland", "Tobey Maguire", "Shameik Moore"),
        correcta = 2, categoria = "peliculas", dificultad = "facil",
        explicacion = "Tobey Maguire interpretó a Peter Parker/Spider-Man en la trilogía dirigida por Sam Raimi (2002, 2004, 2007), considerada clásica por muchos fans."
    ),
    PreguntaQuiz(
        enunciado = "¿En qué película del MCU debuta Spider-Man por primera vez?",
        opciones = listOf("Iron Man 3", "Spider-Man: Homecoming", "Capitán América: Civil War", "Avengers: Age of Ultron"),
        correcta = 2, categoria = "peliculas", dificultad = "media",
        explicacion = "El Spider-Man de Tom Holland apareció por primera vez en Capitán América: Civil War (2016), reclutado por Iron Man antes de obtener su propia película."
    ),
    PreguntaQuiz(
        enunciado = "¿Qué película de Spider-Man fue la primera en superar los mil millones de dólares en taquilla?",
        opciones = listOf("Spider-Man (2002)", "Spider-Man 2 (2004)", "Spider-Man: Sin camino a casa (2021)", "The Amazing Spider-Man (2012)"),
        correcta = 2, categoria = "peliculas", dificultad = "dificil",
        explicacion = "Spider-Man: Sin camino a casa (2021) fue la primera película individual de Spider-Man en superar los mil millones de dólares, llegando a más de 1.900 millones."
    )
)
