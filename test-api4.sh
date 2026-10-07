#!/bin/bash

BASE_URL="http://localhost:8080/api/v1"
ADMIN_EMAIL="admin@fastorder.com"
ADMIN_PASS="Admin123*"
CLIENTE_EMAIL="cliente@fastorder.com"
CLIENTE_PASS="Cliente123*"

echo "=== INICIANDO PRUEBAS DEL SISTEMA ==="

# Registro y autenticación de Administrador
echo -e "\n[0] Registrando Admin..."
curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Administrador", "apellido": "Sistema", "correo": "'"$ADMIN_EMAIL"'", "password": "'"$ADMIN_PASS"'", "telefono": "55551111", "rol": "ADMIN"}' | jq .

echo -e "\n[2] Autenticando Admin..."
ADMIN_LOGIN=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"correo": "'"$ADMIN_EMAIL"'", "password": "'"$ADMIN_PASS"'"}')
ADMIN_TOKEN=$(echo $ADMIN_LOGIN | jq -r '.token // .accessToken // .jwt')
echo "Token obtenido con éxito."

# Registro y autenticación de Cliente
echo -e "\n[1] Registrando Cliente..."
curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Cliente", "apellido": "Ejemplo", "correo": "'"$CLIENTE_EMAIL"'", "password": "'"$CLIENTE_PASS"'", "telefono": "55554321", "rol": "CLIENTE"}' | jq .

echo -e "\n[5] Autenticando Cliente..."
CLIENTE_LOGIN=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"correo": "'"$CLIENTE_EMAIL"'", "password": "'"$CLIENTE_PASS"'"}' )
CLIENTE_TOKEN=$(echo $CLIENTE_LOGIN | jq -r '.token // .accessToken // .jwt')

# Operaciones del Negocio
echo -e "\n[3] Creando comercio..."
curl -s -X POST "$BASE_URL/comercios" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{"nombre": "Burger Express", "categoria": "RESTAURANTE", "direccion": "Calzada Roosevelt 12-45", "abierto": true}' | jq .

echo -e "\n[4] Registrando producto..."
curl -s -X POST "$BASE_URL/comercios/1/productos" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{"nombre": "Hamburguesa Doble con Queso", "precio": 45.00, "stock": 50, "disponible": true}' | jq .

echo -e "\n[6] Generando pedido..."
curl -s -X POST "$BASE_URL/pedidos" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $CLIENTE_TOKEN" \
  -d '{"items": [{"productoId": 1, "cantidad": 2}]}' | jq .

# Pruebas de Seguridad y Estrés
echo -e "\n[7] Verificando restricciones de rol (403)..."
HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/comercios" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $CLIENTE_TOKEN" \
  -d '{"nombre": "Tienda Ilegal", "categoria": "SUPERMERCADO", "direccion": "Desconocida", "abierto": true}')

if [ "$HTTP_STATUS" -eq 403 ]; then
  echo "--> OK: Acceso denegado correctamente (403 Forbidden)."
else
  echo "--> ADVERTENCIA: Código inesperado ($HTTP_STATUS)."
fi

echo -e "\n--> Ejecutando prueba de estrés en catálogo..."
seq 20 | xargs -n 1 -P 5 curl -s -o /dev/null -w "%{http_code}\n" \
  -X GET "$BASE_URL/comercios" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | sort | uniq -c

echo -e "\n=== PRUEBAS FINALIZADAS ===\n"