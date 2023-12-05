# CRUD-vehiculos
Este proyecto hace las funciones de un CRUD basico adicionalmente se armaron pruebas unitarias con JUNIT5 Y MOCKITO

#Levantar ambiente Base de Datos
Se añadio un archivo docker-compose para poder levantar un ambiente en docker si se tiene instalado
las credenciales para acceder ael son:
Hostname:127.0.0.1
Port:3307   (en caso de estar usando Mysql con el prto 3306 este debera ser cambiado en el archivo application.properties)
Username:root

#Creación de la BD
Una vez establecida la conexion 
1. crear el esquema "vehiculos"
2. se sugiere cargar y ejecutar el archivo script-creacion-tabla.sql  que generara la tabla automaticamente.  

#Pruebas
-Obtener un vehículo : http://localhost:8080/v1/vehiculos/{numero de id del vehiculo a obtener}
-Obtener vehiculos :http://localhost:8080/v1/vehiculos
-Crear vehiculo: http://localhost:8080/v1/vehiculos
  body:  ejemplo para la creación
    {
    "marca":"Audi",
    "modelo":"aaa",
    "placa":"GHI-678",
    "precio":"55500.00"
    }
-Actualizar vehiculo : http://localhost:8080/v1/vehiculos/{numero de id del vehiculo a actualizar}
  body: ejemplo para la actualización
    {
    "marca":"Toyota",
    "modelo":"Camry",
    "placa":"LMN-456",
    "precio":"25000.00"
    }
-Eliminar vehiculo : http://localhost:8080/v1/vehiculos/{numero de id del vehiculo a eliminar}

