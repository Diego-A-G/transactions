# Aplicación Móvil de Autorización y Anulación de Transacciones

Esta aplicación móvil desarrollada en Kotlin permite realizar operaciones de autorización y anulación de transacciones, así como la búsqueda y visualización de transacciones aprobadas. La aplicación se comunica con una API de servicios HTTP que proporciona la funcionalidad necesaria para estas operaciones.

## Requisitos del Proyecto

Para ejecutar esta aplicación, debes tener en cuenta los siguientes requisitos:

- [Android Studio](https://developer.android.com/studio) instalado en tu computadora.
- El servidor de la API de servicios HTTP debe estar en ejecución en [http://localhost:8080/api/payments](http://localhost:8080/api/payments).
- se debe actulizar la url del proyecto con la ip del servidor
- se deben conectar a la misma red wi-fi el dispositvo y el servidor

## Funcionalidades

### Autorización de Transacciones

- Para autorizar una transacción, ingresa los datos requeridos en el formulario.
- Los campos obligatorios incluyen: ID de transacción, monto, número de tarjeta, código de comercio y código de terminal.
- La transacción autorizada se almacena en la base de datos interna de la aplicación.

### Búsqueda de Transacciones Aprobadas por Número de Recibo

- Ingresa el número de recibo de una transacción en el formulario de búsqueda.
- Si se encuentra la transacción, se muestra su detalle y se proporciona la opción de anularla.
- Si no se encuentra la transacción, se muestra un mensaje de no exitente.

### Listado de Todas las Transacciones Aprobadas

- Esta vista muestra un listado de todas las transacciones almacenadas en la base de datos interna.
- Puedes ver el detalle de cada transacción y, si lo deseas, anularla.

### Anulación de Transacciones por Número de Recibo

- Desde la vista de búsqueda de transacciones, puedes anular una transacción si se encuentra en la base de datos interna.
- La anulación cambia el estado de la transacción en la base de datos y no permite su anulación nuevamente.

## Instrucciones de Ejecución

1. Clona este repositorio en tu computadora.

```shell
git clone https://github.com/TuUsuario/nombre-de-repo.git

2.Abre el proyecto en Android Studio.

3.Asegúrate de tener el servidor de la API en ejecución en http://localhost:8080/api/payments.

4.Ejecuta la aplicación en un emulador de Android o dispositivo físico.
