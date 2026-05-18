# MS_Arancel
El motor financiero central del ecosistema. Administra las cuentas corrientes de los alumnos, costos anuales, recargos por repitencia y saldos pendientes.
## Especificaciones Técnicas
* **Puerto:** `8006`
* **Base de Datos (Laragon):** `aranceles_db`
* **Tecnologías:** Spring Boot 4.0.6, JPA

## Lógica de Negocio Financiera
* Recibe penalizaciones desde Calificaciones (8004) aplicando reajustes de interés compuesto sobre el costo acumulado por cada semestre extra.
* Expone endpoints seguros para que el servicio de Pagos (8007) descuente dinero del saldo de forma inmediata.

## Endpoints Principales
* `GET /api/v1/arancel/{rut}` - Ver el estado de cuenta financiero y saldo del alumno en tiempo real.
* `PUT /api/v1/arancel/report-delay/{rut}` - Aplicar recargo por reprobación académica.
