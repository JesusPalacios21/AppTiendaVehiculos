# App Tienda Vehículos

Aplicación Android sencilla que permite **gestionar vehículos** (CRUD) conectándose a un **API REST con Node.js + MySQL**.  
Este proyecto es parte de un ejercicio académico para aprender a consumir servicios desde Android Studio con **Volley**.

---

## Funcionalidades
La app permite:
- **Listar vehículos** registrados en la base de datos.
- **Agregar un vehículo** con sus datos.
- **Actualizar** un vehículo existente.
- **Eliminar** un vehículo por su ID.
- **Buscar** un vehículo por **id**.

---

## ⚙Requisitos
### Backend (Servidor)
- Node.js + Express  
- Base de datos MySQL  
- Puerto configurado en **3000** (editable en `app.js`):  
  ```js
  const port = process.env.PORT || 3000;

  Este proyecto deriva de otro. Configurar bien la direccion ip al ejecutarlo en Android Studio para probra
