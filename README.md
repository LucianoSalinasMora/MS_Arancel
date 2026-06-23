MS_Arancel
El motor financiero central del ecosistema. Administra las cuentas corrientes de los alumnos, costos anuales, recargos por repitencia y saldos pendientes.

Especificaciones Técnicas
Puerto: 8006

Base de Datos (Laragon): aranceles_db

Tecnologías: Spring Boot 3.4.1, Spring Data JPA, Springdoc OpenAPI (Swagger), JUnit 5, Mockito

Lógica de Negocio Financiera
Recibe penalizaciones desde Calificaciones (8004) aplicando reajustes sobre el costo acumulado por cada semestre extra. Expone endpoints seguros para que el servicio de Pagos (8007) descuente dinero del saldo de forma inmediata.

Estructura de Desarrollo y Pruebas
Estructura de Carpetas: Configuración global de Swagger bajo el paquete config. Pruebas unitarias organizadas en las carpetas controller y service dentro del directorio src/test/java/SigueTuCarrera/.

Carga Inicial: Archivo SQL configurado al lado de application.properties utilizando sentencias INSERT IGNORE INTO.

Endpoints Principales
GET /arancel/{rut} - Ver el estado de cuenta financiero y saldo del alumno en tiempo real.

PUT /arancel/report-delay/{rut} - Aplicar recargo por reprobación académica.

GET /swagger-ui.html - Documentación interactiva de la API.

Compilación y Despliegue Docker
Bash
mvn clean package
docker build -t ms-arancel:1.0 .
docker run -d -p 8006:8006 --name ms-arancel -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.interna
