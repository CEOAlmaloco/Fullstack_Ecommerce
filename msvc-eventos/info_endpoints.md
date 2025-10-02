POST: /usuarios -> registrar usuarios con validaciones de edad +18 despues de la autenticacion

POST: /usuarios/login ->autenticacion con JWT

GET: /usuarios/{id} -> obtener info del perfil

PUT: /usuarios/{id} -> actualizar datos personales

GET /usuarios/{id}/referidos -> listar los referidos

se comunica con
autenticacion -> para validar el login
referidos -> para asociar el otro usuario a este y generar los puntos
pedido -> para asociar la compra al usuario 