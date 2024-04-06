**API RESTful de Usuarios**
Esta API RESTful permite la creación y consulta de usuarios, almacenando la información en una base de datos H2 en memoria.

**Requisitos**
Docker
Java 11

**Ejecución**

**1. Clona el repositorio:**

git clone https://github.com/tu-usuario/register-cl.git

**2. Entra al directorio del proyecto:**

cd register-cl

**3. Construye la imagen de Docker:**

docker build -t register-cl .

**4. Ejecuta el contenedor:**

docker run -p 8080:8080 register-cl

Endpoints

Obtener Usuarios
Método: GET

URL: /api/cl/find

Respuesta Exitosa:
{
    "message": "Usuarios",
    "data": [
        {
            "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
            "name": "John Doe",
            "email": "john.doe@example.com",
            "password": "password",
            "phones": [
                {
                    "id": 1,
                    "number": "1234567",
                    "cityCode": "1",
                    "countryCode": "57"
                }
            ],
            "created": "2022-01-01T00:00:00",
            "modified": "2022-01-01T00:00:00",
            "lastLogin": "2022-01-01T00:00:00",
            "token": "token123",
            "active": true
        }
    ]
}


Registrar Usuario
Método: POST

URL: /api/cl/register

Cuerpo de la Petición:

{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.cl",
    "password": "P@ssw0rd",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}

Respuesta Exitosa:

{
    "message": "Usuario",
    "data": {
        "id": "5efb402b-ae1b-4f70-b280-b6ac4e959567",
        "created": "2024-04-06T10:47:07.3840682",
        "modified": "2024-04-06T10:47:07.3840682",
        "lastLogin": "2024-04-06T10:47:07.3840682",
        "token": "asdfgsdfg",
        "active": true
    }
}

Respuesta de Error:

{
    "message": "Correo ya registrado",
    "data": null
}




**Diagrama de Solucion**

https://drive.google.com/file/d/1bulI1rm0KpSZxZcRsiyCTcz8z_iE_eKM/view?usp=sharing