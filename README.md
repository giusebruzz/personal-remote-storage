Project: Personal Remote Storage

Questo progetto è un'applicazione backend Java/Spring Boot che simula un servizio di storage remoto personale.
Permette a utenti di registrarsi, caricare file, creare nodi di storage e gestire trasferimenti di file tra nodi.

Il progetto segue un'architettura a strati:
- Controller: gestisce le richieste HTTP
- Service: logica di business
- Repository: interazioni con il database (JPA/Hibernate)
- Model/Entity: rappresentazione degli oggetti persistenti
- DTO: oggetti per richieste e risposte API

Tecnologie:
- Java 17+
- Spring Boot 3.x
- Spring Security
- JPA/Hibernate
- MySQL o H2 (per test)
- Lombok
- Maven

Endpoints principali:
-User Management
- POST /users/register: registra un nuovo utente
- POST /users/login: login e ricezione token JWT

Storage Nodes:
- POST /nodes: crea un nodo di storage
- GET /nodes: lista nodi dell'utente
- GET /nodes/{id}: dettaglio nodo
- PUT /nodes/{id}: aggiorna nodo
- DELETE /nodes/{id}: elimina nodo

File Management:
- POST /files/upload?nodeId={id}: carica un file su un nodo
- GET /files: lista file dell'utente
- GET /files/{id}: dettaglio file
- DELETE /files/{id}: elimina file

File Transfers:
- POST /transfers: crea un trasferimento file verso un nodo
- PUT /transfers/{id}/status: aggiorna lo stato del trasferimento
- GET /transfers/status/{status}: lista trasferimenti filtrati per stato
- GET /transfers/status/{idFile}: lista trasferimenti filtrati per idFile

  
