-- ============================================================
-- Spider-Biblioteca - Script de Base de Datos
-- Ejecutar en phpMyAdmin o MySQL Workbench antes de arrancar Spring Boot
-- ============================================================

CREATE DATABASE IF NOT EXISTS spider_biblioteca
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE spider_biblioteca;

-- La tabla la crea automaticamente Hibernate (ddl-auto=update)
-- Este script solo crea la base de datos.

-- Si quieres crear la tabla manualmente, descomenta lo siguiente:
/*
CREATE TABLE IF NOT EXISTS personajes (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(100)  NOT NULL,
    nombre_real      VARCHAR(100)  NOT NULL,
    apodos           VARCHAR(255),
    poderes          TEXT,
    descripcion      TEXT,
    primera_aparicion VARCHAR(100),
    tipo             VARCHAR(50)   NOT NULL,
    imagen_url       VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/

-- ============================================================
-- Datos de ejemplo (opcionales, Spring Boot los crea desde personajes-ejemplo.json si lo insertas)
-- Para insertar desde aqui, descomenta:
/*
INSERT INTO personajes (nombre, nombre_real, apodos, poderes, descripcion, primera_aparicion, tipo, imagen_url) VALUES
('Spider-Man', 'Peter Parker',
 'Spidey, El Trepamuros, El Hombre Araña',
 'Fuerza sobrehumana, agilidad, sentido de araña, trepar por superficies, lanzar telarañas con lanzatelarañas mecánicos',
 'Peter Parker es un estudiante de secundaria de Queens, Nueva York, que tras ser mordido por una araña radiactiva adquirió poderes sobrehumanos. Tras la muerte de su tío Ben, aprende que con un gran poder viene una gran responsabilidad.',
 'Amazing Fantasy #15 (1962)',
 'heroe',
 'https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png'),

('Miles Morales', 'Miles Morales',
 'Spider-Man (Ultimate), El Otro Spider-Man',
 'Fuerza sobrehumana, agilidad, sentido de araña, camuflaje, veneno bioeléctrico (venom blast), trepar superficies',
 'Miles Morales es un adolescente afrolatino de Brooklyn que se convierte en Spider-Man tras ser picado por una araña radiactiva de un universo alternativo. Hereda el manto de Peter Parker en su universo.',
 'Ultimate Fallout #4 (2011)',
 'heroe',
 'https://upload.wikimedia.org/wikipedia/en/thumb/d/d9/Miles_Morales_character_design.jpg/220px-Miles_Morales_character_design.jpg'),

('Spider-Gwen', 'Gwen Stacy',
 'Ghost-Spider, Spider-Woman',
 'Fuerza sobrehumana, agilidad, sentido de araña, lanzar telarañas orgánicas, reflejos mejorados',
 'En su universo alternativo (Tierra-65), fue Gwen Stacy quien recibió los poderes de araña en lugar de Peter Parker. Tras la muerte de su Peter Parker, se convierte en la heroína conocida como Ghost-Spider.',
 'Edge of Spider-Verse #2 (2014)',
 'heroe',
 'https://upload.wikimedia.org/wikipedia/en/thumb/a/a0/Spider-Gwen.jpg/220px-Spider-Gwen.jpg'),

('Venom', 'Eddie Brock',
 'El Simbionte, Lethal Protector',
 'Fuerza y agilidad sobrehumanas, mimetismo de apariencia, lengua extensible, inmunidad al sentido de araña de Spider-Man, generar equipamiento del simbionte',
 'Eddie Brock es un reportero que alberga al simbionte alienígena Venom, creando una de las amenazas más icónicas de Spider-Man. Aunque comenzó como villano, ha evolucionado hacia el papel de protector letal.',
 'The Amazing Spider-Man #300 (1988)',
 'antiheroe',
 'https://upload.wikimedia.org/wikipedia/en/thumb/6/60/Venom_character_image.jpg/220px-Venom_character_image.jpg'),

('Green Goblin', 'Norman Osborn',
 'El Duende Verde, El Campeón',
 'Fórmula Duende: fuerza, agilidad y velocidad sobrehumanas, regeneración acelerada, inteligencia aumentada. Equipamiento: Planeador Duende, calabazas explosivas, rayos fantasma.',
 'Norman Osborn es un empresario corrupto y genio científico que se convierte en el Green Goblin tras exponerse a la fórmula Duende. Es considerado el mayor archivillano de Spider-Man, responsable de la muerte de Gwen Stacy.',
 'The Amazing Spider-Man #14 (1964)',
 'villano',
 'https://upload.wikimedia.org/wikipedia/en/thumb/3/3d/GreenGoblin.jpg/220px-GreenGoblin.jpg'),

('Doctor Octopus', 'Otto Octavius',
 'Doc Ock, El Superior Spider-Man',
 'Cuatro tentáculos de adamantio telepáticamente controlados, genio científico, en el cuerpo de Peter Parker: todos los poderes de Spider-Man',
 'Otto Octavius es un físico nuclear que quedó fusionado con cuatro brazos robóticos tras un accidente. Ha sido uno de los principales enemigos de Spider-Man, llegando incluso a intercambiar cuerpos con él.',
 'The Amazing Spider-Man #3 (1963)',
 'villano',
 'https://upload.wikimedia.org/wikipedia/en/thumb/0/04/Doctor_Octopus.jpg/220px-Doctor_Octopus.jpg'),

('Mary Jane Watson', 'Mary Jane Watson',
 'MJ, La Chica de Peter',
 'Sin poderes (humana), actriz y modelo profesional, gran valentía e inteligencia emocional',
 'Mary Jane Watson es la amor de la vida de Peter Parker. Conocida por su frase icónica "Face it, Tiger, you just hit the jackpot!", es una actriz y modelo que conoce la identidad secreta de Spider-Man.',
 'The Amazing Spider-Man #25 (1965)',
 'aliado',
 'https://upload.wikimedia.org/wikipedia/en/thumb/0/06/Mary_Jane_Watson.jpg/220px-Mary_Jane_Watson.jpg'),

('Aunt May', 'May Parker',
 'Tía May, Tía May Parker',
 'Sin poderes (humana). Fuerza moral excepcional, sabiduría y compasión inquebrantables.',
 'May Parker es la tía y figura materna de Peter Parker. Tras la muerte de su esposo Ben, cría a Peter sola. Representa los valores morales que guían a Spider-Man: responsabilidad, sacrificio y bondad.',
 'Amazing Fantasy #15 (1962)',
 'aliado',
 'https://upload.wikimedia.org/wikipedia/en/thumb/a/ae/Aunt_May.jpg/220px-Aunt_May.jpg'),

('Black Cat', 'Felicia Hardy',
 'La Gata Negra, El Gato',
 'Agilidad y reflejos sobrehumanos, habilidades acrobáticas, garra retráctil, inducir mala suerte en sus oponentes (en algunas versiones)',
 'Felicia Hardy es una ladrona de guante blanco que se convierte en aliada y amor interés de Spider-Man. Su compleja relación con Peter Parker la sitúa entre el bien y el mal.',
 'The Amazing Spider-Man #194 (1979)',
 'antiheroe',
 'https://upload.wikimedia.org/wikipedia/en/thumb/1/14/Black_Cat_character.jpg/220px-Black_Cat_character.jpg'),

('Sandman', 'Flint Marko',
 'El Hombre de Arena, Sandman',
 'Transformar su cuerpo en arena, control total de la arena, tamaño variable, regeneración total mientras haya arena',
 'Flint Marko es un criminal que adquiere poderes al caer en un campo de pruebas nucleares que altera su estructura molecular. Puede convertirse en arena y manipularla a voluntad. Comenzó como villano pero ha tenido etapas como aliado.',
 'The Amazing Spider-Man #4 (1963)',
 'villano',
 'https://upload.wikimedia.org/wikipedia/en/thumb/5/53/Sandman_%28Marvel_Comics%29.png/220px-Sandman_%28Marvel_Comics%29.png');
*/
